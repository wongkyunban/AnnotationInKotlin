package com.example.annotationinkotlin

import junit.framework.TestCase

class JsonParserTest : TestCase() {

    fun testParseToJson() {
        val student = Student(lastName = "Wong", firstName = "ban", address = "GZ", age = 18, school =  "110Mid")
        val jsonParser = JsonParser()
        val actual: String = jsonParser.parseToJson(student)
        val expected =
            "{\"lastName\":\"Wong\",\"firstName\":\"ban\",\"fullName\":\"Wong ban@\",\"home\":\"GZ\",\"age\":18}"
        assertEquals(expected, actual)
    }
}