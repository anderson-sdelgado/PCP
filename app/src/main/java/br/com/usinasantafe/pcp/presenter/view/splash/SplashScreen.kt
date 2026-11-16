package br.com.usinasantafe.pcp.presenter.view.splash

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import br.com.usinasantafe.pcp.BuildConfig
import br.com.usinasantafe.pcp.R
import br.com.usinasantafe.pcp.presenter.theme.AlertDialogSimpleDesign
import br.com.usinasantafe.pcp.presenter.theme.MsgErrors
import br.com.usinasantafe.pcp.presenter.theme.PCPTheme
import br.com.usinasantafe.pcp.utils.UiStatusState
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.time.Duration.Companion.milliseconds

@Composable
fun SplashScreen(
    viewModel: SplashViewModel = hiltViewModel(),
    onNavMenuInicial: () -> Unit,
    onNavMenuApont: () -> Unit
) {
    PCPTheme {
        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
            val uiState by viewModel.uiState.collectAsStateWithLifecycle()

            LaunchedEffect(Unit) {
                viewModel.processInitial(BuildConfig.VERSION_NAME)
            }

            SplashContent(
                modifier = Modifier.padding(innerPadding),
                flagMovOpen = uiState.flagMovOpen,
                onCloseDialog = viewModel::onCloseDialog,
                status = uiState.status,
                onNavMenuInicial = onNavMenuInicial,
                onNavMenuApont = onNavMenuApont,
            )
        }
    }
}

@Composable
fun SplashContent(
    modifier: Modifier = Modifier,
    flagMovOpen: Boolean,
    onCloseDialog: () -> Unit,
    status: UiStatusState,
    onNavMenuInicial: () -> Unit,
    onNavMenuApont: () -> Unit,
) {
    var visibility by remember { mutableStateOf(true) }
    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect(Unit) {
        coroutineScope.launch {
            while (true) {
                // Toggle visibility
                visibility = !visibility
                // Delay to control the toggle frequency
                delay(2000.milliseconds) // Adjust delay as needed
            }
        }
    }
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier.fillMaxSize()
    ) {
        AnimatedVisibility(
            visible = visibility,
            enter = fadeIn(animationSpec = tween(durationMillis = 1100)),
            exit = fadeOut(animationSpec = tween(durationMillis = 1100))
        ) {
            Image(
                painter = painterResource(id = R.drawable.logo),
                contentDescription = stringResource(id = R.string.app_name),
                contentScale = ContentScale.Fit,
                modifier = modifier.size(250.dp)
            )
        }
    }

    if(status.flagDialog) {
        MsgErrors(status.errors, onCloseDialog, status.failure)
    }

    LaunchedEffect(status.flagAccess) {
        if (status.flagAccess) {
            if (flagMovOpen) onNavMenuApont() else onNavMenuInicial()
        }
    }

}

@Preview(showBackground = true)
@Composable
fun SplashPagePreview() {
    PCPTheme {
        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
            SplashContent(
                modifier = Modifier.padding(innerPadding),
                flagMovOpen = false,
                onCloseDialog = {},
                status = UiStatusState(),
                onNavMenuInicial = {},
                onNavMenuApont = {}
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SplashPagePreviewFailure() {
    PCPTheme {
        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
            SplashContent(
                modifier = Modifier.padding(innerPadding),
                flagMovOpen = false,
                onCloseDialog = {},
                status = UiStatusState(),
                onNavMenuInicial = {},
                onNavMenuApont = {}
            )
        }
    }
}