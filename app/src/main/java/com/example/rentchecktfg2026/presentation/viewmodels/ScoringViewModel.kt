package com.example.rentchecktfg2026.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.rentchecktfg2026.data.repositories.UserRepositoryImpl
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.launch

class ScoringViewModel: ViewModel() {
    private val auth= FirebaseAuth.getInstance()
    private val repository = UserRepositoryImpl(FirebaseFirestore.getInstance())

    fun guardarResultadoScoring(score:Int){
        val id= auth.currentUser?.uid
        if(id!=null){
                viewModelScope.launch {
                    repository.updateScoring(id,score)
                }
        }
    }
}