package com.example.annotationinkotlin

@SerializableJSON
data class Student(
    @JsonField
    val lastName: String,
    @JsonField
    val firstName: String,
    @JsonField("fullName")
    var name: String = "",
    @JsonField("home")
    val address: String,
    @JsonField
    val age:Int,
    val school:String
){
    @BeforeSerializable private fun obtainFullName(){
        name = "$lastName ${firstName}@"
    }
}
