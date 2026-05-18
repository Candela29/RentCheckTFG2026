package com.example.rentchecktfg2026.presentation.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PrivacyPolicySheet(
    onDismiss: () -> Unit,
    onAccept: () -> Unit,
    sheetState: SheetState
) {
    val azul= Color(0xFF2D63ED)
    ModalBottomSheet(
        onDismissRequest = onDismiss,
        sheetState = sheetState,
        containerColor = MaterialTheme.colorScheme.surface
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp)
                .padding(bottom = 48.dp)
        ) {
            Text(
                text = "Tratamiento de Datos Sensibles",
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold,
                color = azul
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "En RentCheck, la seguridad de tus datos es prioritaria. Al registrarte, aceptas que:\n\n" +
                        "1. Las nóminas y documentos bancarios se utilizan exclusivamente para el cálculo de scoring.\n" +
                        "2. Estos archivos se eliminarán automáticamente cada 90 días si no hay una solicitud activa.\n" +
                        "3. Los datos solo serán visibles para la inmobiliaria si tú autorizas el envío de la solicitud.",
                style = MaterialTheme.typography.bodyLarge,
                lineHeight = 24.sp
            )
            Spacer(modifier = Modifier.height(24.dp))
            Button(

                onClick = {
                    onAccept()
                    onDismiss()
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = azul,
                    contentColor = Color.White
                ),
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("He leído y acepto")
            }
        }
    }
}