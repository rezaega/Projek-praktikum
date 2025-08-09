package com.example.projek_praktikum.model.response
//* Representasi response dari server setelah proses login berhasil atau gagal.
//*
//* @property code Kode status HTTP dari server (contoh: 200 untuk sukses).
//* @property message Pesan deskriptif dari hasil login (contoh: "Login berhasil").
//* @property data Informasi pengguna yang berhasil login, jika tersedia.
//* @property token Token autentikasi yang diberikan server untuk sesi pengguna.
//*/
data class LoginResponse(
    val status: Int?,
    val message: String?,
    val data: LoginData?,
)

/**
 * Informasi pengguna yang dikembalikan setelah proses login berhasil.
 *
 * @property uuid ID unik pengguna (biasanya digunakan sebagai identifikasi global).
 * @property fullName Nama lengkap pengguna.
 */
data class LoginData(
    val username: String,
    val token: String?


)