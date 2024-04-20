import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.input.*
import br.ufms.gitpay.ui.shared.CurrencyVisualTransformation
import br.ufms.gitpay.ui.shared.SmartVisualTransformation
import br.ufms.gitpay.util.Validador
import java.text.DecimalFormatSymbols

enum class SmartTextFieldType(
    val label: String? = null,
    val icon: ImageVector? = null,
) {
    Text(label = null, icon = null),
    Number(label = null, icon = null),
    NumberValue(label = null, icon = null),
    Name(label = "Nome", icon = Icons.Default.Person),
    Username(label = "Usuário", icon = Icons.Default.AccountCircle),
    Phone(label = "Telefone", icon = Icons.Default.Phone),
    Email(label = "Email", icon = Icons.Default.Email),
    Password(label = "Senha", icon = Icons.Default.Lock),
    NumberPassword(label = "Senha", icon = Icons.Default.Lock),
    Currency(label = "Valor", icon = Icons.Default.AttachMoney),
    Cpf(label = "CPF", icon = Icons.Default.AccountBox),
    Cnpj(label = "CNPJ", icon = Icons.Default.Business),
    CpfOrCnpj(label = "CPF ou CNPJ", icon = Icons.Default.AccountBox)
}

fun isMobile(number: String): Boolean {
    return number.length > 2 && number[2] == '9'
}

private fun acceptText(type: SmartTextFieldType, text: String): Boolean {
    return when (type) {
        SmartTextFieldType.NumberValue -> {
            val value = if (text.startsWith("-")) text.substring(1) else text
            if (value.any { !it.isDigit() && it != DecimalFormatSymbols.getInstance().decimalSeparator }) return false
            value.isEmpty() || text.replace(",", ".").toDoubleOrNull() != null
        }

        SmartTextFieldType.Currency -> {
            if (text.any { !it.isDigit() && it != DecimalFormatSymbols.getInstance().decimalSeparator }) return false
            text.isEmpty() || text.replace(",", ".").toDoubleOrNull() != null
        }

        else -> {
            val pattern = when (type) {
                SmartTextFieldType.Name -> "^[a-zA-ZÀ-ÖØ-öø-ÿ ]{0,50}\$"
                SmartTextFieldType.Number -> "^[0-9()+\\-*/ˆ^%!., ]*{0,50}\$"
                SmartTextFieldType.Username -> "^(?!\\\\.|.*\\\\.\$)[a-zA-Z0-9_.]{0,30}\$"
                SmartTextFieldType.Phone -> if (isMobile(text)) "^\\d{0,11}\$" else "^\\d{0,10}\$"
                SmartTextFieldType.Email -> "^(?!.*@.*@)[a-zA-Z@\\._]{0,50}\$"
                SmartTextFieldType.Password -> "^[^\\s\\p{Z}\\p{C}]{0,30}\$"
                SmartTextFieldType.NumberPassword -> "^\\d{0,30}\$"
                SmartTextFieldType.Cpf -> "^\\d{0,11}\$"
                SmartTextFieldType.Cnpj -> "^\\d{0,14}\$"
                SmartTextFieldType.CpfOrCnpj -> "^\\d{0,14}\$"
                else -> "^.{0,50}\$"
            }
            Regex(pattern).matches(text)
        }
    }
}

private fun validateField(type: SmartTextFieldType, text: String): Boolean {
    return when (type) {
        SmartTextFieldType.Name -> Validador.validarNome(text)
        SmartTextFieldType.Username -> Validador.validarUsuario(text)
        SmartTextFieldType.Phone -> Validador.validarTelefone(text)
        SmartTextFieldType.Email -> Validador.validarEmail(text)
        SmartTextFieldType.Password -> Validador.validarSenha(text)
        SmartTextFieldType.NumberPassword -> Validador.validarSenhaNumerica(text)
        SmartTextFieldType.Cpf -> Validador.validarCPF(text)
        SmartTextFieldType.Cnpj -> Validador.validarCNPJ(text)
        SmartTextFieldType.CpfOrCnpj -> {
            if (text.length == 11) Validador.validarCPF(text) else Validador.validarCPF(text)
        }

        else -> true
    }
}

private fun visualTransformation(type: SmartTextFieldType): VisualTransformation {
    return when (type) {
        SmartTextFieldType.Password, SmartTextFieldType.NumberPassword -> PasswordVisualTransformation()
        SmartTextFieldType.Phone -> SmartVisualTransformation.Phone
//        SmartTextFieldType.Currency -> CurrencyVisualTransformation("BRL")
        SmartTextFieldType.Currency -> CurrencyVisualTransformation()
        SmartTextFieldType.Cpf -> SmartVisualTransformation.Cpf
        SmartTextFieldType.Cnpj -> SmartVisualTransformation.Cnpj
        SmartTextFieldType.CpfOrCnpj -> SmartVisualTransformation.CpfCnpj
        else -> VisualTransformation.None
    }
}

@Composable
fun SmartTextField(
    type: SmartTextFieldType,
    attr: MutableState<String>,
    label: String? = type.label,
    icon: ImageVector? = type.icon,
) {
    var errorMsg: String by remember { mutableStateOf("") }

    OutlinedTextField(
        modifier = Modifier.onFocusChanged {
            if (attr.value.isNotBlank() && !it.isFocused) {
                errorMsg = if (validateField(type, attr.value)) "" else "Valor inválido"
            }
        },
        label = label?.let { { Text(label) } },
        leadingIcon = icon?.let {
            {
                Icon(
                    imageVector = if (type == SmartTextFieldType.CpfOrCnpj) {
                        if (attr.value.length <= 11) {
                            Icons.Default.PersonOutline
                        } else {
                            Icons.Default.Business
                        }
                    } else {
                        icon
                    },
                    contentDescription = label
                )
            }
        },
        prefix = (type == SmartTextFieldType.Currency).takeIf { it }?.let { { Text("R$") } },
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text, imeAction = ImeAction.Next),
        singleLine = true,
        value = attr.value,
        onValueChange = {
            if (acceptText(type, it)) {
                attr.value = it
            }
        },
        isError = errorMsg.isNotBlank(),
        supportingText = errorMsg.takeIf { it.isNotBlank() }?.let { { Text(errorMsg) } },
        visualTransformation = visualTransformation(type)
    )
}