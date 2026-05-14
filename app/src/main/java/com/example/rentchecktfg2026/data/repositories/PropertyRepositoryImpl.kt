package com.example.rentchecktfg2026.data.repositories

import android.util.Log
import com.example.rentchecktfg2026.domain.model.Property
import com.example.rentchecktfg2026.domain.repositories.PropertyRepository
import com.example.rentchecktfg2026.network.ApiService
import com.example.rentchecktfg2026.network.RetrofitClient
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.firestoreSettings
import kotlinx.coroutines.tasks.await

class PropertyRepositoryImpl(
    private val apiService: ApiService = RetrofitClient.instance,
    private val firestore: FirebaseFirestore
): PropertyRepository {

    override suspend fun getAllProperties(): Result<List<Property>> {
        return try {
            val response = apiService.getProperties()
            if (response .isSuccessful) {
                Result.success(response.body() ?: emptyList())
            } else {
                Result.failure(Exception("Error al obtener propiedades"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun getPropertiesByOwner(ownerId: String): Result<List<Property>> {
        return try {
            val response = apiService.getPropertiesByOwner(ownerId)
            if (response .isSuccessful) {
                Result.success(response.body() ?: emptyList())
            } else {
                Result.failure(Exception("Error al obtener tus propiedades"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun createProperty(property: Property): Result<Property> {
        return try {
            val response = apiService.createProperty(property)
            if (response .isSuccessful && response.body() != null) {
                Result.success(response.body() !!)
            } else {
                Result.failure(Exception("No se pudo crear la propiedad"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
   override suspend fun deleteProperty(id:String):Boolean{
       return try {
           // Llamamos a la API de Java en lugar de a Firebase
           val response = apiService.deleteProperty(id)

           if (response.isSuccessful) {
               Log.d("PropertyRepository", "Eliminado de la base de datos SQL con éxito")
               true
           } else {
               Log.e("PropertyRepository", "Error al borrar: ${response.code()}")
               false
           }
       } catch (e: Exception) {
           Log.e("PropertyRepository", "Error de red", e)
           false
       }
    }
}