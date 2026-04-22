package com.example.rentchecktfg2026.domain.repositories

import com.example.rentchecktfg2026.domain.model.User
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

class UserRepository(private val firestore: FirebaseFirestore){
    private val usersCollection=firestore.collection("users")

    //Busca el usuario y devuelve el objeto User con su rol
    suspend fun getUserById(id:String): User?{
        return try{
            val documentSnapshot= usersCollection.document(id).get().await()
            documentSnapshot.toObject(User::class.java)
        }catch(e: Exception){
            null
        }
    }

    suspend fun saveUser(user: User): Boolean{
        return try{
            usersCollection.document(user.id).set(user).await()
            true
        }catch (e: Exception){
            e.printStackTrace()
            false
        }
    }

    suspend fun updateScoring(id:String, score:Int): Boolean{
        return try{
            firestore.collection("users").document(id).update("scoring",score).await()
            true
        }catch (e: Exception){
            e.printStackTrace()
            false
        }
    }


}