package com.example.rentchecktfg2026.data.repositories

import com.example.rentchecktfg2026.domain.model.Application
import com.example.rentchecktfg2026.domain.repositories.ApplicationRepository
import com.example.rentchecktfg2026.network.RetrofitClient

class ApplicationRepositoryImpl : ApplicationRepository {
    private val api = RetrofitClient.instance

    override suspend fun applyToProperty(application: Application): Result<Application> {
        return try {
            val response = api.applyToProperty(application)
            if (response .isSuccessful && response.body() != null) {
                Result.success(response.body() !!)
            } else {
                Result.failure(Exception("Error al enviar solicitud"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun getCandidates(propertyId: Long): Result<List<Application>> {
        return try {
            val response = api.getApplicationsByProperties(propertyId)
            if (response .isSuccessful) {
                Result.success(response.body() ?: emptyList())
            } else {
                Result.failure(Exception("Error al obtener candidatos"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun updateStatus(id: Long, status: String): Result<Application> {
        return try {
            val response = api.updateApplicationStatus(id, status)
            if (response.isSuccessful && response.body() != null) {
                Result.success(response.body()!!)
            } else {
                Result.failure(Exception("Error al actualizar estado"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}