package pl.mankevich.characterslist.presentation.view

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp

@Composable
fun EmptyView(
    modifier: Modifier = Modifier,
    text: String = "Empty",
) {
    Box(
        modifier = modifier
    ) {
        Text(
            modifier = Modifier
                .fillMaxSize()
                .align(Alignment.Center),
            text = text,
            textAlign = TextAlign.Center, fontSize = 16.sp,
        )
    }
}