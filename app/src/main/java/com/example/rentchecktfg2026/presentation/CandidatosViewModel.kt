package com.example.tfg.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.tfg.domain.model.Candidato

class CandidatosViewModel : ViewModel() {
    private val _candidatos = MutableLiveData<List<Candidato>>()
    val candidatos: LiveData<List<Candidato>> = _candidatos

    init {
        loadDummyData()
    }

    private fun loadDummyData() {
        _candidatos.value = listOf(
            Candidato("Candela García", "candela.g@email.com", 85, "Indefinido"),
            Candidato("Sandra Cascos", "sandra.c@email.com", 45, "Temporal"),
            Candidato("Any Fiallos", "any.f@email.com", 72, "Autónomo"),
            Candidato("Juan Pérez", "juan@email.com", 30, "Prácticas")
        )
    }

    fun filterTop() {
        _candidatos.value = _candidatos.value?.filter { it.puntuacion >= 70 }
    }
}