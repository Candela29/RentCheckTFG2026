package com.example.rentchecktfg2026.domain.usecase

import com.example.rentchecktfg2026.domain.repositories.UserRepository

class LogoutUseCase(private val repository: UserRepository) {

    //Para el botón de Cerrar Sesión
    suspend operator fun invoke() = repository.cerrarSesion()
}