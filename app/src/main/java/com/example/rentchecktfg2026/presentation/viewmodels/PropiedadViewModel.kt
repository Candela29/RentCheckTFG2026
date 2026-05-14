package com.example.rentchecktfg2026.presentation.viewmodels

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.rentchecktfg2026.data.repositories.PropertyRepositoryImpl
import com.example.rentchecktfg2026.data.repositories.UserRepositoryImpl
import com.example.rentchecktfg2026.domain.model.Property
import com.example.rentchecktfg2026.domain.repositories.PropertyRepository
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch


class PropiedadViewModel (
    private val repository: PropertyRepository
): ViewModel() {


    private val _titulo = MutableStateFlow("")
    val titulo: StateFlow<String> = _titulo.asStateFlow()


    private val _precio = MutableStateFlow("")
    val precio: StateFlow<String> = _precio.asStateFlow()

    private val _habitaciones = MutableStateFlow("")
    val habitaciones: StateFlow<String> = _habitaciones.asStateFlow()

    private val _tieneAscensor = MutableStateFlow(false)
    val tieneAscensor: StateFlow<Boolean> = _tieneAscensor.asStateFlow()

    private val _estaAmueblado = MutableStateFlow(false)
    val estaAmueblado: StateFlow<Boolean> = _estaAmueblado.asStateFlow()

    private val _tieneGaraje = MutableStateFlow(false)
    val tieneGaraje: StateFlow<Boolean> = _tieneGaraje.asStateFlow()

    private val _tipovivienda = MutableStateFlow("Medio")
    val tipoVivienda: StateFlow<String> = _tipovivienda

    private val _listaPropiedades = MutableStateFlow<List<Property>>(emptyList())
    val listaPropiedades: StateFlow<List<Property>> = _listaPropiedades

    private val _userRole = MutableStateFlow("inmobiliaria")
    val userRole: StateFlow<String> = _userRole.asStateFlow()

    var propiedadSeleccionada by mutableStateOf<Property?>(null)
    private val _metros = MutableStateFlow("")
    val metros: StateFlow<String> = _metros.asStateFlow()

    private val _tienePiscina = MutableStateFlow(false)
    val tienePiscina: StateFlow<Boolean> = _tienePiscina.asStateFlow()

    private val _tieneAire = MutableStateFlow(false)
    val tieneAire: StateFlow<Boolean> = _tieneAire.asStateFlow()

    private val _tieneCalefaccion = MutableStateFlow(false)
    val tieneCalefaccion: StateFlow<Boolean> = _tieneCalefaccion.asStateFlow()
    fun setTitulo(valor: String) {
        _titulo.value = valor
    }

    fun setPrecio(valor: String) {
        _precio.value = valor
    }

    fun setHabitaciones(valor: String) {
        _habitaciones.value = valor
    }

    fun setTipoVivienda(valor: String) {
        _tipovivienda.value = valor
    }

    fun toggleAscensor(valor: Boolean) {
        _tieneAscensor.value = valor
    }

    fun toggleAmueblado(valor: Boolean) {
        _estaAmueblado.value = valor
    }

    fun toggleGaraje(valor: Boolean) {
        _tieneGaraje.value = valor
    }

    fun setMetros(valor: String) { _metros.value = valor }

    fun togglePiscina(valor: Boolean) { _tienePiscina.value = valor }

    fun toggleAire(valor: Boolean) { _tieneAire.value = valor }

    fun toggleCalefaccion(valor: Boolean) { _tieneCalefaccion.value = valor }

    init {
        cargarMisPropiedades()
    }
    fun registrarPropiedad(onSuccess: () -> Unit) {
        Log.d("PROPIEDAD", "titulo: ${titulo.value}, precio: ${precio.value}")
        if (titulo.value.isEmpty() || precio.value.isEmpty()){
            Log.e("PROPIEDAD", "Faltan datos obligatorios")
            return
        }
        viewModelScope.launch {
            try {
                //Crear el objeto con los datos actuales
                val nuevaPropiedad = Property(
                    title = titulo.value,
                    price = precio.value.toDoubleOrNull() ?: 0.0, // Property usa Double
                    rooms = habitaciones.value.toIntOrNull() ?: 0,
                    hasElevator = tieneAscensor.value,
                    isFurnished = estaAmueblado.value,
                    hasGarage = tieneGaraje.value,
                    propertyType = tipoVivienda.value,
                    size = _metros.value.toIntOrNull() ?: 0,
                    hasPool = _tienePiscina.value,         // <-- NUEVO
                    hasAirConditioning = _tieneAire.value, // <-- NUEVO
                    hasHeating = _tieneCalefaccion.value,
                )
                Log.d("DEBUG_JSON", "Enviando Tipo: ${tipoVivienda.value} y Metros: ${_metros.value}")

                Log.d("PROPIEDAD", "Guardando: $nuevaPropiedad")
                //Llamar al repositorio para guardar en Firestore
                val resultado = repository.createProperty(nuevaPropiedad)
                Log.d("PROPIEDAD", "Resultado: ${resultado.isSuccess}")
                Log.d("PROPIEDAD", "Error: ${resultado.exceptionOrNull()?.message}")

                if (resultado.isSuccess) {

                    // Ejecutamos la navegación hacia atrás solo si hubo éxito
                    onSuccess()
                }
            } catch (e: Exception) {
                Log.e("PROPIEDAD", "Excepción: ${e.message}")
            }
        }
    }



        fun cargarMisPropiedades() {
            viewModelScope.launch {
                // Usamos el repositorio en lugar de llamar a Firestore directo
                val res = repository.getAllProperties()
                _listaPropiedades.value = res.getOrDefault(emptyList())
            }

        }
    fun removePropiedad(id:String){

        viewModelScope.launch {
            val resultado = repository.deleteProperty(id)

            if (resultado) {
                cargarMisPropiedades()
            }
        }
    }

    fun seleccionarPropiedad(property:Property){
        propiedadSeleccionada= property
    }
    }

