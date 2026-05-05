package com.example.rentchecktfg2026.domain.repositories

import com.example.rentchecktfg2026.domain.model.Application

interface ApplicationRepository {

    //
    suspend fun applyToProperty(application: Application): Result<Application>
    suspend fun getCandidates(propertyId: Long): Result<List<Application>>
    suspend fun updateStatus(id: Long, status: String): Result<Application>
}