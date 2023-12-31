package com.example.registroapp.ui.clienteUI

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.registroapp.data.remote.dto.ClienteDto
import com.example.registroapp.util.Resource

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ClienteConsultascreen(
    navController: NavHostController, viewModel: ClienteApiViewModel =
        hiltViewModel()
) {
    val uiState by viewModel.uiState

    Scaffold(topBar = {
        TopAppBar(
            title = { Text(text = "Consulta de clientes") }, modifier =
            Modifier.fillMaxWidth()
        )
    }, content = {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            when {
                uiState.isLoading -> {
                    CircularProgressIndicator()
                }
                uiState.error.isNotEmpty() -> {
                    Text(text = "Error: ${uiState.error}")
                }
                uiState.clientes.isEmpty() -> {
                    Text(text = "No hay clientes en la lista.")
                }
                else -> {
                    val clientes = uiState.clientes
                    Text(
                        text = "Lista (${clientes.size} registros):",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(bottom = 16.dp)
                    )
                    ClientesList(clientes, viewModel)
                }
            }
        }
    })
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ClientesList(clientes: List<ClienteDto>, viewModel: ClienteApiViewModel) {
    LazyColumn(modifier = Modifier.fillMaxWidth()) {
        items(clientes) { cliente ->
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp)
            ) {
                ClienteCard(cliente, viewModel)
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ClienteCard(cliente: ClienteDto, viewModel: ClienteApiViewModel) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Text(
            text = "ID: ${cliente.clienteId}",
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 8.dp)
        )
        Text(
            text = "Nombre: ${cliente.nombres}", modifier =
            Modifier.padding(bottom = 4.dp)
        )
        Text(
            text = "RNC: ${cliente.rnc}", modifier =
            Modifier.padding(bottom = 4.dp)
        )
        Text(
            text = "Dirección: ${cliente.direccion}", modifier =
            Modifier.padding(bottom = 4.dp)
        )
        Text(
            text = "Límite de Crédito: ${cliente.limiteCredito}",
            modifier = Modifier.padding(bottom = 4.dp)
        )
        OutlinedButton(
            onClick = {
                cliente.clienteId?.let { viewModel.deleteCliente(it) }
            }, modifier = Modifier.align(Alignment.End)
        ) {
            Text(text = "Eliminar")
        }

    }
}