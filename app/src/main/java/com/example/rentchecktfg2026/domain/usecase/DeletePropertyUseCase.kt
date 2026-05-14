package com.example.rentchecktfg2026.domain.usecase

import com.example.rentchecktfg2026.domain.model.Property
import com.example.rentchecktfg2026.domain.repositories.PropertyRepository

class DeletePropertyUseCase(private val repository: PropertyRepository) {

    suspend operator fun invoke(propertyId:String):Boolean{
        return repository.deleteProperty(propertyId)
    }
}