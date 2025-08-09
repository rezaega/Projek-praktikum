package com.example.projek_praktikum.model.viewModel


import android.content.Context
import android.util.Log
import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.projek_praktikum.model.request.PenggunaanDanaItem
import com.example.projek_praktikum.model.response.HomeData
import com.example.projek_praktikum.model.response.HomeResponse
import com.example.projek_praktikum.service.api.ApiClient
import com.example.projek_praktikum.utils.PreferenceManager
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import org.json.JSONObject
import retrofit2.HttpException
import java.io.IOException




class ViewModelDana : ViewModel() {

    private val _penggunaanDanaList = mutableStateListOf<HomeData>()
    val penggunaanDanaList: List<HomeData> = _penggunaanDanaList

    private val _selectedItem = MutableStateFlow<HomeData?>(null)
    val selectedItem: StateFlow<HomeData?> = _selectedItem

    fun selectedItem(item: HomeData) {
        _selectedItem.value = item
    }

    fun DataPenggunaanDana(
        context: Context,
        item: PenggunaanDanaItem,
        onSuccess: () -> Unit,
        onError: (String) -> Unit
    ) {
        viewModelScope.launch {
            try {
                val response = ApiClient.instance.PenggunaanDana(
                    token = ("Bearer " + PreferenceManager.getToken(context)) ?: "",
                    nominal = item.nominal,
                    deskripsi = item.deskripsi
                )
                if (response.isSuccessful) {
                    // Refresh list setelah tambah data
                    getPenggunaanDanaFromApi()
                    onSuccess()
                } else {
                    val bodyError = response.errorBody()?.string()
                    val errorMessage = JSONObject(bodyError).optString("message","terjadi kesalahan")
                    onError(errorMessage)
                }
            } catch (e: IOException) {
                onError("Network error: ${e.message}")
            } catch (e: HttpException) {
                onError("HTTP error: ${e.message}")
            }
        }
    }

    fun getPenggunaanDanaFromApi(token: String? = null) {
        viewModelScope.launch {
            try {
                val response = ApiClient.instance.getPenggunaanDana(token)
                if (response.isSuccessful) {
                    _penggunaanDanaList.clear()
                    response.body()?.let {
                        _penggunaanDanaList.addAll(it.data)
                    }
                }
            } catch (e: Exception) {
                // Optional: log atau tampilkan error
            }
        }
    }
    fun deleteItemApi(
        context: Context,
        itemId: String,
        onSuccess: () -> Unit,
        onError: (String) -> Unit
    ) {
        viewModelScope.launch {
            try {
                Log.d("ViewModel", "Deleting item with ID: $itemId")
                val response = ApiClient.instance.deletePenggunaanDana(
                    token = "Bearer " + PreferenceManager.getToken(context),
                    id = itemId
                )

                if (response.isSuccessful) {
                    // refresh list dari API
                    getPenggunaanDanaFromApi("Bearer " + PreferenceManager.getToken(context))
                    onSuccess()
                } else {
                    val bodyError = response.errorBody()?.string()
                    val errorMessage = JSONObject(bodyError)
                        .optString("message", "Terjadi kesalahan")
                    onError(errorMessage)
                }
            } catch (e: IOException) {
                onError("Network error: ${e.message}")
            } catch (e: HttpException) {
                onError("HTTP error: ${e.message}")
            }
        }
    }

    fun updateItemApi(
        context: Context,
        itemId: String,
        nominal: Int,
        deskripsi: String,
        onSuccess: () -> Unit,
        onError: (String) -> Unit
    ) {
        viewModelScope.launch {
            try {
                Log.d("ViewModel", "Updating item with ID: $itemId")
                Log.d("ViewModel", "Updating item with nominal: $nominal")
                Log.d("ViewModel", "Updating item with deskripsi: $deskripsi")

                val response = ApiClient.instance.updatePenggunaanDana(
                    token = "Bearer " + PreferenceManager.getToken(context),
                    id = itemId,
                    nominal = nominal,
                    deskripsi = deskripsi
                )

                if (response.isSuccessful) {
                    getPenggunaanDanaFromApi("Bearer " + PreferenceManager.getToken(context))
                    onSuccess()
                } else {
                    val bodyError = response.errorBody()?.string()
                    val errorMessage = JSONObject(bodyError)
                        .optString("message", "Terjadi kesalahan")
                    onError(errorMessage)
                }
            } catch (e: IOException) {
                onError("Network error: ${e.message}")
            } catch (e: HttpException) {
                onError("HTTP error: ${e.message}")
            }
        }
    }


}
