package com.example.projek_praktikum.service.api

import com.example.projek_praktikum.model.request.LoginRequest
import com.example.projek_praktikum.model.request.PenggunaanDanaItem
import com.example.projek_praktikum.model.request.RegisterRequest
import com.example.projek_praktikum.model.response.DeleteResponse
import com.example.projek_praktikum.model.response.HomeResponse
import com.example.projek_praktikum.model.response.InputPenggunaanDana
import com.example.projek_praktikum.model.response.LoginResponse
import com.example.projek_praktikum.model.response.RegisterResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Path

interface ApiService {
        @FormUrlEncoded
        @POST("login")
        suspend fun login(
                @Field("username") username : String,
                @Field("password") password : String
        ): Response<LoginResponse>

        @FormUrlEncoded
        @POST("register")
        suspend fun register(
                @Field("name") name: String,
                @Field("username") username : String,
                @Field("password") password : String
        ): Response<RegisterResponse>

        @FormUrlEncoded
        @POST("donasi")
        suspend fun PenggunaanDana(
                @Header("Authorization") token: String,
                @Field("nominal") nominal: Int,
                @Field("deskripsi") deskripsi: String
        ): Response<InputPenggunaanDana>

        @GET("donasi")
        suspend fun getPenggunaanDana(
                @Header("Authorization") token: String?,
        ): Response<HomeResponse>

        @DELETE("donasi/{id}")
        suspend fun deletePenggunaanDana(
                @Header("Authorization") token: String,
                @Path("id") id: String
        ): Response<DeleteResponse>

        @FormUrlEncoded
        @PATCH("donasi/{id}")
        suspend fun updatePenggunaanDana(
                @Header("Authorization") token: String,
                @Path("id") id: String,
                @Field("nominal") nominal: Int,
                @Field("deskripsi") deskripsi: String
        ): Response<InputPenggunaanDana>




}