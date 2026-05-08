package com.example.rentchecktfg2026.data.repositories

import android.net.Uri
import android.util.Log
import com.example.rentchecktfg2026.domain.model.Property
import com.example.rentchecktfg2026.domain.model.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import kotlinx.coroutines.tasks.await

class UserRepositoryImpl( private val firestore: FirebaseFirestore = FirebaseFirestore.getInstance()) {

    private val auth: FirebaseAuth = FirebaseAuth.getInstance()
    private val storage: FirebaseStorage = FirebaseStorage.getInstance()
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
    suspend fun subirDocumento(uri: Uri, tipoDocumento: String): String? {
        val uid=auth.currentUser?.uid

        Log.d("STORAGE", "UID: $uid")
        Log.d("STORAGE", "URI: $uri")

        if (uid == null) {
            Log.e("STORAGE", "❌ No hay usuario autenticado")
            return null
        }

        // El nombre será "dni.pdf" o "nomina.pdf"
        val nombreArchivo="$tipoDocumento.pdf"
        // La ruta queda más limpia: documentos/ID_USUARIO/dni.pdf
        val referencia =storage.reference.child("documentos/$uid/$nombreArchivo")
        Log.d("STORAGE", "Ruta Storage: documentos/$uid/$nombreArchivo")


        return try{
            // Intentamos subir el archivo (putFile)
            referencia.putFile(uri).await()
            Log.d("STORAGE", "✅ Archivo subido correctamente")
            // Si todo va bien, pedimos la URL pública (downloadUrl)
            val url =  referencia.downloadUrl.await().toString()
            Log.d("STORAGE", "✅ URL: $url")
            url
        }catch (e:Exception){
            Log.e("STORAGE", "❌ Error: ${e.message}")
            Log.e("STORAGE", "❌ Causa: ${e.cause}")
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

    //
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

    suspend fun saveProperty (property: Property) : Result<Unit>{
        return try{
            firestore.collection("propiedades").add(property).await()
            Result.success(Unit)
        }catch (e: Exception){
            Result.failure(e)
        }
    }

    fun cerrarSesion(){
        auth.signOut()
    }


}