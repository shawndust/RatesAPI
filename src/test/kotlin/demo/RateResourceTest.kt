package demo

import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import junit.framework.Assert.assertEquals
import org.junit.Assert.assertNotEquals
import org.junit.jupiter.api.Test

// Testing decisions, I decided for this project not to test db calls, nor Kotlin libraries, which left a few cases:
// testing the logic the findDayOfWeek method, and also the getPrice method, which includes more than just a db call.
internal class RateResourceTest {
    @Test
    fun findDayOfWeek() {
        val service = mockk<RateService>()
        val rR = RateResource(service)
        assertEquals(rR.findDayOfWeek("2015-07-01T07:00:00-05:00"), "WEDNESDAY")
        assertNotEquals(rR.findDayOfWeek("2015-07-02T07:00:00-05:00"), "WEDNESDAY")
    }

    @Test
    fun getPrice() {
        val service = mockk<RateService>()
        val mondayRate = Rate("1", "mon,tues,thurs","0900-2100", "America/Chicago", 1500)
        val rateList = listOf<Rate>(mondayRate)
        val rateResource = RateResource(service)

        every { service.findRates("WEDNESDAY") } returns rateList

        rateResource.getPrice("2015-07-01T07:00:00-05:00","2015-07-01T12:00:00-05:00")

        verify {service.findRates("WEDNESDAY")}
    }


}