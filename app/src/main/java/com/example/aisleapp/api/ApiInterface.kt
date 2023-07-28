package com.example.aisleapp.api

import com.example.aisleapp.model.Invites
import com.example.aisleapp.model.ModelDetails
import com.example.aisleapp.model.OtpModel
import com.example.aisleapp.model.TokenClass
import com.google.gson.JsonElement
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.POST

interface ApiInterface {
    @POST("users/phone_number_login")
    fun postNumber(@Body number: String): Call<JsonElement?>?

    @POST("users/verify_otp")
    fun postOtp(@Body model :OtpModel): Call<TokenClass?>?

    @GET("users/test_profile_list")
    fun getNotes(@Header("Authorization") bearerToken: String): Call<ModelDetails?>?
}
