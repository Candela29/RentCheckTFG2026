package com.example.rentchecktfg2026.domain.usecase

import android.net.Uri
import com.example.rentchecktfg2026.domain.repositories.DocumentRepository

class UploadDocumentUseCase(private val repository: DocumentRepository) {

    //Sirve para guardar archivos en la API
    suspend operator fun invoke(uri: Uri, type: String, userId: String) = repository.uploadDocument(uri, type, userId)
}