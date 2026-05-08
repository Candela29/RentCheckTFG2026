package com.example.rentchecktfg2026.presentation.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
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
    private val repository: UserRepositoryImpl = UserRepositoryImpl()
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

    init {
        cargarMisPropiedades()
    }
    fun registrarPropiedad(onSuccess: () -> Unit) {
        if (titulo.value.isEmpty() || precio.value.isEmpty()) return
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
                    propertyType = tipoVivienda.value
                )

                //Llamar al repositorio para guardar en Firestore
                val resultado = repository.saveProperty(nuevaPropiedad)

                if (resultado.isSuccess) {

                    // Ejecutamos la navegación hacia atrás solo si hubo éxito
                    onSuccess()
                }
            } catch (e: Exception) {
                Log.e("ERROR_SAVE", "Error al registrar: ${e.message}")
            }
        }
    }

        fun cargarMisPropiedades() {
            val db = FirebaseFirestore.getInstance()

            //Filtramos por el ID del dueño
            db.collection("propiedades").addSnapshotListener { snapshot, _ ->
                if (snapshot != null) {
                    val propiedades = snapshot.toObjects(Property::class.java)
                    _listaPropiedades.value = propiedades
                }
            }

        }
    }

