package com.example.registroapp.ui.clienteUI
import android.annotation.SuppressLint
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.registroapp.util.Buttonbar
import com.example.registroapp.util.CustomNumericalOutlinedTextField
import com.example.registroapp.util.CustomOutlinedTextField
import com.example.registroapp.util.MyBar
import com.example.registroapp.util.SaveButton

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalComposeUiApi::class, ExperimentalMaterial3Api::class)
@Composable
fun clienteScreen(navController: NavHostController, viewModel:
ClienteApiViewModel = hiltViewModel()) {
    val snackbarHostState = remember { SnackbarHostState() }
    //

    // Pantalla con Scaffold
    Scaffold(snackbarHost = { SnackbarHost(snackbarHostState) },
        modifier = Modifier.fillMaxSize(),
        topBar = { MyBar() },
        bottomBar = { Buttonbar(navController) }) {
        ContentColumn(viewModel)
    }
}
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ContentColumn(viewModel: ClienteApiViewModel) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        // Detalles
        ClienteDetails(viewModel)
        // Botón de guardar
        SaveButton(viewModel)
    }
}
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ClienteDetails(viewModel: ClienteApiViewModel) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        Text(
            text = "Cliente Detalle:",
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(8.dp))
        // Campos de entrada
        CustomOutlinedTextField(
            label = "Nombre",
            value = viewModel.nombres,
            modifier = Modifier.padding(vertical = 8.dp),
            isValid = viewModel.isValidNombre,
            onValueChange = { viewModel.nombres = it },
            imeAction = ImeAction.Next
        )
        CustomOutlinedTextField(
            label = "Rnc",
            value = viewModel.rnc,
            modifier = Modifier.padding(vertical = 8.dp),
            isValid = viewModel.isValidRnc,
            onValueChange = { viewModel.rnc = it },
            imeAction = ImeAction.Next
        )
        CustomOutlinedTextField(
            label = "Dirección",
            value = viewModel.direccion,
            modifier = Modifier.padding(vertical = 8.dp),
            isValid = viewModel.isValidDireccion,
            onValueChange = { viewModel.direccion = it },
            imeAction = ImeAction.Next
        )
        CustomNumericalOutlinedTextField(
            label = "LimiteCredito",
            value = viewModel.limiteCredito,
            modifier = Modifier.padding(vertical = 8.dp),
            isValid = viewModel.isValidLimiteCredito,
            onValueChange = { viewModel.limiteCredito = it },
            imeAction = ImeAction.Next
        )
    }
}
