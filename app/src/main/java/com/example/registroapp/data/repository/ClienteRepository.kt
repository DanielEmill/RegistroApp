package com.example.registroapp.data.repository

import com.example.registroapp.data.remote.dto.ClienteDto
import com.example.registroapp.util.Resource
import kotlinx.coroutines.flow.Flow

interface ClienteRepository {

    fun getClientes(): Flow<Resource<List<ClienteDto>>>

    fun getClienteId(id: Int): Flow<Resource<ClienteDto>>

    suspend fun putCliente(id: Int, clienteDto: ClienteDto)

    suspend fun deleteClientes(id: Int, clienteDto: ClienteDto)

    suspend fun postCliente(clienteDto: ClienteDto)
}