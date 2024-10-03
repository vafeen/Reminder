package ru.vafeen.reminder.ui.common.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import ru.vafeen.reminder.noui.local_database.entity.Reminder
import ru.vafeen.reminder.ui.common.viewmodel.EventCreatorViewModel
import ru.vafeen.reminder.ui.theme.FontSize
import ru.vafeen.reminder.ui.theme.Theme

@Composable
fun Reminder.ReminderDataString(
    modifier: Modifier = Modifier,
    viewModel: EventCreatorViewModel
) {
    val cor = rememberCoroutineScope()
    var isDialogDeleteShows by remember {
        mutableStateOf(false)
    }
    if (isDialogDeleteShows) RemoveReminderDialog(onDismissRequest = {
        isDialogDeleteShows = false
    }) {
        viewModel.removeEvent(reminder = this)
    }
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(10.dp),
        colors = CardDefaults.cardColors(containerColor = Theme.colors.buttonColor)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp)
        ) {
            Column(
                modifier = Modifier.weight(1f)
            ) {
                TextForThisTheme(text = this@ReminderDataString.title, fontSize = FontSize.big22)
                TextForThisTheme(text = this@ReminderDataString.text, fontSize = FontSize.big22)
                TextForThisTheme(
                    text = this@ReminderDataString.dt.toString(),
                    fontSize = FontSize.big22
                )
            }
            IconButton(onClick = { isDialogDeleteShows = true }) {
                Icon(imageVector = Icons.Default.Delete, contentDescription = "Delete reminder ")
            }
        }
    }
}