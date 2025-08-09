package com.example.projek_praktikum.screen

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.projek_praktikum.model.request.LoginRequest
import com.example.projek_praktikum.service.api.ApiClient
import com.example.projek_praktikum.utils.PreferenceManager
import kotlinx.coroutines.launch
import org.json.JSONObject

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen(navController: NavController) {
    val username = remember { mutableStateOf("") }
    val password = remember { mutableStateOf("") }
    val passwordVisible = remember { mutableStateOf(false) }

    // State untuk menandai apakah terdapat error pada input
    var usernameError by remember { mutableStateOf(false) }
    var passwordError by remember { mutableStateOf(false) }

    // State untuk menampilkan indikator loading saat proses login berlangsung
    var isLoading by remember { mutableStateOf(false) }

    // Utilitas untuk manajemen UI
    val focusManager = LocalFocusManager.current
    val coroutineScope = rememberCoroutineScope()
    val context = LocalContext.current

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(Color(0xFFFFECB3), Color.White)
                )
            )
            .padding(24.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center
        ) {
            Text("E - Donasi",
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp)

            Spacer(modifier = Modifier.height(70.dp))

            Text("Login", style = MaterialTheme.typography.headlineMedium.copy(fontWeight = FontWeight.Bold))

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = username.value,
                onValueChange = { username.value = it },
                label = { Text("Username") },
                leadingIcon = {
                    Icon(imageVector = Icons.Default.Email, contentDescription = "Email Icon", tint = Color(0xFFFFC107))
                },
                singleLine = true,
                modifier = Modifier.fillMaxWidth(),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Color.Gray,
                    unfocusedBorderColor = Color.LightGray,
                    focusedLabelColor = Color(0xFFFFC107),
                    cursorColor = Color.Black
                )
            )

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = password.value,
                onValueChange = { password.value = it },
                label = { Text("Password") },
                leadingIcon = {
                    Icon(imageVector = Icons.Default.Lock, contentDescription = "Password Icon", tint = Color(0xFFFFC107))
                },
                trailingIcon = {
                    val image = if (passwordVisible.value)
                        Icons.Default.Visibility
                    else Icons.Default.VisibilityOff

                    IconButton(onClick = {
                        passwordVisible.value = !passwordVisible.value
                    }) {
                        Icon(imageVector = image, contentDescription = "Toggle Password Visibility")
                    }
                },
                singleLine = true,
                visualTransformation = if (passwordVisible.value) VisualTransformation.None else PasswordVisualTransformation(),
                modifier = Modifier.fillMaxWidth(),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Color.Gray,
                    unfocusedBorderColor = Color.LightGray,
                    focusedLabelColor = Color(0xFFFFC107),
                    cursorColor = Color.Black
                )
            )

            Spacer(modifier = Modifier.height(24.dp))

            Button(
                onClick = {
                    focusManager.clearFocus() // Tutup keyboard
                    usernameError = username.value.isBlank()
                    passwordError = password.value.isBlank()

                    if (!usernameError && !passwordError) {
                        isLoading = true
                        coroutineScope.launch {
                            val loginRequest =
                                LoginRequest(username = username.value, password = password.value)
                            try {
                                val response = ApiClient.instance.login(
                                    username = loginRequest.username,
                                    password = loginRequest.password
                                )
                                isLoading = false
                                val body = response.body()

                                if (response.isSuccessful && body?.status  == 200) {
                                    val token = body.data?.token // Ganti ini sesuai struktur respons kamu
                                    // Simpan token ke DataStore
                                    token?.let {
                                        PreferenceManager.setToken(context, it)
                                    }

                                    Toast.makeText(context, "Login Berhasil", Toast.LENGTH_SHORT).show()
                                    navController.navigate("home") {
                                        popUpTo("login") { inclusive = true }
                                    }
                                } else {
                                    val errorJsonString = response.errorBody()?.string()
                                    val errorMessage = try {
                                        JSONObject(errorJsonString ?: "").getString("message")
                                    } catch (e: Exception) {
                                        "Terjadi kesalahan saat parsing error"
                                    }

                                    Toast.makeText(context, "$errorMessage", Toast.LENGTH_LONG).show()
                                }
                            } catch (e: Exception) {
                                isLoading = false
                                Toast.makeText(
                                    context,
                                    "terjadi kesalahan : ${e.localizedMessage}",
                                    Toast.LENGTH_LONG
                                ).show()
                            }
                        }
                    }else{
                        Toast.makeText(context, "mohon Diisi oke", Toast.LENGTH_LONG).show()
                    }
                },
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFFC107)),
                shape = RoundedCornerShape(8.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp)
            ) {
                Text("LOGIN", fontWeight = FontWeight.Bold)
            }

            Spacer(modifier = Modifier.height(16.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                Text("Tidak punya akun ? ")
                Text(
                    "Register",
                    color = Color(0xFFFFC107),
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.clickable {
                        navController.navigate("register")
                    }
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewLoginScreen() {
    LoginScreen(rememberNavController())
}

