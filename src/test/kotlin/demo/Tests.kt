//package demo;
//
//import jdk.nashorn.internal.objects.NativeRegExp.test
//import junit.framework.Assert.assertEquals
//import org.junit.Test
//import kotlin.test.Test
//
//
//class Tests {
//    @Test
//    fun testEncodeToString() {
//        checkEncodeToString("Kotlin is awesome", "S290bGluIGlzIGF3ZXNvbWU=")
//    }
//
//    @Test
//    fun testPaddedStrings() {
//        checkEncodeToString("", "")
//        checkEncodeToString("1", "MQ==")
//        checkEncodeToString("22", "MjI=")
//        checkEncodeToString("333", "MzMz")
//        checkEncodeToString("4444", "NDQ0NA==")
//    }
//
//    private fun checkEncodeToString(input: String, expectedOutput: String) {
//        assertEquals(expectedOutput, Base64Factory.createEncoder().encodeToString(input.asciiToByteArray()))
//    }
//
//    private fun String.asciiToByteArray() = ByteArray(length) {
//        get(it).toByte()
//    }
//}
