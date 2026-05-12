package com.example.rentchecktfg2026.presentation.viewmodels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.rentchecktfg2026.data.repositories.UserRepositoryImpl
import com.example.rentchecktfg2026.domain.model.User
import com.example.rentchecktfg2026.domain.repositories.UserRepository
import com.example.rentchecktfg2026.presentation.ui.utils.ScoringResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.launch

class ScoringViewModel(
    private val repository : UserRepository
): ViewModel() {
    private val auth = FirebaseAuth.getInstance()

    var resultadoScoring by mutableStateOf<ScoringResult?>(null)

    fun guardarResultadoScoring(score: Int) {

        val id = auth.currentUser?.uid
        val email = auth.currentUser?.email ?: ""
        val nombre = auth.currentUser?.displayName ?: "Candidato"


        if (id != null) {
            viewModelScope.launch {
                val objetoInquilino = User(
                    id = id,
                    name = nombre,
                    email = email,
                    scoring = score,
                    telefono = "",
                    role = "INQUILINO"
                )
             repository.guardarScoring(objetoInquilino)

                repository.updateScoring(id,score)
            }
        }
    }
    fun calcularYGuardar(s: Double, a: Double, contrato: String, ant: Int, result: ScoringResult) {
        resultadoScoring=result

        viewModelScope.launch {
            val id = auth.currentUser?.uid ?: return@launch
            // Seguimos mandando el total a la API y Firebase
            repository.calcularScoringApi(id, s, a, contrato, ant)
            repository.updateScoring(id, result.total)
            val nuevoInquilino = User(
                id = id,
                name = auth.currentUser?.displayName ?: "Candidato",
                email = auth.currentUser?.email ?: "",
                scoring = result.total,    // Usamos el total calculado
                contractType = contrato,   // Pasamos el contrato del formulario
                role = "INQUILINO",
                emailVerified = auth.currentUser?.isEmailVerified ?: false,
                documentExpiryAt = 0L
            )
            // Guardar en la colección 'inquilinos'
            repository.guardarScoring(nuevoInquilino)
            repository.updateScoring(id, result.total)
        }
    }

    fun enviarExpediente (score:Int, contrato:String){
        val user= auth.currentUser
        if(user!=null){
            viewModelScope.launch {
                val objetoInquilino= User(
                    id=user.uid,
                    name=user.displayName?:"",
                    email =user.email?:"",
                    scoring = score,
                    contractType = contrato,
                    role="INQUILINO",
                    description = "Perfil enviado desde el test de solvencia"
                )
                //guardamos en la coleccion que lee la inmobiliaria
                repository.guardarScoring(objetoInquilino)

                //actualizamos perfil general
                repository.updateScoring(user.uid,score)

            }
        }
    }

}