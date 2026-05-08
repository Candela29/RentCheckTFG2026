package com.example.rentchecktfg2026.domain.usecase

import com.example.rentchecktfg2026.domain.repositories.PropertyRepository

class GetPropertiesByOwner(private val repository: PropertyRepository) {

    //Para que el inquilino pueda ver solo sus pisos
    suspend operator fun invoke(ownerId: String) = repository.getPropertiesByOwner(ownerId)
}