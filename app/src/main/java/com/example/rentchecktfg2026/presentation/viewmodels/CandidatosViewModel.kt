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

        firestore.collection("inquilinos")
            .orderBy("scoring", Query.Direction.DESCENDING) // Los mejores primero
            .addSnapshotListener { snapshot, error ->
                if (error != null) {
                    // Manejar error si fuera necesario
                    return@addSnapshotListener
                }

                if (snapshot != null) {
                    val usuarios = snapshot.toObjects(User::class.java)
                    listaOriginal = usuarios
                    _candidatos.value = usuarios
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