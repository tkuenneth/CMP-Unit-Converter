package de.thomaskuenneth.cmp

import androidx.compose.animation.Crossfade
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import cmpunitconverter.composeapp.generated.resources.Res
import cmpunitconverter.composeapp.generated.resources.show_as_list
import org.jetbrains.compose.resources.stringResource

@Composable
fun ListGridDemo() {
    val list = (1..42).toList()
    MaterialTheme {
        SimpleScreen(list)
    }
}

@Composable
fun SimpleScreen(
    list: List<Int>
) {
    var toggle by remember { mutableStateOf(true) }
    Column(modifier = Modifier.fillMaxSize()) {
        Row(modifier = Modifier.padding(all = 16.dp).clickable { toggle = !toggle },
            verticalAlignment = Alignment.CenterVertically) {
            Switch(checked = toggle, onCheckedChange = { toggle = it })
            Spacer(modifier = Modifier.width(8.dp))
            Text(stringResource(Res.string.show_as_list))
        }
        Crossfade(targetState = toggle) {
            if (it) {
                NumbersList(list = list)
            } else {
                NumbersGrid(list = list)
            }
        }
    }
}

@Composable
private fun NumbersItem(number: Int) {
    Text(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp, horizontal = 16.dp)
            .border(width = 1.dp,
        color = MaterialTheme.colorScheme.primary),
        text = number.toString(),
        style = MaterialTheme.typography.displayLarge,
        textAlign = TextAlign.Center
    )
}

@Composable
fun NumbersList(
    list: List<Int>, modifier: Modifier = Modifier
) {
    val state = rememberLazyListState()
    LazyColumn(
        state = state,
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(
            items = list
        ) {
            NumbersItem(it)
        }
    }
}

@Composable
fun NumbersGrid(
    list: List<Int>, modifier: Modifier = Modifier, columns: Int = 2
) {
    LazyVerticalGrid(
        modifier = modifier,
        columns = GridCells.Fixed(columns),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(
            items = list
        ) {
            NumbersItem(number = it)
        }
    }
}
