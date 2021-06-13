package com.example.annotationinkotlin

@Retention(AnnotationRetention.RUNTIME)
@Target(AnnotationTarget.FIELD)
annotation class JsonField(val key:String = "")
