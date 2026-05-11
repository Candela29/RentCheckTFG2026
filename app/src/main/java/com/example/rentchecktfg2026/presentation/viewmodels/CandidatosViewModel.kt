package com.example.rentchecktfg2026.presentation.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.rentchecktfg2026.data.repositories.UserRepositoryImpl
import com.example.rentchecktfg2026.domain.model.User
import com.example.rentchecktfg2026.domain.repositories.UserRepository
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.launch

class CandidatosViewModel(
    private val repository: UserRepository ,
    private val isPreview: Boolean=false
) : ViewModel() {
    // Esta es la fuente de verdad (siempre tiene todos los datos)
    private var listaCompleta: List<User> = emptyList()

    // Esta es la que observa la UI
    private val _candidatos = MutableLiveData<List<User>>(emptyList())
    val candidatos: LiveData<List<User>> = _candidatos



    init {
        obtenerCandidatosReales()
    }

    private fun obtenerCandidatosReales() {
        // 2. Usamos el modelo User con los nombres de campos nuevos
        viewModelScope.launch {
            try {
                val result = repository.obtenerInquilinos()
                val lista = result.getOrDefault(emptyList())
                // Usamos postValue para asegurar que se actualiza en el hilo principal

                listaCompleta = lista
                _candidatos.postValue(lista)

                Log.d("DEBUG_CANDIDATOS", "Candidatos cargados: ${lista.size}")


            } catch (e: Exception) {
                Log.e("ERROR_CANDIDATOS", "Error: ${e.message}")
                _candidatos.postValue(emptyList())
            }
        }
    }



    fun filterTop() {
        val filtrados = listaCompleta.filter { it.scoring >= 70 }
        _candidatos.value = filtrados
    }

    fun limpiarFiltro() {
        _candidatos.value = listaCompleta
    }
}