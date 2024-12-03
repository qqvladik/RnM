package pl.mankevich.designsystem.ui.filter

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.DialogProperties
import pl.mankevich.designsystem.component.SurfaceIconButton
import pl.mankevich.designsystem.icons.RnmIcons
import pl.mankevich.designsystem.theme.RnmTheme
import pl.mankevich.designsystem.theme.ThemePreviews

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun FilterDialog(
    name: String,
    showSettingsDialog: Boolean,
    filterGroupList: List<FilterGroup>,
    chipPadding: Dp,
    chipHeight: Dp,
    onDismissed: () -> Unit,
) {
    if (showSettingsDialog) {
        val configuration = LocalConfiguration.current

        /**
         * usePlatformDefaultWidth = false is use as a temporary fix to allow
         * height recalculation during recomposition. This, however, causes
         * Dialog's to occupy full width in Compact mode. Therefore max width
         * is configured below. This should be removed when there's fix to
         * https://issuetracker.google.com/issues/221643630
         */
        AlertDialog(
            properties = DialogProperties(usePlatformDefaultWidth = false),
            modifier = Modifier.widthIn(max = (configuration.screenWidthDp * 0.9).dp),
            onDismissRequest = { onDismissed() },
            title = {
                Text(
                    text = name,
                    style = MaterialTheme.typography.titleLarge,
                    color = MaterialTheme.colorScheme.onSurface
                )
            },
            text = {
                /* https://issuetracker.google.com/issues/270983047, https://issuetracker.google.com/issues/289117017 - only emulator bug.
                1. Turn on your emulator/device Developer Options;
                2. Scroll down to the section 'Hardware accelerated rendering';
                3. Then, just enable the 'Disable HW overlays'.

                (LocalView.current.parent as DialogWindowProvider).window.setDimAmount(0f) */

                HorizontalDivider()

                Column(
                    Modifier.verticalScroll(rememberScrollState()),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    Spacer(modifier = Modifier.height(8.dp))

                    filterGroupList.forEach { filterGroupData ->
                        FilterGroupView(
                            filterGroup = filterGroupData,
                            chipPadding = chipPadding,
                            chipHeight = chipHeight,
                            modifier = Modifier.fillMaxWidth()
                        )
                    }
                }
            },
            confirmButton = {
                Button(
                    onClick = { onDismissed() },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.Transparent,
                    )
                ) {
                    Text(
                        text = "OK",
                        color = MaterialTheme.colorScheme.primary
                    )
                }
            },
            containerColor = MaterialTheme.colorScheme.background,
        )
    }
}

@OptIn(ExperimentalLayoutApi::class, ExperimentalMaterial3Api::class)
@Composable
fun FilterGroupView(
    filterGroup: FilterGroup,
    chipPadding: Dp,
    chipHeight: Dp,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
    ) {
        Text(
            text = filterGroup.name,
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.onSurface
        )

        Spacer(modifier = Modifier.height(8.dp))

        FlowRow(
            horizontalArrangement = Arrangement.spacedBy(chipPadding),
            verticalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            filterGroup.labelList.forEach { label ->
                FilterChip(
                    label = label,
                    filterGroup = filterGroup,
                    modifier = Modifier.height(chipHeight)
                )
            }

            var isInputVisible by rememberSaveable { mutableStateOf(false) }
            var inputValue by rememberSaveable { mutableStateOf("") }
            val focusRequester = remember { FocusRequester() }

            if (isInputVisible) {
                BasicTextField(
                    value = inputValue,
                    onValueChange = {
                        inputValue = it.filter { !it.isWhitespace() }
                    },
                    textStyle = MaterialTheme.typography.bodySmall.copy(color = MaterialTheme.colorScheme.onSurface),
                    modifier = Modifier
                        .padding(start = 8.dp)
                        .height(chipHeight)
                        .focusRequester(focusRequester)
                        .defaultMinSize(minWidth = 50.dp)
                        .weight(1f),
                    singleLine = true,
                    keyboardActions = KeyboardActions(
                        onDone = {
                            filterGroup.selectValue(inputValue)
                            inputValue = ""
                            focusRequester.freeFocus()
                            isInputVisible = false
                        },
                    ),
                    cursorBrush = SolidColor(MaterialTheme.colorScheme.primary),
                ) { innerTextField ->
                    TextFieldDefaults.DecorationBox(
                        value = inputValue,
                        enabled = true,
                        visualTransformation = VisualTransformation.None,
                        interactionSource = remember { MutableInteractionSource() },
                        innerTextField = innerTextField,
                        singleLine = true,
                        contentPadding = PaddingValues(0.dp),

                        colors = TextFieldDefaults.colors(
                            unfocusedContainerColor = Color.Transparent,
                            focusedContainerColor = Color.Transparent,
                            unfocusedIndicatorColor = Color.Transparent,
                        ),
                    )
                }

                //Executed at the end of composition and solves the problem with requestFocus being called during composition
                LaunchedEffect(isInputVisible) {
                    if (isInputVisible) {
                        focusRequester.requestFocus()
                    }
                }
            } else {
                if (!filterGroup.isListFinished) {
                    SurfaceIconButton(
                        onClick = {
                            isInputVisible = true
                        },
                        imageVector = Icons.Default.Add,
                        contentDescription = "Add filter",
                        iconSize = 16.dp,
                        modifier = Modifier.size(chipHeight)
                    )
                }
            }
        }
    }
}

private fun FilterGroup.selectValue(value: String) {
    if (value.isNotBlank()) {
        //Check value originality
        if (!labelList.any { it.value.equals(value, ignoreCase = true) }) {
            onAddLabel(value)
            onSelectedChanged(value)
        } else {
            onSelectedChanged(value)
        }
    }
}

@ThemePreviews
@Composable
fun FilterDialogPreview() {
    RnmTheme {
        FilterDialog(
            name = "Characters filter",
            showSettingsDialog = true,
            filterGroupList = listOf(
                FilterGroup(
                    name = "Species",
                    selected = "Alien",
                    labelList = listOf("Alien", "Human", "Humanoid", "Robo"),
                    resolveIcon = { text -> RnmIcons.Alien },
                    onAddLabel = {},
                    onSelectedChanged = {},
                    isListFinished = false
                )
            ),
            chipPadding = 4.dp,
            chipHeight = 32.dp,
            onDismissed = {}
        )
    }
}

@ThemePreviews
@Composable
fun FlowOfChipsPreview() {
    RnmTheme {
        FilterGroupView(
            filterGroup = FilterGroup(
                name = "Species",
                selected = "Alien",
                labelList = listOf("Alien", "Human", "Humanoid", "Robo"),
                resolveIcon = { text -> RnmIcons.Alien },
                onAddLabel = {},
                onSelectedChanged = {},
                isListFinished = true
            ),
            chipPadding = 4.dp,
            chipHeight = 32.dp,
            modifier = Modifier.width(300.dp)
        )
    }
}