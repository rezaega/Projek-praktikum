package com.example.projek_praktikum.screen

import android.content.Context
import android.widget.Toast
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavController
import com.example.projek_praktikum.model.viewModel.ViewModelDana


@Composable
fun deleteModel(
    showDialog: Boolean,
    onDismiss: () -> Unit,
    itemId: String,
    viewModelDana: ViewModelDana,
    navController: NavController,
    context: Context = LocalContext.current
) {
    if (showDialog) {
        AlertDialog(
            onDismissRequest = onDismiss,
            title = { Text("Konfirmasi") },
            text = { Text("Apakah kamu yakin ingin menghapus item ini?") },
            confirmButton = {
                TextButton(onClick = {
                    viewModelDana.deleteItemApi(
                        context = context,
                        itemId = itemId,
                        onSuccess = {
                            Toast.makeText(context, "Item berhasil dihapus", Toast.LENGTH_SHORT).show()
                            navController.popBackStack() // kembali setelah delete
                        },
                        onError = { error ->
                            Toast.makeText(context, error, Toast.LENGTH_SHORT).show()
                        }
                    )
                    onDismiss()
                }) {
                    Text("Ya")
                }
            },
            dismissButton = {
                TextButton(onClick = onDismiss) {
                    Text("Tidak")
                }
            }
        )
    }
}

