package com.example.rentchecktfg2026

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import com.example.rentchecktfg2026.ui.theme.RentCheckTFG2026Theme
import com.example.rentchecktfg2026.presentation.ui.screens.CandidatosAct
import com.example.rentchecktfg2026.presentation.viewmodels.CandidatosViewModel
import kotlin.getValue

class MainActivity : ComponentActivity() {

    private val viewModel: CandidatosViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            RentCheckTFG2026Theme {
                CandidatosAct(vm = viewModel)
            }
        }
    }
}