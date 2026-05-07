package com.example.rentchecktfg2026.domain.usecase

import com.example.rentchecktfg2026.domain.model.Property
import com.example.rentchecktfg2026.domain.repositories.PropertyRepository

class CreatePropertyUseCase(private val repository: PropertyRepository) {

    //Para subir un piso nuevo
    suspend operator fun invoke(property: Property) = repository.createProperty(property)
}