package com.example.registroapp.data.remote.dto
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Clientes")
data class ClienteDto (
    @PrimaryKey
    var clienteId : Int?=null,
    var nombres: String = "",
    var rnc: String = "",
    var direccion: String? = "",
    var limiteCredito: Int = 0
)