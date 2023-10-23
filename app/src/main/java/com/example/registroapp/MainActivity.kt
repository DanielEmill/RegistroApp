package com.example.registroapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.registroapp.ui.clienteUI.ClienteConsultascreen
import com.example.registroapp.ui.clienteUI.clienteScreen
import com.example.registroapp.ui.theme.RegistroAppTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            RegistroAppTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val navController = rememberNavController()
                    NavHost(navController, startDestination = "RegisterClients") {
                        composable("RegisterClients") {
                            clienteScreen(navController)
                        }
                        composable("consultaClientes") {
                            ClienteConsultascreen(navController)
                        }
                    }
                }
            }
        }
    }
}

