package com.example.rentchecktfg2026.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.rentchecktfg2026.domain.repositories.UserRepository
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.launch

class ScoringViewModel: ViewModel() {
    private val auth= FirebaseAuth.getInstance()
    private val repository = UserRepository(FirebaseFirestore.getInstance())

    fun guardarResultadoScoring(score:Int){
        val id= auth.currentUser?.uid
        if(id!=null){
                viewModelScope.launch {
                    repository.updateScoring(id,score)
                }
        }
    }
}