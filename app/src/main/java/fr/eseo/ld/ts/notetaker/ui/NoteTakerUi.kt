package fr.eseo.ld.ts.notetaker.ui

import android.app.Application
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import fr.eseo.ld.ts.notetaker.model.Note
import fr.eseo.ld.ts.notetaker.repositories.NoteTakerRepositoryRoomImpl
import fr.eseo.ld.ts.notetaker.ui.navigation.NoteTakerScreens
import fr.eseo.ld.ts.notetaker.ui.screens.*
import fr.eseo.ld.ts.notetaker.ui.viewmodels.AuthenticationViewModel
import fr.eseo.ld.ts.notetaker.ui.viewmodels.NoteTakerViewModel
import fr.eseo.ld.ts.notetaker.ui.viewmodels.NoteTakerViewModelFactory
import java.time.LocalDateTime

@Composable
fun NoteTakerUi() {

    val application = LocalContext.current.applicationContext as Application
    val authenticationViewModel: AuthenticationViewModel = hiltViewModel()

    val navController = rememberNavController()
    val viewModel: NoteTakerViewModel = hiltViewModel()


    NavHost(
        navController = navController,
        startDestination = "start"
    ) {

        // START route - login or navigate
        composable("start") {
            val user by authenticationViewModel.user.collectAsState()

            // Show some UI while checking user login status
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                if (user == null) {
                    Text(text = "Logging in anonymously...")
                }
            }

            // Trigger anonymous login once if user is null
            LaunchedEffect(user) {
                if (user == null) {
                    authenticationViewModel.loginAnonymously()
                } else {
                    // Navigate to SummaryScreen when user is available
                    navController.navigate(NoteTakerScreens.SUMMARY_SCREEN.name) {
                        popUpTo("start") { inclusive = true }
                    }
                }
            }
        }

        // Summary Screen
        composable(NoteTakerScreens.SUMMARY_SCREEN.name) {
            SummaryScreen(
                viewModel = viewModel,
                navController = navController,
                authenticationViewModel = authenticationViewModel
            )
        }

        // Connection Screen
        composable(NoteTakerScreens.CONNECTION_SCREEN.name) {
            ConnectionScreen(navController, authenticationViewModel)
        }

        // Details Screen
        composable(
            NoteTakerScreens.DETAILS_SCREEN.name + "/{noteId}",
            arguments = listOf(
                navArgument("noteId") {
                    type = NavType.StringType
                }
            )
        ) { backStackEntry ->
            val noteId = backStackEntry.arguments?.getString("noteId") ?: "NEW"
            viewModel.getNoteById(noteId)
            DetailsScreen(
                navController = navController,
                noteId = noteId,
                viewModel = viewModel,
                authenticationViewModel = authenticationViewModel
            )
        }
    }
}

// Optional: keep this for dummy data
