package com.example.rentchecktfg2026.domain.usecase

import com.example.rentchecktfg2026.domain.repositories.DocumentRepository

class GetDocumentsUseCase(private val repository: DocumentRepository) {

    //Para que el usuario pueda ver qué documentos ha subido y su estado
    suspend operator fun invoke(userId: String) = repository.getDocumentsByUser(userId)
}