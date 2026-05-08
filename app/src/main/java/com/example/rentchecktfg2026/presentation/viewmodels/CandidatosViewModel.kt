package com.example.rentchecktfg2026.presentation.viewmodels

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
                // Usamos postValue para asegurar que se actualiza en el hilo principal
                _candidatos.postValue(result.getOrDefault(emptyList()))
            } catch (e: Exception) {
                _candidatos.postValue(emptyList())
            }
        }
    }






    fun filterTop() {
        val listaActual = _candidatos.value.orEmpty()
        _candidatos.value = listaActual.filter { it.scoring >= 70 }
    }
}