package com.example.rentchecktfg2026.data.repositories

import com.example.rentchecktfg2026.domain.model.Property
import com.example.rentchecktfg2026.domain.repositories.PropertyRepository
import com.example.rentchecktfg2026.network.ApiService
import com.example.rentchecktfg2026.network.RetrofitClient

class PropertyRepositoryImpl(
    private val apiService: ApiService = RetrofitClient.instance
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
}