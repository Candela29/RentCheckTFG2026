package com.example.rentchecktfg2026.domain.repositories

import com.example.rentchecktfg2026.domain.model.User

interface UserRepository {

    //Repositorio de User
    suspend fun getUserById(id: String): Result<User>
    suspend fun saveUser(user: User): Result<Boolean>
    suspend fun syncUserWithApi(user: User): Result<User>
    suspend fun updateScoring(id: String, score: Int): Result<Boolean>
    suspend fun obtenerInquilinos(): Result<List<User>>
    fun cerrarSesion()
}