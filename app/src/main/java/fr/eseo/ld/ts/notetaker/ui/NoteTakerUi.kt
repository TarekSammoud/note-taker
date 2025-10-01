package fr.eseo.ld.ts.notetaker.ui

import android.app.Application
import fr.eseo.ld.ts.notetaker.ui.screens.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import fr.eseo.ld.ts.notetaker.model.Note
import fr.eseo.ld.ts.notetaker.repositories.NoteTakerRepositoryListImpl
import fr.eseo.ld.ts.notetaker.repositories.NoteTakerRepositoryRoomImpl
import fr.eseo.ld.ts.notetaker.ui.navigation.NoteTakerScreens
import fr.eseo.ld.ts.notetaker.ui.screens.addNotes
import fr.eseo.ld.ts.notetaker.ui.viewmodels.NoteTakerViewModel
import fr.eseo.ld.ts.notetaker.ui.viewmodels.NoteTakerViewModelFactory
import java.time.LocalDateTime

@Composable
public fun NoteTakerUi() {

    val application = LocalContext.current.applicationContext as Application

    val navController = rememberNavController()
    val viewModel : NoteTakerViewModel = viewModel(
        factory = NoteTakerViewModelFactory(
            NoteTakerRepositoryRoomImpl(application)
        )
    )

    NavHost(
        navController = navController,
        startDestination = NoteTakerScreens.SUMMARY_SCREEN.name
    ) {
        composable(NoteTakerScreens.SUMMARY_SCREEN.name) {
            SummaryScreen(viewModel = viewModel, navController = navController)

        }

        composable (
            NoteTakerScreens.DETAILS_SCREEN.name+"/{noteId}",
            arguments = listOf(
                navArgument("noteId") {
                    type = NavType.StringType
                }
            )
        ) {
                backStackEntry ->
            val noteId = backStackEntry.arguments?.getString("noteId") ?: "NEW"
            viewModel.getNoteById(noteId)
            DetailsScreen(navController, noteId, viewModel)
        }

    }
}





fun addNotes(notes: MutableList<Note>) {
    notes.add(Note(
        id = "1",
        author = "Tarek",
        title = "Fight Club Review",
        body = "Fight Club is an absolute masterpiece that keeps you on the edge of your seat from start to finis are explored in such a raw, thought-provoking way. The gritty aesthetic and David Fincher's direction make this a must-watch for any film enthusiast.",
        creationDate = LocalDateTime.now(),
        modificationDate = LocalDateTime.now()
    ))
    notes.add(Note(
        id = "2",
        author = "Tarek",
        title = "Inception Thoughts",
        body = "Inception is a mind-bending dream  level offering unique visuals and stakes. Leonardo DiCaprio's performance as Cobb is gripping, and the supporting cast, including Joseph Gordon-Levitt and Marion Cotillttable. Christopher Nolan outdid himself with this one!",
        creationDate = LocalDateTime.now().minusDays(1),
        modificationDate = LocalDateTime.now()
    ))
}
