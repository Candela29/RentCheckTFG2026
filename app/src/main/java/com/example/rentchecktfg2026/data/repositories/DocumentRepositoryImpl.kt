package com.example.rentchecktfg2026.data.repositories

import android.content.Context
import android.net.Uri
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.File
import com.example.rentchecktfg2026.domain.model.Document
import com.example.rentchecktfg2026.domain.repositories.DocumentRepository
import com.example.rentchecktfg2026.network.RetrofitClient

class DocumentRepositoryImpl(private val context: Context) : DocumentRepository {
    private val api = RetrofitClient.instance

    override suspend fun uploadDocument(uri: Uri, type: String, userId: String): Result<Document> {
        return try {

            //1. Preparar el archivo
            val file = uriToFile(uri, context)
            val requestFile = file.asRequestBody("application/pdf".toMediaTypeOrNull())
            val filePart = MultipartBody.Part.createFormData("file", file.name,requestFile)

            //2. Preparar datos adicionales
            val typePart = type.toRequestBody("text/plain".toMediaTypeOrNull())
            val userPart = userId.toRequestBody("text/plain".toMediaTypeOrNull())

            //3. Llamar a la API de Java
            val response = api.uploadDocument(filePart, typePart, userPart)
            if (response.isSuccessful && response.body() != null) {
                Result.success(response.body()!!)
            } else {
                Result.failure(Exception("Error al subir al servidor local"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun getDocumentsByUser(userId: String): Result<List<Document>> {
        return try {
            val response = api.getDocumentsByUser(userId)
            if (response.isSuccessful) Result.success(response.body() ?: emptyList())
            else Result.failure(Exception("Error al obtener documentos"))
        } catch(e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun updateDocumentStatus(
        id: Long,
        status: String,
        comment: String?
    ): Result<Document> {
        return Result.failure(Exception("No implementado aún"))
    }

    private fun uriToFile(uri: Uri, context: Context): File {
        val file = File(context.cacheDir, "upload_${System.currentTimeMillis()}.pdf")
        context.contentResolver.openInputStream(uri)?.use { input ->
            file.outputStream().use { output -> input.copyTo(output) }
        }
        return file
    }
}