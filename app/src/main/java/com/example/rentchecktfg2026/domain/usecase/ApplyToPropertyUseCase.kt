package com.example.rentchecktfg2026.domain.usecase

import com.example.rentchecktfg2026.domain.model.Application
import com.example.rentchecktfg2026.domain.repositories.ApplicationRepository

class ApplyToPropertyUseCase(private val repository: ApplicationRepository) {

    //Cuando el inquilino aplique a un piso
    suspend operator fun invoke(application: Application) = repository.applyToProperty(application)
}