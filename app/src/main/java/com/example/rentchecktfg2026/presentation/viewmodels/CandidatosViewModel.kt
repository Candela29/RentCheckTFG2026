package com.example.rentchecktfg2026.presentation.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.rentchecktfg2026.data.repositories.UserRepositoryImpl
import com.example.rentchecktfg2026.domain.model.User
import com.example.rentchecktfg2026.domain.repositories.UserRepository
import com.example.rentchecktfg2026.network.RetrofitClient
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import kotlinx.coroutines.launch

class CandidatosViewModel(
    private val repository: UserRepository ,

) : ViewModel() {

    private val firestore = FirebaseFirestore.getInstance()
    // Esta es la fuente de verdad (siempre tiene todos los datos)
    private var listaOriginal = listOf<User>()
    // Esta es la que observa la UI
    private val _candidatos = MutableLiveData<List<User>>(emptyList())
    val candidatos: LiveData<List<User>> = _candidatos



    init {
        obtenerCandidatosReales()
    }

    private fun obtenerCandidatosReales() {

        viewModelScope.launch {
            // 1. Usamos Flow para tiempo real
            repository.obtenerInquilinos().collect { lista ->
                // 2. Ordenamos de mayor a menor puntuación
                val listaOrdenada = lista.sortedByDescending { it.scoring }

                listaOriginal = listaOrdenada
                _candidatos.value = listaOrdenada
                Log.d("CANDIDATOS", "Lista actualizada y ordenada: ${lista.size}")
            }
        }
    }



    fun filterTop() {
        val filtrados = listaOriginal.filter { it.scoring >= 70 }
        _candidatos.value = filtrados
    }

    fun limpiarFiltro() {
        _candidatos.value = listaOriginal
    }
}