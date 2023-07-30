package com.devmo.mynotes.feature_note.presentation.add_edit_note.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.input.pointer.motionEventSpy
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp

@Composable
fun FormattingBar(
    modifier: Modifier = Modifier,
    fontSize: TextUnit = MaterialTheme.typography.bodyLarge.fontSize,
    isBald: Boolean = false,
    isItalic: Boolean = false,
    isUnderLined: Boolean = false
) {
    val offset by remember {
        mutableStateOf(Offset.Zero)
    }
    Row(
        modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.tertiary)
            .padding(horizontal = 8.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceAround
    ) {
        Text(
            "Format Content",
            color = MaterialTheme.colorScheme.onTertiary

        )
        var expandedState by remember {
            mutableStateOf(false)
        }
        Row(
            Modifier.clickable { expandedState = !expandedState }
        ) {
            DropdownMenu(
                expanded = expandedState, onDismissRequest = { /*TODO*/ }
            ) {
                DropdownMenuItem(text = { Text(text = "1") }, onClick = {

                })
            }
            Icon(
                imageVector = Icons.Default.FormatSize, contentDescription = "Decrease size",
                tint = MaterialTheme.colorScheme.onTertiary
            )
        }
        Icon(
            imageVector = Icons.Default.FormatBold,
            contentDescription = "Left to right",
            tint = MaterialTheme.colorScheme.onTertiary
        )
        Icon(
            imageVector = Icons.Default.FormatItalic,
            contentDescription = "Left to right",
            tint = MaterialTheme.colorScheme.onTertiary
        )
        Icon(
            imageVector = Icons.Default.FormatUnderlined,
            contentDescription = "Left to right",
            tint = MaterialTheme.colorScheme.onTertiary
        )
    }
}

@Preview
@Composable
fun FormattingBarPrev() {
    Box(modifier = Modifier.fillMaxSize()) {
        FormattingBar()
    }
}