package com.example.rentchecktfg2026.domain.usecase

import com.example.rentchecktfg2026.domain.model.User
import com.example.rentchecktfg2026.domain.repositories.UserRepository

class SyncUserUseCase(private val repository: UserRepository) {

    //Para asegurar que cuando alguien se registra en Firebase, sus datos también
    // se guardan en la base de datos
    suspend operator fun invoke(user: User) = repository.syncUserWithApi(user)
}