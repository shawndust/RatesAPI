//package demo
//
//import io.mockk.mockk
//import org.junit.jupiter.api.Assertions
//import org.junit.jupiter.api.Test
//import org.junit.jupiter.api.extension.ExtendWith
//import org.springframework.boot.test.context.SpringBootTest
//import org.springframework.boot.test.context.TestConfiguration
//import org.springframework.boot.test.web.client.TestRestTemplate
//import org.springframework.boot.web.server.LocalServerPort
//import org.springframework.context.annotation.Bean
//import org.springframework.http.HttpEntity
//import org.springframework.http.HttpMethod
//import org.springframework.http.HttpStatus
//import org.springframework.test.context.ActiveProfiles
//import org.springframework.test.context.ContextConfiguration
//import org.springframework.test.context.junit.jupiter.SpringExtension
//import java.net.URI
//
//@ContextConfiguration (locations = arrayOf("classpath*:/spring/applicationContext*.xml"))
//class ApplicationTest {
//    @ExtendWith(SpringExtension::class)
//    @SpringBootTest(
//        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
//        classes = [ApplicationTest.ControllerTestConfig::class],
//        properties = ["spring.example.property=foobar"]
//    )
//    @ActiveProfiles(value = ["test"])
//    internal class ApplicationTest {
//
//        var testRestTemplate = TestRestTemplate()
//
//        @LocalServerPort
//        var serverPort: Int = 0
//
//        @TestConfiguration
//        internal class ControllerTestConfig {
//            @Bean
//            fun rateService(): RateService = mockk()
//        }
//
//        private fun applicationUrl() = "http://localhost:5000"
//
//
//        @Test
//        fun simpleGetTest() {
//            val result = testRestTemplate.exchange(
//                URI(applicationUrl() + "/price/?start=2015-07-01T07:00:00+05:00&end=2015-07-01T12:00:00+05:00"),
//                HttpMethod.GET,
//                HttpEntity(""),
//                String::class.java
//            )
//
//            Assertions.assertEquals(HttpStatus.OK, result.statusCode)
//            Assertions.assertEquals("{\n" +
//                    "  \"price\": 1750\n" +
//                    "}", result.body)
//        }
//    }
//}