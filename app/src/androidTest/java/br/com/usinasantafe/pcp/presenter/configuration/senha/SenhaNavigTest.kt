package br.com.usinasantafe.pcp.presenter.configuration.senha

import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.navigation.compose.ComposeNavigator
import androidx.navigation.testing.TestNavHostController
import br.com.usinasantafe.pcp.presenter.NavigationGraph
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class SenhaNavigTest {

    @get:Rule
    val composeTestRule = createComposeRule()
    lateinit var navController: TestNavHostController

    @Before
    fun setupNavHost() {
        composeTestRule.setContent {
            navController = TestNavHostController(LocalContext.current)
            navController.navigatorProvider.addNavigator(ComposeNavigator())
            NavigationGraph(navHostController = navController, startDestination = "senha")
        }
    }

    @Test
    fun verify_click_button_cancel_to_menu_initial_screen() {
        composeTestRule.onNodeWithText("CANCELAR")
            .performClick()
        val route = navController.currentBackStackEntry?.destination?.route
        assertEquals(route, "menuinicial")
    }
}