package com.devmo.mynotes.feature_note.presentation.widget.component

import android.content.Context
import android.content.Intent
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.glance.*
import androidx.glance.action.clickable
import androidx.glance.appwidget.GlanceAppWidget
import androidx.glance.appwidget.GlanceAppWidgetReceiver
import androidx.glance.appwidget.cornerRadius
import androidx.glance.appwidget.lazy.LazyColumn
import androidx.glance.appwidget.lazy.items
import androidx.glance.appwidget.provideContent
import androidx.glance.layout.*
import androidx.glance.text.FontWeight
import androidx.glance.text.Text
import androidx.glance.text.TextStyle
import com.devmo.mynotes.feature_note.domain.model.Note
import com.devmo.mynotes.feature_note.presentation.MainActivity
import com.devmo.mynotes.feature_note.presentation.widget.EntryPoint
import dagger.hilt.EntryPoints
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


class AppWidget @Inject constructor() : GlanceAppWidget() {
    @Inject
    override suspend fun provideGlance(context: Context, id: GlanceId) {

        provideContent {
            GlanceTheme {
                MyContent()
            }
        }
    }

    @Composable
    private fun MyContent() {

        val context = LocalContext.current
        val viewModel = EntryPoints.get(
            context,
            EntryPoint::class.java
        ).vm()


        val list = viewModel.getNotes().collectAsState(initial = listOf())
        LazyColumn(
            modifier =
            GlanceModifier
                .fillMaxSize(),
            Alignment.Horizontal.CenterHorizontally
        ) {
            items(list.value) {
                Column {
                    Item(note = it)
                    Spacer(GlanceModifier.height(10.dp))
                }
            }
        }
    }

    @Composable
    private fun Item(note: Note) {
        val context = LocalContext.current
        Box(
            GlanceModifier.fillMaxWidth()
                .background(Color(note.color))
                .cornerRadius(10.dp)

        ) {
            Column(
                GlanceModifier.fillMaxWidth()
                    .padding(8.dp)
                    .clickable {
                        val intent = Intent(context, MainActivity::class.java).apply {
                            flags = Intent.FLAG_ACTIVITY_CLEAR_TASK.or(
                                Intent.FLAG_ACTIVITY_NEW_TASK
                            )
                            putExtra("id", note.id)
                            putExtra("color", note.color)
                            action = Intent.ACTION_EDIT
                        }
                        context.startActivity(intent)
                    }
            ) {
                Text(
                    text = note.title,
                    style = TextStyle(fontSize = 18.sp, fontWeight = FontWeight.Bold)
                )
                Text(
                    text = note.content,
                    style = TextStyle(fontSize = 14.sp)
                )
            }
        }
    }
}

@AndroidEntryPoint
class WidgetReceiver : GlanceAppWidgetReceiver() {
    override val glanceAppWidget: GlanceAppWidget = AppWidget()
}