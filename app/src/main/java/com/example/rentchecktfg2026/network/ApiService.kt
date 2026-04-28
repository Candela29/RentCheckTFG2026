package com.example.rentchecktfg2026.network

import com.example.rentchecktfg2026.domain.model.Application
import com.example.rentchecktfg2026.domain.model.Property
import com.example.rentchecktfg2026.domain.model.User
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    // -- USUARIOS --

    // 1. Sincronizar usuario (Login/Registro)
    @POST("api/users/sync")
    suspend fun syncUser(@Body user: User): Response<User>

    // 2. Para obtener perfil y ver scoring
    @GET("api/users/{id}")
    suspend fun getUser(@Path("id") id: String): Response<User>

    // 3. Calcular scoring
    @POST("api/users/{id}/calculate-scoring")
    suspend fun calculateScoring(
        @Path("id") userId: String,
        @Query("ingresos") ingresos: Double,
        @Query("alquiler") alquiler: Double,
        @Query("tipoContrato") contrato: String,
        @Query("antiguedad") antiguedad: Int
    ): Response<User>


    // -- PROPIEDADES --

    // 4. Obtener todos los pisos
    @GET("api/properties")
    suspend fun getProperties(): Response<List<Property>>

    // 5. Listar pisos de una inmobiliaria específica
    @GET("api/properties/owner/{ownerId}")
    suspend fun getPropertiesByOwner(@Path("ownerId") ownerId: String): Response<List<Property>>

    // 6. Publicar nuevo piso
    @POST("api/properties")
    suspend fun createProperty(@Body property: Property): Response<Property>


    // -- CANDIDATURAS --

    // 7. Crear una solicitud
    @POST("api/applications")
    suspend fun applyToProperty(@Body application: Application): Response<Application>

    // 8. Ver candidatos de un piso
    @GET("api/applications/property/{propertyId}")
    suspend fun getCandidatesByProperty(@Path("propertyId") propertyId: Long): Response<List<Application>>

    // 9. Cambiar estado
    @PATCH("api/applications/{id}/status")
    suspend fun updateApplicationStatus(
        @Path("id") id: Long,
        @Query("status") status: String
    ): Response<Application>
}