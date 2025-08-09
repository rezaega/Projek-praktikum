package com.example.projek_praktikum.service.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiClient {
    // Alamat dasar (base URL) dari backend API
    private const val BASE_URL = "https://e-donasi.vercel.app/api/"

    /**
     * Objek `instance` memberikan akses ke implementasi `ApiService`.
     * Inisialisasi dilakukan secara lazy untuk efisiensi.
     */

    val instance: ApiService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL) // Mengatur base URL
            .addConverterFactory(GsonConverterFactory.create()) // Menggunakan converter Gson
            .build()
            .create(ApiService::class.java) // Membuat implementasi ApiService
    }
}