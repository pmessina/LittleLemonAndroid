package com.coursera.littlelemon

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.coursera.littlelemon.ui.theme.LittleLemonAndroidTheme
import com.coursera.littlelemon.users.UserViewModel
import com.coursera.littlelemon.views.HomeScreen
import com.coursera.littlelemon.views.OnboardingScreen
import org.koin.androidx.compose.koinViewModel
import com.coursera.littlelemon.views.ProfileScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            LittleLemonAndroidTheme {
                LittleLemonAndroidApp()
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview(showBackground = true, device = "spec:width=411dp,height=891dp")
@Composable
fun LittleLemonAndroidApp(userViewModel: UserViewModel = koinViewModel()) {

    val userExists by userViewModel.userExists.collectAsStateWithLifecycle()
    val currentUser by userViewModel.currentUser.collectAsStateWithLifecycle()
    val navController = rememberNavController()

    // Decide the start destination once; later user changes must not reset the back stack
    var startDestination by rememberSaveable { mutableStateOf<String?>(null) }
    if (startDestination == null) {
        val exists = userExists ?: return
        startDestination = if (exists) "home" else "onboarding"
    }
    NavHost(
        navController = navController,
        startDestination = startDestination!!
    ) {
        composable("onboarding") {
            OnboardingScreen(onRegister = { user ->
                userViewModel.addUser(user)
                navController.navigate("home")
            })
        }
        composable("home") {
            val goToOnboarding = {
                if (!navController.popBackStack("onboarding", inclusive = false)) {
                    navController.navigate("onboarding")
                }
            }
            BackHandler(onBack = goToOnboarding)
            HomeScreen(
                onProfileClick = { navController.navigate("profile") },
                onBackClick = goToOnboarding
            )
        }
        composable("profile") {
            ProfileScreen(
                user = currentUser,
                onBack = { navController.popBackStack() },
                onLogout = {
                    userViewModel.logout()
                    navController.navigate("onboarding") {
                        popUpTo(navController.graph.id) { inclusive = true }
                    }
                }
            )
        }
    }
}



