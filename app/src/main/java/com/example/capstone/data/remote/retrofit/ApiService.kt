package com.example.capstone.data.remote.retrofit

import com.example.capstone.data.remote.response.AuthResponse
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface ApiService {
    @FormUrlEncoded
    @POST("auth/register")
    fun register(
        @Field("name")name: String,
        @Field("email")email: String,
        @Field("password")password: String
    ): Call<AuthResponse>

    @FormUrlEncoded
    @POST("auth/login")
    fun login(
        @Field("email")email: String,
        @Field("password")password: String
    ): Call<AuthResponse>

}
