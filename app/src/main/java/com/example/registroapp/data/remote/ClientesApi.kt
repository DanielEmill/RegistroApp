package com.example.registroapp.data.remote.dto

import retrofit2.Response
import retrofit2.http.*
interface ClientesApi{
    @GET("/api/Clientes")
    suspend fun getClientes(): List<ClienteDto>

    @GET("/api/Clientes/{id}")
    suspend fun getClientesId(@Path("id") id: Int): ClienteDto

    @POST("/api/Clientes")
    suspend fun postClientes(@Body clienteDto: ClienteDto): Response<ClienteDto>

    @PUT("/api/Clientes/{id}")
    suspend fun putClientes(@Path("id") id: Int, @Body clienteDto: ClienteDto): Response<Unit>

    @DELETE("/api/Clientes/{id}")
    suspend fun deleteClientes(@Path("id") id: Int, @Body clienteDto: ClienteDto): Response<Unit>
}