package fr.eseo.ld.ts.notetaker.ui.screens

import androidx.compose.runtime.*
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import fr.eseo.ld.ts.notetaker.R
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.HistoricalChange
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import fr.eseo.ld.ts.notetaker.model.Note
import fr.eseo.ld.ts.notetaker.ui.viewmodels.NoteTakerViewModel
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit
import java.util.Date

@OptIn(ExperimentalMaterial3Api::class)
@Composable
public fun DetailsScreen(navController: NavController,noteId : String,
                         viewModel : NoteTakerViewModel
)
{
    val note by viewModel.note.collectAsState()
    var title by remember { mutableStateOf(note?.title ?: "") }
    var body by remember {mutableStateOf(note?.body ?: "")}
    var author by remember {mutableStateOf(note?.author ?: "")}
    var id by remember {mutableStateOf(note?.id)}
    val date = LocalDateTime.now()

    LaunchedEffect(noteId, note) {
        if(noteId == "NEW") {
            id = null;
            title = ""
            body = ""
            author = "Bob"
        }
        else {
            viewModel.getNoteById(noteId)
            note?.let {
                    note ->
                id = note.id
                title = note.title
                body = note.body
                author = note.author
            }
        }
    }

    Surface(
        modifier = Modifier
            .fillMaxSize()
            .statusBarsPadding(),
        color = MaterialTheme.colorScheme.background
    ) {
        Scaffold(
            topBar ={
                TopAppBar(
                    title = {
                        Text(
                            text = stringResource(R.string.app_name)
                        )
                    },
                    navigationIcon = {
                        IconButton(
                            onClick = {
                                navController.popBackStack()
                            }
                        ) {
                            Icon(
                                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                                contentDescription = stringResource(R.string.back)
                            )
                        }
                    },
                    actions = {
                        IconButton(
                            onClick = {
                                val newNote = Note(
                                    creationDate = note?.creationDate ?: date,
                                    modificationDate = date,
                                    author = author,
                                    body =body,
                                    title = title,
                                    id = note?.id ?: createId(date)
                                )
                                viewModel.addOrUpdateNote(newNote)
                                navController.navigateUp()

                            }
                        ) {
                            Icon(
                                imageVector = Icons.Filled.Done,
                                contentDescription = stringResource(R.string.save_note)
                            )
                        }
                    }
                )
            },
            content = {

                    innerPadding ->
                DetailsScreenNoteCard(
                    title = title,
                    body = body,
                    author = author,
                    creationDate =  note?.creationDate ?: date ,
                    modificationDate = note?.modificationDate ?: date ,
                    onBodyChange = {body = it},
                    onTitleChange = {title = it},
                    modifier = Modifier.padding(innerPadding)
                )
            }
        )
    }
}



@Composable
private fun DetailsScreenNoteCard(title: String,
                                  body: String,
                                  author: String,
                                  creationDate: LocalDateTime,
                                  modificationDate: LocalDateTime,
                                  modifier: Modifier = Modifier,
                                  onTitleChange: (String) -> Unit,
                                  onBodyChange: (String) -> Unit
){

    Card(
        modifier = modifier
            .fillMaxSize(),
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp),
    )
    {
        TextField(
            value = title,
            singleLine = true,
            textStyle = MaterialTheme.typography.titleLarge,
            onValueChange = onTitleChange,
            label = {
                Text(
                    text = stringResource(R.string.title_label)
                )
            },
            modifier = Modifier
                .fillMaxWidth()
        )
        Spacer(
            modifier = Modifier.height(8.dp)
        )

        TextField(
            value = body,
            textStyle = MaterialTheme.typography.bodyMedium,
            onValueChange = onBodyChange,
            label = {Text(
                text = stringResource(R.string.body_label)
            )},
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
        )
        Spacer(
            modifier = Modifier.height(8.dp)
        )

        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Text(text = stringResource(R.string.created_by))
            Text(text = author)
        }
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Text(text = stringResource(R.string.creation_date))
            Text(text = displayDate(creationDate as LocalDateTime, true))
        }
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Text(text = stringResource(R.string.modification_date))
            Text(text = displayDate(modificationDate as LocalDateTime, true))
        }

    }

}

private fun displayDate(date : LocalDateTime, since : Boolean) : String {
    val formatter = DateTimeFormatter.ofPattern("dd/MM/yy hh:mm")
    var display = formatter.format(date);
    if(since){
        val daysAgo = ChronoUnit.DAYS.between(date.toLocalDate(), LocalDate.now())
        display = display.plus(" (${daysAgo} days ago)")
    }
    return display
}

private fun createId(date:LocalDateTime) : String {
    val formatter = DateTimeFormatter.ofPattern("yyMMddhhmmss")
    return formatter.format(date)
}