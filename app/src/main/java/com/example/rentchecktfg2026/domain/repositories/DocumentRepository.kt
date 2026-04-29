package com.example.rentchecktfg2026.domain.repositories

import android.net.Uri
import com.example.rentchecktfg2026.domain.model.Document

interface DocumentRepository {

    //
    suspend fun uploadToStorage(uri: Uri, type: String): String?
    suspend fun registerInApi(document: Document): Result<Document>
    suspend fun getDocumentsByUser(userId: String): Result<List<Document>>
}