package com.example.rentchecktfg2026.presentation.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.rentchecktfg2026.domain.model.User

class CandidatosViewModel : ViewModel() {
    private val _candidatos = MutableLiveData<List<User>>()
    val candidatos: LiveData<List<User>> = _candidatos

    init {
        loadDummyData()
    }

    private fun loadDummyData() {
        // 2. Usamos el modelo User con los nombres de campos nuevos
        _candidatos.value = listOf(
            User(name = "Candela García", email = "candela.g@email.com", scoring = 85, contractType = "Indefinido", role = "INQUILINO"),
            User(name = "Sandra Cascos", email = "sandra.c@email.com", scoring = 45, contractType = "Temporal", role = "INQUILINO"),
            User(name = "Any Fiallos", email = "any.f@email.com", scoring = 72, contractType = "Autónomo", role = "INQUILINO"),
            User(name = "Juan Pérez", email = "juan@email.com", scoring = 30, contractType = "Prácticas", role = "INQUILINO")
        )
    }

    // 3. Ojo aquí: cambiamos 'it.puntuacion' por 'it.scoring'
    fun filterTop() {
        _candidatos.value = _candidatos.value?.filter {it.scoring  >= 70 }//Si el scoring es nulo, haz como si fuera un 0
    }
}