package com.example.annotationinkotlin

import android.os.Build
import androidx.annotation.RequiresApi
import java.lang.reflect.Field
import java.util.stream.Collectors

class JsonParser {

    private fun checkIfSerializable(obj: Any) {
        if (!obj.javaClass.isAnnotationPresent(SerializableJSON::class.java)) {
            throw Exception("${obj.javaClass.simpleName} is not annotated with SerializableJSON")
        }
    }

    private fun executeInitialFunction(obj: Any) {
        for (method in obj.javaClass.declaredMethods) {
            if (method.isAnnotationPresent(BeforeSerializable::class.java)) {
                method.isAccessible = true
                method.invoke(obj)
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.N)
    private fun getJsonString(obj: Any): String {
        val map: MutableMap<String, Any> = mutableMapOf()
        for (field in obj.javaClass.declaredFields) {
            field.isAccessible = true
            if (field.isAnnotationPresent(JsonField::class.java)) {
                field.get(obj)?.let { it ->
                    getKey(field)?.let { it1 ->
                        map[it1] = it
                    }
                }
            }
        }
        return "{${
            map.entries.stream().map {
                "\"${it.key}\":${if(it.value is String) "\"${it.value}\"" else it.value}"
            }.collect(Collectors.joining(","))
        }}"
    }

    private fun getKey(field: Field): String? {
        val jsonField = field.getAnnotation(JsonField::class.java)
        return if (jsonField != null) {
            if (jsonField.key.isEmpty()) {
                field.name
            } else {
                jsonField.key
            }
        } else null
    }

    fun parseToJson(obj:Any):String{
        try {
            checkIfSerializable(obj)
            executeInitialFunction(obj)
            return getJsonString(obj)
        }catch (e:Exception){
            throw e
        }
    }
}