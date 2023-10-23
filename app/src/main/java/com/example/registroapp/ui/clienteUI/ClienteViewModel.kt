package com.example.registroapp.ui.clienteUI

import androidx.compose.runtime.State
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
import kotlinx.coroutines.flow.first
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

    private val _uiState = mutableStateOf(ClienteListState())
    val uiState: State<ClienteListState> = _uiState
    private val _uiStateCliente = mutableStateOf(ClienteState())
    val uiStateCliente: State<ClienteState> = _uiStateCliente

    init {
        clienteRepository.getClientes().onEach { result ->
            when (result) {
                is Resource.Loading -> {
                    _uiState.value = ClienteListState(isLoading = true)
                }

                is Resource.Success -> {
                    _uiState.value = ClienteListState(clientes = result.data ?: emptyList())
                }

                is Resource.Error -> {
                    _uiState.value = ClienteListState(error = result.message ?: "Error desconocido")
                }
            }
        }.launchIn(viewModelScope)
    }
    fun putCliente() {
        viewModelScope.launch {
            _uiStateCliente.value = _uiStateCliente.value.copy(isLoading = true)

            val clienteDto = ClienteDto(
                clienteId = clienteId,
                nombres = nombres,
                rnc = rnc,
                direccion = direccion,
                limiteCredito = limiteCredito
            )

            try {
                clienteRepository.putCliente(clienteId, clienteDto)
                limpiar()
                _uiStateCliente.value = _uiStateCliente.value.copy(cliente = clienteDto)
            } catch (e: Exception) {
                _uiStateCliente.value = _uiStateCliente.value.copy(error = e.message ?: "Error desconocido")
            } finally {
                _uiStateCliente.value = _uiStateCliente.value.copy(isLoading = false)
            }
        }
    }

    fun postCliente() {
        viewModelScope.launch {
            if (isValid()) {
                println("Guardando cliente...")

                _uiStateCliente.value = _uiStateCliente.value.copy(isLoading = true)

                val clienteDto = ClienteDto(
                    nombres = nombres,
                    rnc = rnc,
                    direccion = direccion,
                    limiteCredito = limiteCredito
                )

                try {
                    clienteRepository.postCliente(clienteDto)
                    limpiar()
                    _uiStateCliente.value = _uiStateCliente.value.copy(cliente = clienteDto)
                    println("Cliente guardado!")
                } catch (e: Exception) {
                    _uiStateCliente.value = _uiStateCliente.value.copy(error = e.message ?: "Error desconocido")
                } finally {
                    _uiStateCliente.value = _uiStateCliente.value.copy(isLoading = false)
                }

            } else {
                println("Datos de cliente no son válidos.")
            }
        }
    }

    fun deleteCliente(clienteId: Int) {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true)

            try {
                clienteRepository.deleteClientes(clienteId)

                val clientesActualizados = clienteRepository.getClientes().first()
                _uiState.value = _uiState.value.copy(clientes = clientesActualizados.data ?: emptyList(), isLoading = false)
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(error = e.message ?: "Error desconocido", isLoading = false)
            }
        }
    }



    fun limpiar() {
        nombres = ""
        rnc = ""
        direccion = ""
        limiteCredito = 0
    }
}