package br.com.usinasantafe.pcp.presenter

import androidx.compose.runtime.Composable
import br.com.usinasantafe.pcp.ui.theme.PCPTheme

@Composable
fun InititalTestScreen(
    onNavSplash: () -> Unit,
) {
    PCPTheme {
        onNavSplash()
    }
}
