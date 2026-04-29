package com.example.rentchecktfg2026.domain.repositories

import com.example.rentchecktfg2026.domain.model.Property

interface PropertyRepository {

    suspend fun getAllProperties(): Result<List<Property>>
    suspend fun getPropertiesByOwner(ownerId: String): Result<List<Property>>
    suspend fun createProperty(property: Property): Result<Property>
}