package br.ufms.gitpay.ui

import SmartTextField
import SmartTextFieldType
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Savings
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import br.ufms.gitpay.Banco
import br.ufms.gitpay.ContaBancaria
import br.ufms.gitpay.ui.shared.FormCard

enum class LoginState { Login, CreateAccountStep1, CreateAccountStep2 }

class LoginViewModel {

    var loginState by mutableStateOf(LoginState.Login)
        private set

    fun createAccountStep1() {
        loginState = LoginState.CreateAccountStep1
    }

    fun createAccountStep2() {
        loginState = LoginState.CreateAccountStep2
    }

    fun cancelAccount() {
        loginState = LoginState.Login
    }

    fun createAccount() {
        loginState = LoginState.Login
    }

    fun login(
        cpfCnpj: String,
        senha: String,
        msgErro: MutableState<String>,
        onLoginSuccess: (ContaBancaria) -> Unit,
    ) {
        val conta = Banco.GitPay.getConta(cpfCnpj)
        if (conta == null) {
            msgErro.value = "Conta não encontrada"
        } else if (conta.usuario.senha != senha) {
            msgErro.value = "Senha inválida"
        } else {
            onLoginSuccess(conta)
        }
    }
}

@Composable
private fun LogoPane() {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterVertically)
    ) {
        Icon(
            imageVector = Icons.Default.Savings,
            contentDescription = "GitPay",
            modifier = Modifier.size(96.dp),
            tint = MaterialTheme.colorScheme.primary
        )
        Text(text = "GitPay", fontSize = 32.sp, fontWeight = FontWeight.Bold)
        Text(text = "Seu parceiro financeiro para toda vida", fontSize = 16.sp, textAlign = TextAlign.Center)
    }
}

@Composable
private fun FormLogin(
    viewModel: LoginViewModel,
    onLoginSuccess: (ContaBancaria) -> Unit,
) {
    val cpfCnpj = remember { mutableStateOf("") }
    val senha = remember { mutableStateOf("") }
    val msgErro = remember { mutableStateOf("") }

    FormCard(
        form = {
            SmartTextField(SmartTextFieldType.CpfOrCnpj, cpfCnpj)
            SmartTextField(SmartTextFieldType.Password, senha)
        },
        primaryButton = "Entrar" to { viewModel.login(cpfCnpj.value, senha.value, msgErro, onLoginSuccess) },
        secondaryButton = "Criar uma conta" to { viewModel.createAccountStep1() },
        errorMsg = msgErro.value
    )
}

@Composable
fun LoginScreen(
    onLoginSuccess: (ContaBancaria) -> Unit,
) {
    Row(
        modifier = Modifier.fillMaxSize().padding(16.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp, Alignment.CenterHorizontally),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(modifier = Modifier.weight(.5f)) {
            LogoPane()
        }
        Box(modifier = Modifier.weight(.5f)) {
            val viewModel = remember { LoginViewModel() }
            when (viewModel.loginState) {
                LoginState.Login -> FormLogin(viewModel, onLoginSuccess)
//                LoginState.CreateAccountStep1 -> FormCriarConta(viewModel)
//                LoginState.CreateAccountStep2 -> FormCriarSenha(viewModel)
                else -> {}
            }
        }
    }
}