package com.example.rentchecktfg2026.domain.usecase

import com.example.rentchecktfg2026.domain.repositories.PropertyRepository

class GetPropertiesUseCase(private val repository: PropertyRepository) {

    //Para que el inquilino vea la lista de pisos
    suspend operator fun invoke() = repository.getAllProperties()
}