package com.example.rentchecktfg2026.presentation.viewmodels

import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.rentchecktfg2026.data.repositories.UserRepositoryImpl
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch


class InquilinoPerfilViewModel(
    private val repository: UserRepositoryImpl= UserRepositoryImpl()
) : ViewModel() {

    // Estado para el Nombre (Vacío por defecto)
    private val _nombre = MutableStateFlow("")
    val nombre: StateFlow<String> = _nombre.asStateFlow()

    // Estado para el Email (Vacío por defecto)
    private val _email = MutableStateFlow("")
    val email: StateFlow<String> = _email.asStateFlow()

    // Estado del DNI (False = no subido)
    private val _dniSubido = MutableStateFlow(false)
    val dniSubido: StateFlow<Boolean> = _dniSubido.asStateFlow()

    // Estado de la Nómina (False = no subida)
    private val _nominaSubida = MutableStateFlow(false)
    val nominaSubida: StateFlow<Boolean> = _nominaSubida.asStateFlow()


    fun subidaDocumento(uri: Uri, esDni: Boolean) {
        // Aquí en el futuro conectarás con Firebase Storage
        val uid= FirebaseAuth.getInstance().currentUser?.uid ?:return
        val tipo =if(esDni)"dni" else "nomina"
        val campoFirestore= if (esDni) "dniUrl" else "nominaUrl"
        viewModelScope.launch {
            //Subir a Storage
            val urlDescarga = repository.subirDocumento(uri,tipo)

            if (urlDescarga != null) {

                // PASO B: Guardamos esa URL en la ficha de Firestore del usuario
                // ¡Aquí es donde usas el otro método!
                val exito = repository.updateDocumentUrl(
                    id = uid,
                    campo = campoFirestore,
                    url = urlDescarga
                )
                if(exito){
                    if(esDni) _dniSubido.value= true else _nominaSubida.value= true
                }
            }
        }

    }

    // Funciones por si quieres rellenar los datos desde otra pantalla
    fun cargarDatosUsuario(nombreUser: String, emailUser: String) {
        val uid = FirebaseAuth.getInstance().currentUser?.uid ?: return
        viewModelScope.launch {
            val usuario = repository.getUserById(uid)
            usuario?.let {
                _nombre.value = it.name
                _email.value = it.email
                // Si la URL no está vacía, marcamos como subido
                _dniSubido.value = it.dniUrl.isNotEmpty()
                _nominaSubida.value = it.nominaUrl.isNotEmpty()
            }
        }
    }
}


