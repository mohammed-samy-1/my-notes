package com.devmo.mynotes.feature_note.presentation.notes.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.FiniteAnimationSpec
import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Sort
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.devmo.mynotes.feature_note.domain.model.Note
import com.devmo.mynotes.feature_note.presentation.notes.NotesEvent
import com.devmo.mynotes.feature_note.presentation.notes.NotesViewModel
import com.devmo.mynotes.feature_note.presentation.util.Screen
import kotlinx.coroutines.launch

@OptIn(ExperimentalFoundationApi::class)
@Composable

fun NotesScreen(
    navController: NavController,
    viewModel: NotesViewModel = hiltViewModel()
) {
    val state = viewModel.state.value
    val snakeBarHostState = remember {
        SnackbarHostState()
    }
    val scope = rememberCoroutineScope()

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    navController.navigate(Screen.AddEditNotesScreen.route)
                },
                containerColor = MaterialTheme.colorScheme.primaryContainer,
                contentColor = MaterialTheme.colorScheme.onPrimaryContainer
            ) {
                Icon(imageVector = Icons.Default.Add, contentDescription = "Add note")
            }
        },
        snackbarHost = { SnackbarHost(snakeBarHostState) },
        containerColor = MaterialTheme.colorScheme.background,
        contentColor = MaterialTheme.colorScheme.onBackground,
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Your notes",
                    style = MaterialTheme.typography.headlineMedium
                )
                IconButton(
                    onClick = { viewModel.onEvent(NotesEvent.ToggleOrderSection) }
                ) {
                    Icon(imageVector = Icons.Default.Sort, contentDescription = "sort")
                }
            }
            AnimatedVisibility(
                visible = state.isOrderSectionVisible,
            ) {
                OrderSection(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 16.dp), onOrderChanged = {
                        viewModel.onEvent(NotesEvent.Order(it))
                    },
                    noteOrder = state.noteOrder
                )
            }
            Spacer(modifier = Modifier.height(16.dp))
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(state.notes, key = {
                    it.id?:0 }) { note ->
                    NoteItem(
                        modifier = Modifier
                            .animateItemPlacement()
                            .fillMaxWidth()
                            .clickable {
                                navController.navigate(
                                    Screen.AddEditNotesScreen.route + "?noteId=${note.id}&noteColor=${note.color}"
                                )
                            },
                        note = note,
                        onDeleteClicked = {
                            viewModel.onEvent(NotesEvent.Delete(note))
                            scope.launch {
                                val result = snakeBarHostState.showSnackbar(
                                    message = "note ${note.title} deleted",
                                    actionLabel = "Undo",
                                    duration = SnackbarDuration.Short
                                )
                                if (result == SnackbarResult.ActionPerformed) {
                                    viewModel.onEvent(NotesEvent.Restore)
                                }
                            }
                        }
                    )
                }
            }
        }
    }
}
