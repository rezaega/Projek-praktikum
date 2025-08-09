package com.example.projek_praktikum.model.viewModel

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import com.example.projek_praktikum.model.request.PenggunaanDanaItem

class ViewModelDahsboard : ViewModel() {
    private val _penggunaanDanaList = mutableStateListOf<PenggunaanDanaItem>()
    val penggunaanDanaList: List<PenggunaanDanaItem> = _penggunaanDanaList

    fun DataPenggunaanDana(
        item: PenggunaanDanaItem,
        onSuccess: () -> Unit,
        onError: (String) -> Unit
    ) {
        try {
            _penggunaanDanaList.add(item)
            onSuccess()
        } catch (e: Exception) {
            onError(e.message ?: "Terjadi kesalahan")
        }
    }
}
