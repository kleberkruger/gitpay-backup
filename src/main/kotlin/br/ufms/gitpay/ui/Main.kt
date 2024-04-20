package br.ufms.gitpay.ui

import SmartTextField
import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import java.util.Locale

@Composable
@Preview
fun App() {
    val text = remember { mutableStateOf("") }
    val number = remember { mutableStateOf("") }
    val numberValue = remember { mutableStateOf("") }
    val name = remember { mutableStateOf("") }
    val username = remember { mutableStateOf("") }
    val phone = remember { mutableStateOf("") }
    val email = remember { mutableStateOf("") }
    val password = remember { mutableStateOf("") }
    val numberPassword = remember { mutableStateOf("") }
    val currency = remember { mutableStateOf("") }
    val cpf = remember { mutableStateOf("") }
    val cnpj = remember { mutableStateOf("") }
    val cpfCnpj = remember { mutableStateOf("") }

    MaterialTheme {
        Box(Modifier.fillMaxSize()) {
            Row(
                modifier = Modifier.fillMaxWidth().padding(32.dp),
                horizontalArrangement = Arrangement.spacedBy(32.dp, Alignment.CenterHorizontally),
                verticalAlignment = Alignment.Top
            ) {
                Column(
                    modifier = Modifier.weight(.5f),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterVertically)
                ) {
                    SmartTextField(type = SmartTextFieldType.Text, attr = text, label = "Text")
                    SmartTextField(type = SmartTextFieldType.Number, attr = number, label = "Number")
                    SmartTextField(type = SmartTextFieldType.NumberValue, attr = numberValue, label = "NumberValue")
                    SmartTextField(type = SmartTextFieldType.Name, attr = name)
                    SmartTextField(type = SmartTextFieldType.Username, attr = username)
                    SmartTextField(type = SmartTextFieldType.Phone, attr = phone)
                    SmartTextField(type = SmartTextFieldType.Email, attr = email)
                }
                Column(
                    modifier = Modifier.weight(.5f),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterVertically)
                ) {
                    SmartTextField(type = SmartTextFieldType.Password, attr = password)
                    SmartTextField(type = SmartTextFieldType.NumberPassword, attr = numberPassword)
                    SmartTextField(type = SmartTextFieldType.Currency, attr = currency)
                    SmartTextField(type = SmartTextFieldType.Cpf, attr = cpf)
                    SmartTextField(type = SmartTextFieldType.Cnpj, attr = cnpj)
                    SmartTextField(type = SmartTextFieldType.CpfOrCnpj, attr = cpfCnpj)
                }
            }
        }
    }
}

fun main() = application {
    Window(onCloseRequest = ::exitApplication) {
        Locale.setDefault(Locale.of("pt", "BR"))
        App()
    }
}
