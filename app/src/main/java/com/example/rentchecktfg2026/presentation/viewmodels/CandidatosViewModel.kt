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
    fun eliminarCandidato(id:String){
        viewModelScope.launch {
            val exito = repository.deleteUSer(id)
            if (exito) {
                // Filtramos la lista actual para quitar al que acabamos de borrar
                val listaActualizada = listaOriginal.filter { it.id != id }

                // Actualizamos ambas listas para que el cambio sea permanente en la UI
                listaOriginal = listaActualizada
                _candidatos.value = listaActualizada

                Log.d("CANDIDATOS", "UI actualizada tras borrado")
            }
        }

    }

    fun ocultarCandidatoDeLaLista(id:String) {
        viewModelScope.launch {
            try {
                // En lugar de borrar el documento, solo marcamos una propiedad 'oculto'
                firestore.collection("inquilinos").document(id)
                    .update("oculto", true) // Añadimos este campo en Firebase
                    .await()

                Log.d("FIREBASE", "Candidato marcado como oculto")
                // Como tienes un SnapshotListener en 'obtenerInquilinos',
                // la lista se actualizará sola si añades el filtro allí.
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