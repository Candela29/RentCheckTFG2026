package com.example.rentchecktfg2026.domain.usecase

import com.example.rentchecktfg2026.domain.repositories.UserRepository

class GetInquilinosUseCase(private val repository: UserRepository) {

    //Para que la inmobiliaria vea la lista de personas que se han registrado y el scoring
    suspend operator fun invoke() = repository.obtenerInquilinos()
}