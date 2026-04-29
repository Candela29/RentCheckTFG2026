package com.example.rentchecktfg2026.presentation.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.rentchecktfg2026.domain.model.User
import com.example.rentchecktfg2026.domain.repositories.UserRepository
import kotlinx.coroutines.launch

class CandidatosViewModel(
    private val repository: UserRepository= UserRepository(),
    private val isPreview: Boolean=false
) : ViewModel() {
    private val _candidatos = MutableLiveData<List<User>>()
    val candidatos: LiveData<List<User>> = _candidatos



    init {
        obtenerCandidatosReales()
    }

    private fun obtenerCandidatosReales() {
        // 2. Usamos el modelo User con los nombres de campos nuevos
        viewModelScope.launch {
            val lista = repository.obtenerInquilinos()
            _candidatos.value = lista
        }
    }



    // 3. Ojo aquí: cambiamos 'it.puntuacion' por 'it.scoring'
    fun filterTop() {
        _candidatos.value = _candidatos.value?.filter {it.scoring  >= 70 }//Si el scoring es nulo, haz como si fuera un 0
    }
}