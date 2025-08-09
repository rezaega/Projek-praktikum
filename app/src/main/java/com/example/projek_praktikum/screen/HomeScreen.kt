package com.example.projek_praktikum.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.projek_praktikum.model.viewModel.ViewModelDana
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.projek_praktikum.utils.PreferenceManager

@Composable
fun HomeScreen(navController: NavController, viewModelDana: ViewModelDana = viewModel()) {
    // ✅ Ambil data dari INSTANCE viewModel, bukan dari class
    val data = viewModelDana.penggunaanDanaList
    val context = LocalContext.current



    LaunchedEffect(Unit) {
        val token = PreferenceManager.getToken(context)
        viewModelDana.getPenggunaanDanaFromApi("Bearer $token")

    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(Color(0xFFFFF3E0), Color(0xFFFFECB3))
                )
            )
            .padding(top = 24.dp)

    ) {
        // Header
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(0xFFFFC107))
                .padding(16.dp)
                .padding(horizontal = 16.dp)
        ) {
            Text(
                text = "E - Donasi",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold
            )
        }

        Spacer(modifier = Modifier.height(16.dp))


        // Tombol Navigasi
        Button(
            onClick = { navController.navigate("input penggunaan") },
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFFC107)),
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Input Penggunaan Dana", color = Color.Black)
        }

        Spacer(modifier = Modifier.height(16.dp))

        // ✅ Tampilan Data
        if (data.isEmpty()) {
            Text("Belum ada data penggunaan dana", fontSize = 14.sp)
        } else {
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(12.dp),
                modifier = Modifier.fillMaxSize()
            ) {
                items(data) { item ->
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .wrapContentHeight()
                            .clickable {
                                viewModelDana.selectedItem(item)
                                navController.navigate("detail penggunaan")

                            },
                        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
                        colors = CardDefaults.cardColors(containerColor = Color.White)
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Text(
                                text = "Rp ${item.nominal}",
                                fontWeight = FontWeight.Bold,
                                fontSize = 16.sp,
                                color = Color(0xFF388E3C)
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                            item.deskripsi?.let {
                                Text(
                                    text = it,
                                    fontSize = 14.sp,
                                    color = Color.DarkGray
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}
