package com.example.projek_praktikum.screen


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.projek_praktikum.model.request.PenggunaanDanaItem
import com.example.projek_praktikum.model.viewModel.ViewModelDana


@Composable
fun InputPenggunaanDana(navController: NavController, viewModelDana: ViewModelDana = viewModel(),
                        id: String? = null,
                        initNominal: Int = 0,
                        initDeskripsi: String = "",
                        isEdit: Boolean = false) {

    var nominal by remember { mutableStateOf(initNominal.toString()) }
    var deskripsi by remember { mutableStateOf(initDeskripsi) }
    var errorMessage by remember { mutableStateOf("") }
    val context = LocalContext.current

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
                text = (if (isEdit) "Edit Penggunaan Data" else "Input Penggunaan Data"),
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Input Fields
        InputField(
            label = "nominal",
            value = nominal,
            onValueChange = { nominal = it })
        InputField(
            label = "Deskripsi",
            value = deskripsi,
            onValueChange = { deskripsi = it },
            isMultiLine = true)

        Spacer(modifier = Modifier.height(24.dp))

        if (errorMessage.isNotEmpty()) {
            Text(text = errorMessage, color = Color.Red)
            Spacer(modifier = Modifier.height(8.dp))
        }

        // Tombol Simpan
        Button(
            onClick = {
                if (nominal.isNotBlank() && deskripsi.isNotBlank()) {
                    val nominalValue = nominal.toIntOrNull()
                    if (nominalValue != null) {
                        if (isEdit && id != null) {
                            // UPDATE
                            viewModelDana.updateItemApi(
                                context = context,
                                itemId = id,
                                nominal = nominalValue,
                                deskripsi = deskripsi,
                                onSuccess = { navController.navigate("home"){
                                    popUpTo("home"){
                                        inclusive = true}
                                } },
                                onError = { errorMessage = it }
                            )
                        } else {
                            // TAMBAH BARU
                            val item = PenggunaanDanaItem(nominalValue, deskripsi)
                            viewModelDana.DataPenggunaanDana(
                                context,
                                item,
                                onSuccess = {
                                    errorMessage = ""
                                    navController.popBackStack()
                                },
                                onError = { errorMessage = it }
                            )
                        }
                    } else {
                        errorMessage = "Nominal harus berupa angka"
                    }
                } else {
                    errorMessage = "Isi semua field terlebih dahulu"
                }
            },
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFFC107)),
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(if (isEdit) "Edit" else "Simpan", color = Color.Black, fontWeight = FontWeight.Bold)
        }
    }
}

@Composable
fun InputField(
    label: String,
    value: String,
    onValueChange: (String) -> Unit,
    isMultiLine: Boolean = false
) {
    Column(modifier = Modifier.fillMaxWidth().padding(vertical = 6.dp)) {
        Text(text = label, fontWeight = FontWeight.SemiBold, fontSize = 14.sp)
        OutlinedTextField(
            value = value,
            onValueChange = onValueChange,
            modifier = Modifier.fillMaxWidth(),
            singleLine = !isMultiLine,
            maxLines = if (isMultiLine) 5 else 1,
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = Color.Gray,
                unfocusedBorderColor = Color.LightGray,
                cursorColor = Color.Black
            )
        )
    }
}
