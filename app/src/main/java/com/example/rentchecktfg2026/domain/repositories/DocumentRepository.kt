package com.example.rentchecktfg2026.domain.repositories

import android.net.Uri
import com.example.rentchecktfg2026.domain.model.Document

interface DocumentRepository {

    //Repositorio de Document

    //Para subir el documento al servidor de Java
    suspend fun uploadDocument(uri: Uri, type: String, userId: String): Result<Document>
    suspend fun getDocumentsByUser(userId: String): Result<List<Document>>

    //Para que la inmobiliaria pueda cambiar el estado del documento
    suspend fun updateDocumentStatus(id: Long, status: String, comment: String?): Result<Document>
}