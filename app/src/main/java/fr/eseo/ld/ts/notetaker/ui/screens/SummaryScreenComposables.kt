package fr.eseo.ld.ts.notetaker.ui.screens

import fr.eseo.ld.ts.notetaker.R
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import fr.eseo.ld.ts.notetaker.model.Note
import fr.eseo.ld.ts.notetaker.ui.navigation.NoteTakerScreens
import fr.eseo.ld.ts.notetaker.ui.theme.NoteTakerTheme
import java.time.LocalDateTime


@Preview(
    showBackground = true,
    showSystemUi = true
)




@Composable
fun NotesComposePreview(){
    val notes = remember { mutableListOf<Note>().apply { addNotes(this) } }
    NoteTakerTheme {
     //   SummaryScreen(notes)
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SummaryScreen(notes: List<Note> = emptyList(),
                  navController: NavController,
                  modifier: Modifier= Modifier
){
    Surface(
        modifier = Modifier
            .fillMaxSize()
            .statusBarsPadding(),
        color = MaterialTheme.colorScheme.background
    ) {
        Scaffold(
            topBar = {
                TopAppBar (
                    title = {
                        Text(
                            text = stringResource(R.string.app_name)
                        )
                    }
                )
            },
            floatingActionButton = {
                FloatingActionButton(
                    onClick = {
                        navController.navigate(NoteTakerScreens.DETAILS_SCREEN.name)

                    }
                ) {
                    Icon(
                        imageVector = Icons.Default.Add,
                        contentDescription = stringResource(R.string.add_note)
                    )
                }
            },

            content = {
                    innerPadding ->
                SummaryScreenNotesList(
                    notes = notes,
                    modifier = Modifier.padding(innerPadding)
                )
            }
        )
    }
}


@Composable
private fun SummaryScreenNoteCard(note: Note, modifier: Modifier = Modifier) {
    Card(
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        modifier = modifier.padding(4.dp)
    ) {
        Column (modifier = Modifier.padding(8.dp)) {
            Text(
                text = note.title,
                fontSize = MaterialTheme.typography.titleMedium.fontSize,
                fontWeight = FontWeight.Medium,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = note.body,
                fontSize = MaterialTheme.typography.bodyMedium.fontSize,
                fontWeight = FontWeight.Normal,
                maxLines = 3, // limit to 3 lines
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}

@Composable
private fun SummaryScreenNotesList(notes: List<Note>,
                                   modifier: Modifier = Modifier){
    LazyVerticalStaggeredGrid(verticalItemSpacing = 8.dp,
        columns = StaggeredGridCells.Fixed(2),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        modifier = modifier
            .padding(8.dp)) {
        items(notes) {
            note ->
            SummaryScreenNoteCard(note)
        }
    }
}