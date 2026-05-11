package com.example.rentchecktfg2026.network

import com.google.firebase.auth.FirebaseAuth
import com.google.android.gms.tasks.Tasks
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object RetrofitClient {

    // 10.0.2.2 es el "localhost" desde el emulador de Android
    const val BASE_URL = "http://10.0.2.2:8080"
        //"http://10.0.2.2:8080/"

    //OkHttpClient es un interceptor que recupera el Token de Firebase en cada petición.
    private val okHttpClient = OkHttpClient.Builder()
        .addInterceptor { chain ->
            val user = FirebaseAuth.getInstance().currentUser
            val task = user?.getIdToken(false)

            // Espera síncrona del token para el interceptor
            val token = try {
                Tasks.await(task!!).token
            } catch (e: Exception) {
                null
            }

            val newRequest = chain.request().newBuilder()
                .addHeader("Authorization", "Bearer $token")
                .build()
            chain.proceed(newRequest)
        }
        .connectTimeout(30, TimeUnit.SECONDS)
        .build()

    val instance: ApiService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiService::class.java)
    }
}