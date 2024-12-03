package pl.mankevich.designsystem.component

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.unit.dp
import pl.mankevich.designsystem.theme.RnmTheme
import pl.mankevich.designsystem.theme.ThemePreviews
import pl.mankevich.designsystem.utils.keyboardAsState

@Composable
fun SearchField(
    value: String,
    onValueChange: (String) -> Unit,
    placeholder: String = "Search...",
    onClearClick: () -> Unit = {},
    modifier: Modifier = Modifier,
) {
    OutlinedTextField(
        value = value,
        textStyle = MaterialTheme.typography.bodyMedium,
        onValueChange = onValueChange,
        placeholder = {
            Text(
                text = placeholder,
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f),
                style = MaterialTheme.typography.bodyMedium
            )
        },
        trailingIcon = {
            if (value.isNotEmpty()) {
                IconButton(onClick = onClearClick) {
                    Icon(
                        imageVector = Icons.Default.Close,
                        contentDescription = "Clear Search",
                        tint = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
                    )
                }
            }
        },
        singleLine = true,
        shape = CircleShape,
        colors = TextFieldDefaults.colors(
            focusedTextColor = MaterialTheme.colorScheme.onSurface,
            unfocusedTextColor = MaterialTheme.colorScheme.onSurface,
            cursorColor = MaterialTheme.colorScheme.primary,
            unfocusedContainerColor = MaterialTheme.colorScheme.surface,
//            unfocusedContainerColor = Color.Transparent,
            focusedContainerColor = MaterialTheme.colorScheme.background, //should not be transparent to remove intermediate gray color during animation
//            focusedContainerColor = MaterialTheme.colorScheme.surface,
//            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
        ),
        modifier = modifier
            .fillMaxWidth()
            .height(56.dp),
    )

    // Clear focus if keyboard was hidden by user
    val isKeyboardOpen by keyboardAsState()
    val focusManager = LocalFocusManager.current
    if (!isKeyboardOpen) {
        focusManager.clearFocus()
    }
}

@ThemePreviews
@Composable
fun SearchFieldPreview() {
    RnmTheme {
        SearchField(
            value = "Rick Sanchez",
            onValueChange = { },
            modifier = Modifier
                .fillMaxWidth(),
        )
    }
}

@ThemePreviews
@Composable
fun SearchFieldBlankPreview() {
    RnmTheme {
        SearchField(
            value = "",
            onValueChange = { },
            modifier = Modifier
                .fillMaxWidth(),
        )
    }
}
