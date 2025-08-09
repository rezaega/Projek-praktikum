package com.example.projek_praktikum.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.navigation.NavController
import com.example.projek_praktikum.model.viewModel.ViewModelDana

@Composable
fun DetailPenggunaanDanaScreen(
    viewModelDana: ViewModelDana,
    navController: NavController
) {
    var showDialog by remember { mutableStateOf(false) }

    // Ambil data dari ViewModel supaya nggak acak
    val selectedItem = viewModelDana.selectedItem.collectAsState().value
    val itemId = selectedItem?.id ?: ""
    val nominal = selectedItem?.nominal?.toString() ?: "-"
    val deskripsi = selectedItem?.deskripsi ?: "-"


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
                text = "Detail Penggunaan Dana",
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Action Bar
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            IconButton(onClick = { navController.popBackStack() }) {
                Icon(Icons.Default.ArrowBack, contentDescription = "Back")
            }

            Row {
                IconButton(onClick = { showDialog = true }) {
                    Icon(Icons.Default.Delete, contentDescription = "Delete")
                }
                IconButton(onClick = {
                    navController.navigate(
                        "editPenggunaan/${selectedItem?.id ?: ""}/${selectedItem?.nominal ?: 0}/${selectedItem?.deskripsi ?: ""}"
                    )
                }) {
                    Icon(Icons.Default.Edit, contentDescription = "Edit")
                }
            }
        }

        // Dialog Konfirmasi Delete
        deleteModel(
            showDialog = showDialog,
            onDismiss = { showDialog = false },
            itemId = itemId,
            viewModelDana = viewModelDana,
            navController = navController
        )

        Spacer(modifier = Modifier.height(8.dp))

        // Isi Data
        InfoText(label = "Jumlah :", value = nominal)
        InfoText(label = "Deskripsi :", value = deskripsi)

        Spacer(modifier = Modifier.height(16.dp))
    }
}

@Composable
fun InfoText(label: String, value: String) {
    Column(modifier = Modifier.padding(vertical = 6.dp)) {
        Text(text = label, fontSize = 14.sp)
        Text(text = value, fontWeight = FontWeight.Bold, fontSize = 15.sp)
    }
}
