/*
 * Copyright 2022 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package pl.mankevich.designsystem.component

import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.LocalRippleConfiguration
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import pl.mankevich.designsystem.icons.RnmIcons
import pl.mankevich.designsystem.theme.RnmTheme
import pl.mankevich.designsystem.theme.ThemePreviews

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Chip(
    label: String,
    textStyle: TextStyle = MaterialTheme.typography.bodySmall,
    icon: ImageVector,
    iconSize: Dp = 16.dp,
    isSelected: Boolean,
    isRippleEnabled: Boolean = true,
    onClick: (Boolean) -> Unit,
    modifier: Modifier = Modifier,
) {
    CompositionLocalProvider(
        LocalRippleConfiguration provides if (isRippleEnabled) LocalRippleConfiguration.current else null
    ) {
        FilterChip(
            selected = isSelected,
            onClick = { onClick(!isSelected) },
            label = {
                IconText(
                    text = label,
                    icon = icon,
                    iconSize = iconSize,
                    iconTint = if (isSelected) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.onSurface,
                    textColor = if (isSelected) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.onSurface,
                    textStyle = textStyle,
                    modifier = Modifier.wrapContentSize()
                )
            },
            border = FilterChipDefaults.filterChipBorder(
                enabled = true,
                selected = isSelected,
                borderColor = Color.Transparent,
            ),
            modifier = modifier,
            shape = CircleShape,
            colors = FilterChipDefaults.filterChipColors(
                containerColor = MaterialTheme.colorScheme.surface,
                selectedContainerColor = MaterialTheme.colorScheme.primary,
            ),
        )
    }
}

@ThemePreviews
@Composable
fun ChipTruePreview() {
    RnmTheme {
        var isSelected by remember { mutableStateOf(true) }
        Chip(
            label = "Parasite",
            icon = RnmIcons.Blocks,
            isSelected = isSelected,
            isRippleEnabled = false,
            onClick = { isSelected = it },
            modifier = Modifier.wrapContentSize()
        )
    }
}

@ThemePreviews
@Composable
fun ChipFalsePreview() {
    RnmTheme {
        var isSelected by remember { mutableStateOf(false) }
        Chip(
            label = "Chisafasdfp",
            icon = RnmIcons.Alien,
            isSelected = isSelected,
            onClick = { isSelected = it },
            modifier = Modifier.wrapContentSize()
        )
    }
}
