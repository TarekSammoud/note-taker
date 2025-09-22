package fr.eseo.ld.ts.notetaker.ui

import fr.eseo.ld.ts.notetaker.ui.screens.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import fr.eseo.ld.ts.notetaker.model.Note
import fr.eseo.ld.ts.notetaker.ui.navigation.NoteTakerScreens
import fr.eseo.ld.ts.notetaker.ui.screens.addNotes
import java.time.LocalDateTime

@Composable
public fun NoteTakerUi() {
    val notes = remember { mutableListOf<Note>().apply { addNotes(this) } }
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = NoteTakerScreens.SUMMARY_SCREEN.name
    ) {
        composable(NoteTakerScreens.SUMMARY_SCREEN.name) {
            SummaryScreen(notes = notes, navController = navController)

        }

        composable (
            NoteTakerScreens.DETAILS_SCREEN.name
        ) {
            DetailsScreen(navController, notes.get(0))
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
