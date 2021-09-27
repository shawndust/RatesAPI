package demo

import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.registerKotlinModule
import com.google.gson.Gson
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.event.ApplicationReadyEvent
import org.springframework.boot.runApplication
import org.springframework.context.event.EventListener
import org.springframework.data.annotation.Id
import org.springframework.data.jdbc.repository.query.Query
import org.springframework.data.relational.core.mapping.Table
import org.springframework.data.repository.CrudRepository
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service
import org.springframework.web.bind.annotation.*
import java.io.File
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter


@SpringBootApplication
class DemoApplication

// Runs the program
fun main(args: Array<String>) {
	runApplication<DemoApplication>(*args)
}

// After the program starts up, loads the json file with initial rates into the database
@EventListener(ApplicationReadyEvent::class)
fun uploadJsonRatesAfterStartup() {
	val mapper = jacksonObjectMapper()
	mapper.registerKotlinModule()
	mapper.registerModule(JavaTimeModule())

	val jsonString: String = File("./src/main/resources/rates.json").readText(Charsets.UTF_8)

	if(jsonString != null) {
		initialSaveToDb(jsonString)
	}
}

fun initialSaveToDb(jsonString: String) {
	initialSaveToDb(jsonString)
}

@RestController
class RateResource(val service: RateService) {
	// Price endpoint (Http GET)
	// Takes a range of date times (ISO 8601) and returns a price to reserve that slot
	// Rule: the given range must fit within a rate's time window (rates are loaded at startup), or else it is invalid
	// Example query: http://localhost:5000/price/?start=2015-07-01T07:00:00+05:00&end=2015-07-01T12:00:00+05:00
	@GetMapping("/price/")
	open fun getPrice(@RequestParam start: String, @RequestParam end: String): ResponseEntity<String> {
		val httpHeaders = HttpHeaders()
		httpHeaders.setContentType(MediaType.APPLICATION_JSON);
		val unavailable = ResponseEntity<String>("unavailable", httpHeaders, HttpStatus.BAD_REQUEST)

		// If the reservation spans more than one day, it is unavailable
		if (!startDateEqualsEndDate(start, end)){
				return unavailable
		}

		val listOfRates = service.findRates(findDayOfWeek(start))
		val startHour = convertToLocalDateTime(start).hour
		val endHour = convertToLocalDateTime(end).hour
		var dollarAmount = 0

		for (rate in listOfRates){
			var min = rate.times?.substring(0,4)?.toInt()
			var max = rate.times?.substring(5,9)?.toInt()

			if (startHour*100 >= min!! && startHour*100 < max!! && endHour*100 > min && endHour*100 <= max && max > min){
				dollarAmount = rate.price!!
				break
			}
		}

		// If a valid reservation slot has not been found (for example, if a requested time range spanned multiple rates)
		// then return unavailable
		if (dollarAmount == 0){
			return unavailable
		}

		// Return in json format the price of a valid reservation
		return ResponseEntity<String>("{ \"price\": " + dollarAmount.toString() + " }", httpHeaders, HttpStatus.OK)
	}

	// Rates endpoint (Http PUT)
	// Updates the current rate schedule with new entries
	// Rule: Rates must be in Json format
	// Example query: http://localhost:5000/rates/  (with json rates in the body)
	@PutMapping("/rates/")
	fun putRates(@RequestBody rates: String) {
		service.put(rates)
	}

	// Rates endpoint (Http GET)
	// Returns all current rates
	// Example query: http://localhost:5000/rates/
	@GetMapping("/rates/")
	fun getAllRates(): List<Rate> {
		return service.findAllRates()
	}

	// Helper functions
	private fun convertToLocalDateTime(date: String): LocalDateTime {
		return LocalDateTime.parse(date, DateTimeFormatter.ISO_OFFSET_DATE_TIME)
	}

	public fun findDayOfWeek(start: String): String {
		var date = convertToLocalDate(start)
		var dayOfWeek = date.dayOfWeek
		return dayOfWeek.toString()
	}

	private fun convertToLocalDate(date: String): LocalDate {
		return LocalDate.parse(date, DateTimeFormatter.ISO_OFFSET_DATE_TIME)
	}

	private fun startDateEqualsEndDate(start: String, end: String): Boolean {
		var startDate = convertToLocalDate(start)
		var endDate = convertToLocalDate(end)

		return startDate.equals(endDate)
	}
}

@Table("RATES")
data class Rate(@Id val id: String?, val days: String?, val times: String?, val tz: String?, val price: Int?)

data class RatesModel(
	val rates: List<Rate>
)

interface RateRepository : CrudRepository<Rate, String>{
	@Query("SELECT * FROM rates WHERE days LIKE :date")
	fun findRates(date: String): List<Rate>

	@Query("select * from rates")
	fun findAllRates(): List<Rate>
}

@Service
class RateService(val db: RateRepository) {
	// Do a fuzzy search to find which rate records have a given date in them
	fun findRates(day: String): List<Rate> {
		val search = "%" + day.substring(0,3).toLowerCase() + "%"
		return db.findRates(search)
	}

	fun findAllRates(): List<Rate> {
		return db.findAllRates()
	}

	fun put(jsonAsString: String){
		// Before putting the new rates, delete the old ones
		db.deleteAll()
		var gson = Gson()
		var rateModel = gson.fromJson(jsonAsString, RatesModel::class.java)
		rateModel.rates.forEach { x -> db.save(x) }
	}

	fun initialSaveToDb(jsonAsString: String){
		db.deleteAll()
		var gson = Gson()
		var rateModel = gson.fromJson(jsonAsString, RatesModel::class.java)
		rateModel.rates.forEach { x -> db.save(x) }
	}
}




