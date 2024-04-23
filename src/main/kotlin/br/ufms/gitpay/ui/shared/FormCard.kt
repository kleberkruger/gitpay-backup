package br.ufms.gitpay.ui.shared

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
private fun Header(
    icon: ImageVector,
    title: String,
    subtitle: String? = null
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(8.dp, Alignment.Top),
        horizontalAlignment = Alignment.Start,
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(16.dp, Alignment.Start),
        ) {
            Icon(imageVector = icon, contentDescription = title, modifier = Modifier.size(32.dp))
            Text(text = title, fontSize = 20.sp, fontWeight = FontWeight.Bold)
        }
        subtitle?.let { subtitle -> Text(subtitle, fontSize = 14.sp) }
    }
}

@Composable
private fun ActionButtons(
    primaryButton: Pair<String, () -> Unit>,
    secondaryButton: Pair<String, () -> Unit>? = null,
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(8.dp, Alignment.End),
        verticalAlignment = Alignment.CenterVertically
    ) {
        secondaryButton?.let { TextButton(onClick = secondaryButton.second) { Text(secondaryButton.first) } }
        Button(onClick = primaryButton.second) { Text(primaryButton.first) }
    }
}

@Composable
fun FormCard(
    icon: ImageVector? = null,
    title: String? = null,
    subtitle: String? = null,
    form: @Composable () -> Unit,
    primaryButton: Pair<String, () -> Unit>,
    secondaryButton: Pair<String, () -> Unit>? = null,
    errorMsg: String? = null
) {
    Card {
        Column(
            modifier = Modifier.fillMaxSize().padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp, Alignment.CenterVertically)
        ) {
            title?.let {
                Header(icon = icon!!, title = title, subtitle = subtitle)
            }
            Spacer(Modifier.weight(1f))
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterVertically)
            ) {
                form()
            }
            Spacer(Modifier.weight(1f))
            errorMsg?.let { Text(errorMsg, color = MaterialTheme.colorScheme.error) }
            ActionButtons(primaryButton, secondaryButton)
        }
    }
}