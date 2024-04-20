package br.ufms.gitpay.ui.shared

import androidx.compose.foundation.layout.width
import androidx.compose.material3.OutlinedTextField
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.input.TransformedText
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import isMobile

sealed class SmartVisualTransformation {

    object Phone : VisualTransformation {

        private val phone10 = BasicVisualTransformation("(##) ####-####")
        private val phone11 = BasicVisualTransformation("(##) #####-####")

        override fun filter(text: AnnotatedString): TransformedText {
            return if (isMobile(text.text)) phone11.filter(text) else phone10.filter(text)
        }
    }

    object Cpf : BasicVisualTransformation("###.###.###-##")
    object Cnpj : BasicVisualTransformation("##.###.###/####-##")

    object CpfCnpj : VisualTransformation {

        override fun filter(text: AnnotatedString): TransformedText {
            return if (text.length > 11) Cnpj.filter(text) else Cpf.filter(text)
        }
    }
}
