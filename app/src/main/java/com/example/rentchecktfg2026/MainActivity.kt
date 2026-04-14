package com.example.rentchecktfg2026

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.rentchecktfg2026.ui.theme.RentCheckTFG2026Theme
import com.example.tfg.presentation.CandidatosAct
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