package com.example.projek_praktikum.model.response

data class RegisterResponse(
    val status: String,
    val message: String?
)
data class RegisterUser(
    val id : String,
    val username:String,
)