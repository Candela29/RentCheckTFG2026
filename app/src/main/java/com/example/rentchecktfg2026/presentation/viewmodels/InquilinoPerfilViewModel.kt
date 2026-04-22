package com.example.rentchecktfg2026.presentation.viewmodels

import android.net.Uri
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow


class InquilinoPerfilViewModel : ViewModel() {

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


    fun subirDocumento(uri: Uri, esDni: Boolean) {
        // Aquí en el futuro conectarás con Firebase Storage
        if (esDni) {
            _dniSubido.value = true
        } else {
            _nominaSubida.value = true
        }
        println("Archivo seleccionado en: $uri")
    }

    // Funciones por si quieres rellenar los datos desde otra pantalla
    fun cargarDatosUsuario(nombreUser: String, emailUser: String) {
        _nombre.value = nombreUser
        _email.value = emailUser
    }
}


/*
 init{
        cargarDatos()
    }
fun cargarDatos(){
        val uid=auth.currentUser?.uid
        if (uid!=null){
            viewModelScope.launch {
                try{
                    // 3. Llamamos al repositorio para obtener el objeto User completo
                    val usuario = repository.obtenerDatosUsuario(uid)

                    if (usuario != null) {
                        // 4. Actualizamos los estados con los datos reales de la base de datos
                        _nombre.value = usuario.name
                        _email.value = usuario.email

                        // 5. ¡ESTO ES CLAVE! Si el usuario ya había subido documentos antes,
                        // marcamos los checks como "true" para que no tenga que volver a subirlos.
                        if (usuario.dniUrl?.isNotEmpty() == true) {
                            _dniSubido.value = true
                        }

                        if (usuario.nominaUrl?.isNotEmpty() == true) {
                            _nominaSubida.value = true
                        }
                    }
                }catch (e: Exception){
                    null
                }
            }
        }
    }
    fun subirDocumento(uri: Uri, esDni: Boolean) {
        val uid = auth.currentUser?.uid ?: return
        viewModelScope.launch {
            val carpeta = if (esDni) "dni" else "nomina"
            val campoFirestore = if (esDni) "dniUrl" else "nominaUrl"

            // PASO 1: Subir a Storage y obtener la URL de descarga
            val urlDescarga = repository.subirDocumento(uri, carpeta)

            if (urlDescarga != null) {
                // PASO 2: Actualizar el documento del usuario en Firestore con la URL
                val exito = repository.actualizarDatosUsuario(
                    uid = uid,
                    datos = mapOf(campoFirestore to urlDescarga)
                )
                if (exito) {
                    // Actualizamos el estado visual de la pantalla
                    if (esDni) _dniSubido.value = true else _nominaSubida.value = true
                }
            }

        }
    }*/