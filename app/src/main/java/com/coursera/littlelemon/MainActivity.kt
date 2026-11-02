package com.coursera.littlelemon

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.adaptive.navigationsuite.NavigationSuiteScaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.coursera.littlelemon.ui.theme.LittleLemonAndroidTheme
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
    var currentDestination by rememberSaveable { mutableStateOf(AppDestinations.HOME) }

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
                navController.navigate("home") {
                    popUpTo("onboarding") { inclusive = true }
                }
            })
        }
        composable("home") {
            HomeScreen(onProfileClick = { navController.navigate("profile") })
        }
        composable("profile") {
            ProfileScreen(
                user = currentUser,
                onLogout = {
                    userViewModel.logout()
                    navController.navigate("onboarding") {
                        popUpTo(navController.graph.id) { inclusive = true }
                    }
                }
            )
        }
    }

//    NavigationSuiteScaffold(
//        navigationSuiteItems = {
//            AppDestinations.entries.forEach {
//                item(
//                    icon = {
//                        Icon(
//                            painterResource(it.icon),
//                            contentDescription = it.label
//                        )
//                    },
//                    label = { Text(it.label) },
//                    selected = it == currentDestination,
//                    onClick = {
//                        currentDestination = it
//                        navController.navigate(it.name)
//                    }
//                )
//            }
//        }
//    ) {
//        NavHost(navController = navController, startDestination = AppDestinations.HOME.name) {
//            composable(AppDestinations.HOME.name) {
//                HomeScreen()
//            }
//            composable(AppDestinations.FAVORITES.name) {
//                FavoritesScreen()
//            }
//            composable(AppDestinations.PROFILE.name) {
//                ProfileScreen()
//            }
//        }
//    }
}

enum class AppDestinations(
    val label: String,
    val icon: Int,
) {
    HOME("Home", R.drawable.ic_home),
    FAVORITES("Favorites", R.drawable.ic_favorite),
    PROFILE("Profile", R.drawable.ic_account_box),
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    LittleLemonAndroidTheme {
        Greeting("Android")
    }
}