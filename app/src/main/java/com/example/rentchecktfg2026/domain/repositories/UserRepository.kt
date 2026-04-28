package com.example.rentchecktfg2026.domain.repositories

import android.net.Uri
import com.example.rentchecktfg2026.domain.model.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import kotlinx.coroutines.tasks.await

class UserRepository(
        private val firestore: FirebaseFirestore = FirebaseFirestore.getInstance(),
        private val auth: FirebaseAuth = FirebaseAuth.getInstance(),
        private val storage: FirebaseStorage = FirebaseStorage.getInstance()){

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
    /**
     * Guarda el resultado del Scoring calculado
     */
    suspend fun guardarScoring(id:String, score: Int): Boolean{
        return try{
           firestore.collection("users").document(id)
               .update("scoring",score)
               .await()
            true
        }catch (e: Exception){
            e.printStackTrace()
            false
        }
    }
    /**
     * Sube un archivo (DNI o Nómina) a Firebase Storage y devuelve la URL de descarga
     */
    suspend fun subirDocumento(uri: Uri,tipoDocumento: String): String? {
        val uid=auth.currentUser?.uid?:return null
        // El nombre será "dni.pdf" o "nomina.pdf"
        val nombreArchivo="$tipoDocumento.pdf"
        // La ruta queda más limpia: documentos/ID_USUARIO/dni.pdf
        val referencia =storage.reference.child("documentos/$uid/$nombreArchivo")

        return try{
            // Intentamos subir el archivo (putFile)
            referencia.putFile(uri).await()
            // Si todo va bien, pedimos la URL pública (downloadUrl)
            referencia.downloadUrl.await().toString()
        }catch (e:Exception){
            e.printStackTrace()
            null
        }
    }

    // Este método es solo para actualizar la base de datos (Firestore)
    suspend fun updateDocumentUrl(id: String, campo: String, url: String): Boolean {
        return try {
            // Accedemos a la colección de usuarios, al documento por su ID y actualizamos un campo
            firestore.collection("users").document(id).update(campo, url).await()
            true
        } catch (e: Exception) {
            e.printStackTrace()
            false
        }
    }
    /**
     * Obtiene todos los usuarios con rol "Inquilino" (Para la vista de la Inmobiliaria)
     */
    suspend fun obtenerInquilinos():List<User>{
        return try{
            firestore.collection("users")
                .whereEqualTo("role","Inquilino")
                .get()
                .await()
                .toObjects(User::class.java)
        }catch (e: Exception){
            emptyList()
        }
    }

    fun cerrarSesion(){
        auth.signOut()
    }


}