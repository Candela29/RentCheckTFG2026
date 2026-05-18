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
import kotlinx.coroutines.tasks.await

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

            repository.obtenerInquilinos().collect { lista ->
                //FILTRAR: Solo mostramos los que NO están ocultos
                val listaFiltrada = lista.filter { it.oculto != true }

                // ORDENAR: Favoritos primero, luego puntuación
                val listaOrdenada = listaFiltrada.sortedWith(
                    compareByDescending<User> { it.favorito }
                        .thenByDescending { it.scoring }
                )

                listaOriginal = listaOrdenada
                _candidatos.value = listaOrdenada
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


    fun ocultarCandidatoDeLaLista(id:String) {
        viewModelScope.launch {
            try {
                firestore.collection("inquilinos").document(id)
                    .update("oculto", true) // Cambiamos el campo en Firebase
                    .await()
                Log.d("FIREBASE", "Candidato $id marcado como oculto")
            } catch (e: Exception) {
                Log.e("ERROR", "No se pudo ocultar: ${e.message}")
            }
        }
    }
    fun toggleFavorito(user: User) {
        viewModelScope.launch {
            val nuevoEstado = !user.favorito
            firestore.collection("inquilinos").document(user.id)
                .update("favorito", nuevoEstado) // <--- Actualiza con el nuevo nombre
                .await()
        }
    }
}