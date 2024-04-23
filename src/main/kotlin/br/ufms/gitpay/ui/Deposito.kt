package br.ufms.gitpay.ui

import SmartTextField
import SmartTextFieldType
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.twotone.CurrencyExchange
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import br.ufms.gitpay.ContaBancaria
import br.ufms.gitpay.ui.shared.FormCard

@Composable
fun DepositoCard(conta: ContaBancaria) {

    val valor = remember { mutableStateOf("") }
    val msgErro = remember { mutableStateOf("") }

    FormCard(
        icon = Icons.TwoTone.CurrencyExchange,
        title = "Depósito",
        subtitle = "Depositar na sua conta GitPay",
        form = {
            SmartTextField(SmartTextFieldType.Currency, valor)
        },
        primaryButton = "Depositar" to { conta.depositar(valor.value.toDouble()) },
        errorMsg = msgErro.value,
    )
}