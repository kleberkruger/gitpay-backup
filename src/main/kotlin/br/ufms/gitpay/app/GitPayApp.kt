package br.ufms.gitpay.app

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import androidx.compose.ui.window.rememberWindowState
import br.ufms.gitpay.Banco
import br.ufms.gitpay.BancoCentral
import br.ufms.gitpay.ContaBancaria
import br.ufms.gitpay.ui.ConfigScreen
import br.ufms.gitpay.ui.HomeScreen
import br.ufms.gitpay.ui.LoginScreen
import br.ufms.gitpay.ui.theme.DarkColors
import br.ufms.gitpay.ui.theme.LightColors
import br.ufms.gitpay.util.gerarClientes
import java.awt.Dimension

/**
 * Função que gera dados aleatórios para o sistema.
 */
private fun gerarDadosAleatorios() {
    gerarDadosBanco(Banco(1, "Banco do Brasil"), 4, 2)
    gerarDadosBanco(Banco(104, "Caixa Econômica Federal"), 5, 2)
    gerarDadosBanco(Banco(260, "Nubank"), 3, 1)
    gerarDadosBanco(Banco.GitPay, 12, 4)
}

/**
 * Função que registra um banco e o inicia com algumas contas aleatórias
 *
 * @param banco
 * @param qtdPessoaFisica
 * @param qtdPessoaJuridica
 */
private fun gerarDadosBanco(banco: Banco, qtdPessoaFisica: Int, qtdPessoaJuridica: Int) {
    println("\n--- Gerando clientes para ${banco.nome.uppercase()} ---\n")
    BancoCentral.INSTANCE.registrarBanco(banco)
    gerarClientes(banco.codigo, qtdPessoaFisica, qtdPessoaJuridica).map {
        println(it)
    }
}

enum class ScreenState { Login, Home, Config }

class GitPayAppViewModel {

    var screenState by mutableStateOf(ScreenState.Login)
        private set

    var currentAccount by mutableStateOf<ContaBancaria?>(null)
        private set

    fun login(account: ContaBancaria) {
        currentAccount = account
        screenState = ScreenState.Home
    }

    fun logout() {
        currentAccount = null
        screenState = ScreenState.Login
    }

    fun navigateToSettings() {
        screenState = ScreenState.Config
    }

    fun navigateBack() {
        screenState = if (currentAccount == null) ScreenState.Login else ScreenState.Home
    }
}

@Preview
@Composable
fun GitPayApp(
    useDarkTheme: Boolean = isSystemInDarkTheme()
) {
    val colors = if (!useDarkTheme) LightColors else DarkColors

    MaterialTheme(colorScheme = colors) {
        Surface(modifier = Modifier.fillMaxSize()) {

            val viewModel = remember { GitPayAppViewModel() }

            when (viewModel.screenState) {
                ScreenState.Login -> LoginScreen(
                    onLoginSuccess = { conta -> viewModel.login(conta) },
                )

                ScreenState.Home -> HomeScreen(
                    conta = viewModel.currentAccount!!,
                    onLogoutClick = viewModel::logout
                )

                ScreenState.Config -> ConfigScreen(
                    conta = viewModel.currentAccount,
                    onBackClick = viewModel::navigateBack
                )
            }
        }
    }
}

fun main() = application {
    Window(
        title = "GitPay",
        state = rememberWindowState(width = 900.dp, height = 700.dp),
        onCloseRequest = ::exitApplication
    ) {
        window.minimumSize = Dimension(600, 500)
        gerarDadosAleatorios()
        GitPayApp()
    }
}