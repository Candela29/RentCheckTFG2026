package com.example.rentchecktfg2026.domain.usecase

import com.example.rentchecktfg2026.domain.repositories.ApplicationRepository

class UpdateApplicationStatusUseCase(private val repository: ApplicationRepository) {

    //Cuando la inmobiliaria acepta o rechaza una solicitud
    suspend operator fun invoke(id: Long, status: String) = repository.updateStatus(id, status)
}