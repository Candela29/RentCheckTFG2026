package com.example.rentchecktfg2026.presentation.viewmodels

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow


class PropiedadViewModel: ViewModel() {
    private  val _titulo= MutableStateFlow("")
    val titulo: StateFlow<String> = _titulo.asStateFlow()

    private val _direccion=MutableStateFlow("")
    val direccion: StateFlow<String> = _direccion.asStateFlow()

    private val _precio = MutableStateFlow("")
    val precio: StateFlow<String> = _precio.asStateFlow()

    private val _habitaciones = MutableStateFlow("")
    val habitaciones : StateFlow<String> = _habitaciones.asStateFlow()

    private val _tieneAscensor= MutableStateFlow(false)
    val tieneAscensor: StateFlow<Boolean> = _tieneAscensor.asStateFlow()

    private val _estaAmueblado = MutableStateFlow(false)
    val estaAmueblado: StateFlow<Boolean> = _estaAmueblado.asStateFlow()

    private val _tieneGaraje= MutableStateFlow(false)
    val tieneGaraje : StateFlow<Boolean> = _tieneGaraje.asStateFlow()

    fun setTitulo(nuevo: String) { _titulo.value = nuevo }
    fun setDireccion(nuevo: String) { _direccion.value = nuevo }
    fun setPrecio(nuevo: String) { _precio.value = nuevo }
    fun setHabitaciones(nuevo: String) { _habitaciones.value = nuevo }

    fun toggleAscensor(valor: Boolean) {
        _tieneAscensor.value = valor
    }

    fun toggleAmueblado(valor: Boolean) {
        _estaAmueblado.value = valor
    }
    fun toggleGaraje(valor: Boolean){
        _tieneGaraje.value= valor
    }
    fun registrarPropiedad() {
        val tituloFinal = _titulo.value
        val precioFinal = _precio.value

        if (tituloFinal.isNotEmpty() && precioFinal.isNotEmpty()) {
            // Aquí es donde en el futuro llamarías al repositorio
            println("Propiedad registrada: $tituloFinal por $precioFinal €")
        } else {
            println("Error: Faltan datos obligatorios")
        }
    }

}