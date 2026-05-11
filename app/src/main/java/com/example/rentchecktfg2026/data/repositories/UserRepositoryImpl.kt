package com.example.rentchecktfg2026.data.repositories

import android.net.Uri
import android.util.Log
import com.example.rentchecktfg2026.domain.model.Property
import com.example.rentchecktfg2026.domain.model.User
import com.example.rentchecktfg2026.domain.repositories.UserRepository
import com.example.rentchecktfg2026.network.ApiService
import com.example.rentchecktfg2026.network.RetrofitClient
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import kotlinx.coroutines.tasks.await

class UserRepositoryImpl(
    private val api: ApiService = RetrofitClient.instance,
    private val auth: FirebaseAuth = FirebaseAuth.getInstance(),
    private val firestore: FirebaseFirestore = FirebaseFirestore.getInstance()
) : UserRepository {

    override suspend fun getUserById(id:String): Result<User> {
        return try{
            val doc = firestore.collection("users").document(id).get().await()
            val user = doc.toObject(User::class.java)
            if(user != null) Result.success(user) else Result.failure(Exception("No existe"))
        } catch(e: Exception){
            Result.failure(e)
        }
    }

    override suspend fun syncUserWithApi(user: User): Result<User> {
        return try {
            val response = api.syncUser(user)
            if(response.isSuccessful && response.body() != null) Result.success(response.body()!!)
            else Result.failure(Exception("Error de sincronización con MySQL"))
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun saveUser(user: User): Result<Boolean> {
        return try{
            firestore.collection("users").document(user.id).set(user).await()
            Result.success(true)
        }catch (e: Exception){
            Result.failure(e)
        }
    }

    override suspend fun updateScoring(id:String, score:Int): Result<Boolean> {
        return try{
            firestore.collection("users").document(id).update("scoring",score).await()
            Result.success(true)
        }catch (e: Exception){
            Result.failure(e)
        }
    }

    override suspend fun obtenerInquilinos(): Result<List<User>> {
        return try {
            val snapshot = firestore.collection("inquilinos")
                .get().await()
            Result.success(snapshot.toObjects(User::class.java))
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun guardarScoring(user: User): Boolean{
        return try{
            firestore.collection("inquilinos").document(user.id)
                .set(user)
                .await()
            true
        }catch (e: Exception){
            e.printStackTrace()
            false
        }
    }

    override suspend fun calcularScoringApi(
        id:String, ingresos: Double, alquiler: Double, contrato:String, antiguedad: Int
    ): Result<User> {
        return try{
            val response= api.calculateScoring(id,ingresos,alquiler,contrato, antiguedad)
            if (response.isSuccessful) Result.success(response.body()!!)
            else Result.failure(Exception("Error en API"))
        } catch (e: Exception) {
            Result.failure(e)
        }
        }


    override fun cerrarSesion(){
        auth.signOut()
    }


}