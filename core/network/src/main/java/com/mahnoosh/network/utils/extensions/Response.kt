package com.mahnoosh.network.utils.extensions

import com.google.gson.Gson
import com.google.gson.JsonSyntaxException
import okhttp3.ResponseBody
import retrofit2.Response
import java.io.IOException

fun <T, E> Response<T>.toErrorBody(errorClass: Class<E>, gson: Gson = Gson()): E? {
    // Check if the response was not successful (i.e., it has an error body)
    if (this.isSuccessful) {
        return null // No error body to parse if the response was successful
    }
    val errorBody: ResponseBody? = this.errorBody()

    if (errorBody == null) {
        return null
    }

    try {
        val errorJsonString = errorBody.string()
        return gson.fromJson(errorJsonString, errorClass)
    } catch (e: IOException) {
        return null
    } catch (e: JsonSyntaxException) {
        return null
    } finally {
        errorBody.close()
    }
}