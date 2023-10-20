package com.example.registroapp.util

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import com.example.registroapp.ui.clienteUI.ClienteApiViewModel

@OptIn(ExperimentalMaterial3Api::class, ExperimentalComposeUiApi::class)
@Composable
fun SaveButton(viewModel: ClienteApiViewModel) {
    val keyboardController = LocalSoftwareKeyboardController.current
    OutlinedButton(
        onClick = {
            viewModel.postCliente()
            keyboardController?.hide()
        },
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.Default.AddCircle,
                contentDescription = "Guardar"
            )
            Text(text = "Guardar")
        }
    }
}
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomOutlinedTextField(
    label: String,
    value: String,
    modifier: Modifier = Modifier,
    isValid: Boolean,
    onValueChange: (String) -> Unit,
    imeAction: ImeAction = ImeAction.Done
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        modifier = modifier.fillMaxWidth(),
        label = { Text(text = label) },
        singleLine = true,
        textStyle = LocalTextStyle.current.copy(
            color = if (isValid)
                Color.Black else Color.Red
        ),
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = if (isValid) Color.Gray else Color.Red,
            unfocusedBorderColor = if (isValid) Color.Gray else Color.Red,
        ),
        keyboardOptions = KeyboardOptions.Default.copy(
            imeAction =
            imeAction
        )
    )
}
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomNumericalOutlinedTextField(
    label: String,
    value: Int,
    modifier: Modifier = Modifier,
    isValid: Boolean,
    onValueChange: (Int) -> Unit,
    imeAction: ImeAction = ImeAction.Done
) {
    OutlinedTextField(
        value = value.toString(),
        onValueChange = { newValue ->
            val numericValue = newValue.filter { it.isDigit() }
            onValueChange(numericValue.toIntOrNull() ?: 0)
        },
        modifier = modifier.fillMaxWidth(),
        label = { Text(text = label) },
        singleLine = true,
        textStyle = LocalTextStyle.current.copy(color = if (isValid)
            Color.Black else Color.Red),
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = if (isValid) Color.Gray else Color.Red,
            unfocusedBorderColor = if (isValid) Color.Gray else Color.Red,
        ),
        keyboardOptions = KeyboardOptions.Default.copy(
            keyboardType = KeyboardType.Number,
            imeAction = imeAction
        )
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyBar() {
    TopAppBar(title = { Text(text = "Registro Cliente") })
}