//package demo
//
//import org.junit.Test
//import org.junit.jupiter.api.BeforeEach
//import org.junit.jupiter.api.DisplayName
//import org.junit.jupiter.api.Tag
//import org.springframework.boot.test.context.SpringBootTest
//import org.springframework.http.ResponseEntity.status
//import org.springframework.test.web.servlet.MockMvc
//import org.springframework.test.web.servlet.RequestBuilder
//import org.springframework.test.web.servlet.ResultActions
//import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
//import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
//
//
//
//
//@SpringBootTest
//class DemoApplicationTests {
//
//	@org.testng.annotations.Test
//	fun contextLoads() {
//
//	}
//}
//
//class TaskCrudRequestBuilder(private val mockMvc: MockMvc) {
//	fun findRate(): ResultActions {
//		return mockMvc.perform(get("/rates") as RequestBuilder)
//	}
//}
//
//@Tag("unitTest")
//@DisplayName("Perform CRUD operations for tasks")
//class TaskCrudControllerTest (private val mockMvc: MockMvc) {
//
//	@BeforeEach
//	fun configureSystemUnderTest(){
//
//	}
//
//	private lateinit var requestBuilder: TaskCrudRequestBuilder
//
//	@Test
//	@DisplayName("Should test the price endpoint")
//	fun shouldReturnHTTPStatusCodeOk(){
//		requestBuilder.findRate()
//			.andExpect(status().isOk)
//	}
//
//	fun getPrice(): ResultActions {
//		return mockMvc.perform(get("/rates") as RequestBuilder)
//	}
//}