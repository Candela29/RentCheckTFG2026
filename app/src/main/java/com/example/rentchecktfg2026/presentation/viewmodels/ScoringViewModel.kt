package com.example.rentchecktfg2026.presentation.viewmodels

import android.util.Log
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
        Log.d("SCORING", "Score a guardar: ${result.total}")


        viewModelScope.launch {
            val id = auth.currentUser?.uid ?: return@launch
            Log.d("SCORING", "UID: $id")

            val usuarioActual = repository.getUserById(id).getOrNull()
            Log.d("SCORING", "Nombre de Firestore: ${usuarioActual?.name}")
            Log.d("SCORING", "Scoring actual: ${usuarioActual?.scoring}")

            val nuevoInquilino = User(
                id = id,
                name = usuarioActual?.name ?: "",  // ← nombre real de Firestore
                email = usuarioActual?.email ?: auth.currentUser?.email ?: "",
                scoring = result.total,
                contractType = contrato,
                role = "INQUILINO",
                telefono = usuarioActual?.telefono ?: ""
            )
            Log.d("SCORING", "Objeto a guardar: scoring=${nuevoInquilino.scoring}, name=${nuevoInquilino.name}")
            val ok = repository.guardarScoring(nuevoInquilino)
            Log.d("SCORING", "Resultado: $ok")
            repository.updateScoring(id, result.total)

        }
    }

    fun enviarExpediente (score:Int, contrato:String){
        val uid = auth.currentUser?.uid ?: return
        viewModelScope.launch {
            val usuarioActual = repository.getUserById(uid).getOrNull()
            val objetoInquilino = User(
                id = uid,
                name = usuarioActual?.name ?: "",  // ← nombre real de Firestore
                email = usuarioActual?.email ?: auth.currentUser?.email ?: "",
                scoring = score,
                contractType = contrato,
                role = "INQUILINO",
                telefono = usuarioActual?.telefono ?: "",
                description = "Perfil enviado desde el test de solvencia"
            )
            repository.guardarScoring(objetoInquilino)
            repository.updateScoring(uid, score)
        }
    }

}