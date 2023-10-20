package com.example.registroapp.ui.clienteUI

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.registroapp.data.remote.dto.ClienteDto
import com.example.registroapp.data.repository.ClienteRepositoryImp
import com.example.registroapp.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

data class ClienteListState(
    val isLoading: Boolean = false,
    val clientes: List<ClienteDto> = emptyList(),
    val error: String = ""
)

data class ClienteState(
    val isLoading: Boolean = false,
    val cliente: ClienteDto? = null,
    val error: String = ""
)

@HiltViewModel
class ClienteApiViewModel @Inject constructor(
    private val clienteRepository: ClienteRepositoryImp
) : ViewModel() {
    var clienteId by mutableStateOf(1)
    var nombres by mutableStateOf("")
    var rnc by mutableStateOf("")
    var direccion by mutableStateOf("")
    var limiteCredito by mutableStateOf(1)

    var isValidNombre by mutableStateOf(true)
    var isValidRnc by mutableStateOf(true)
    var isValidDireccion by mutableStateOf(true)
    var isValidLimiteCredito by mutableStateOf(true)
    private fun isValid(): Boolean {
        isValidNombre = nombres.isNotBlank()
        isValidRnc = rnc.isNotBlank()
        isValidDireccion = direccion.isNotBlank()
        isValidLimiteCredito = limiteCredito > 0
        return isValidNombre && isValidRnc && isValidDireccion && isValidLimiteCredito
    }

    var uiState = MutableStateFlow(ClienteListState())
        private set
    var uiStateCliente = MutableStateFlow(ClienteState())
        private set

    val clientes: StateFlow<Resource<List<ClienteDto>>> = clienteRepository.getClientes().stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = Resource.Loading()
    )
    //
    init {
        clienteRepository.getClientes().onEach { result ->
            when (result) {
                is Resource.Loading -> {
                    uiState.update { it.copy(isLoading = true) }
                }

                is Resource.Success -> {
                    uiState.update {
                        it.copy(clientes = result.data ?: emptyList())
                    }
                }

                is Resource.Error -> {
                    uiState.update { it.copy(error = result.message ?: "Error desconocido") }
                }
            }
        }.launchIn(viewModelScope)
    }

    fun putCliente() {
        viewModelScope.launch {
            val clienteDto = ClienteDto(
                clienteId = clienteId,
                nombres = nombres,
                rnc = rnc,
                direccion = direccion,
                limiteCredito = limiteCredito
            )
            clienteRepository.putCliente(clienteId, clienteDto)
            limpiar()
        }
    }

    fun postCliente() {
        viewModelScope.launch {
            if (isValid()) {
                println("Guardando cliente...")
                val clienteDto = ClienteDto(
                    nombres = nombres,
                    rnc = rnc,
                    direccion = direccion,
                    limiteCredito = limiteCredito
                )
                clienteRepository.postCliente(clienteDto)
                limpiar()
                println("Cliente guardado!")
            } else {
                println("Datos de cliente no son válidos.")
            }
        }
    }

    fun deleteCliente(clienteId: Int, clienteDto: ClienteDto) {
        viewModelScope.launch {
            clienteRepository.deleteClientes(clienteId, clienteDto)
        }
    }

    fun limpiar() {
        nombres = ""
        rnc = ""
        direccion = ""
        limiteCredito = 0
    }
}
