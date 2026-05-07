package com.example.rentchecktfg2026.domain.usecase

import com.example.rentchecktfg2026.domain.repositories.UserRepository

class GetUserUseCase(private val repository: UserRepository) {

    //Para cargar los datos del usuario o saber su rol al entrar
    suspend operator fun invoke(id: String) = repository.getUserById(id)
}