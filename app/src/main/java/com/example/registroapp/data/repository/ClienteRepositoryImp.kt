package com.example.registroapp.data.repository

import com.example.registroapp.data.remote.dto.ClienteDto
import com.example.registroapp.data.remote.dto.ClientesApi
import com.example.registroapp.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class ClienteRepositoryImp @Inject constructor(
    private val api: ClientesApi
): ClienteRepository {
    override fun getClientes(): Flow<Resource<List<ClienteDto>>> = flow {
        try {
            emit(Resource.Loading()) //indicar que estamos cargando

            val ticket = api.getClientes() //descarga los cliente de internet, se supone quedemora algo

            emit(Resource.Success(ticket)) //indicar que se cargo correctamente
        } catch (e: HttpException) {
            //error general HTTP
            emit(Resource.Error(e.message ?: "Error HTTP GENERAL"))
        } catch (e: IOException) {
            //debe verificar tu conexion a internet
            emit(Resource.Error(e.message ?: "verificar tu conexion a internet"))
        }
    }

    override  fun getClienteId(id: Int): Flow<Resource<ClienteDto>> = flow {
        try {
            emit(Resource.Loading()) //indicar que estamos cargando

            val tickets =
                api.getClientesId(id) //descagar la lista de cliente por el id

            emit(Resource.Success(tickets)) //indicar que se cargo correctamente
        } catch (e: HttpException) {
            //error general HTTP
            emit(Resource.Error(e.message ?: "Error HTTP GENERAL"))
        } catch (e: IOException) {
            //debe verificar tu conexion a internet
            emit(Resource.Error(e.message ?: "verificar tu conexion a internet"))
        }
    }

    override suspend fun putCliente(id: Int, clienteDto: ClienteDto) {
        api.putClientes(id, clienteDto)
    }
    override suspend fun deleteClientes(id: Int, clienteDto: ClienteDto){
        api.deleteClientes(id, clienteDto)
    }
    override suspend fun postCliente(clienteDto: ClienteDto) {
        api.postClientes(clienteDto)
    }


}