package pl.mankevich.designsystem.ui.filter

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import pl.mankevich.designsystem.component.Chip
import pl.mankevich.designsystem.component.SurfaceIconButton
import pl.mankevich.designsystem.icons.RnmIcons
import pl.mankevich.designsystem.theme.RnmTheme
import pl.mankevich.designsystem.theme.ThemePreviews
import pl.mankevich.designsystem.utils.rememberSaveableMutableStateListOf
import kotlin.collections.first

data class FilterLabel(
    val groupName: String,
    val value: String,
)

/**
 * @param name must be unique
 * @param labelList must have unique values
 */
@ConsistentCopyVisibility
data class FilterGroup private constructor(
    val name: String,
    val selected: String? = null,
    val labelList: List<FilterLabel>,
    val isListFinished: Boolean,
    val resolveIcon: @Composable (String) -> ImageVector,
    val onAddLabel: (String) -> Unit,
    val onSelectedChanged: (String?) -> Unit,
) {
    companion object {
        /**
         * @param name must be unique
         * @param labelList must have unique values
         */
        operator fun invoke(
            name: String,
            selected: String? = null,
            labelList: List<String>,
            isListFinished: Boolean,
            resolveIcon: @Composable (String) -> ImageVector,
            onAddLabel: (String) -> Unit,
            onSelectedChanged: (String?) -> Unit,
        ) = FilterGroup(
            name = name,
            selected = selected,
            labelList = labelList.map { FilterLabel(name, it) },
            isListFinished = isListFinished,
            resolveIcon = resolveIcon,
            onAddLabel = onAddLabel,
            onSelectedChanged = onSelectedChanged,
        )
    }
}

@Composable
fun FilterView(
    name: String,
    filterGroupList: List<FilterGroup>,
    chipPadding: Dp = 4.dp,
    chipHeight: Dp = 32.dp,
    modifier: Modifier = Modifier.height(32.dp)
) {

    var showDialog by rememberSaveable { mutableStateOf(false) }
    if (showDialog) {
        FilterDialog(
            name = name,
            showSettingsDialog = showDialog,
            filterGroupList = filterGroupList,
            chipPadding = chipPadding,
            chipHeight = chipHeight,
            onDismissed = { showDialog = false }
        )
    }

    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
    ) {

        var labelList: List<FilterLabel> = mutableListOf()
        filterGroupList.forEach { labelList += it.labelList }

        val selectedLabelList = labelList.filter { filterLabel ->
            filterGroupList.any { filterGroup ->
                filterGroup.name == filterLabel.groupName && filterGroup.checkLabelSelected(
                    filterLabel.value
                )
            }
        }
        val unselectedLabelList = labelList.filter { filterLabel ->
            !selectedLabelList.contains(filterLabel)
        }

        val sortedLabelList = selectedLabelList + unselectedLabelList

        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(chipPadding),
            modifier = Modifier.weight(1f)
        ) {
            items(count = sortedLabelList.size) { index ->
                val label = sortedLabelList[index]
                var filterGroup = filterGroupList.first { it.name == label.groupName }
                FilterChip(
                    label = label,
                    filterGroup = filterGroup,
                    modifier = Modifier.height(chipHeight)
                )
            }
        }

        Spacer(modifier = Modifier.width(8.dp))

        SurfaceIconButton(
            onClick = {
                showDialog = true
            },
            imageVector = Icons.Default.Menu,
            contentDescription = "Show filters",
            iconSize = 16.dp,
            modifier = Modifier.size(chipHeight)
        )
    }
}

private fun FilterGroup.checkLabelSelected(label: String): Boolean =
    selected.equals(label, ignoreCase = true)

@Composable
fun FilterChip(
    label: FilterLabel,
    filterGroup: FilterGroup,
    modifier: Modifier = Modifier
) {
    val isSelected = filterGroup.checkLabelSelected(label.value)
    Chip(
        label = label.value,
        icon = filterGroup.resolveIcon(label.value),
        isSelected = isSelected,
        onSelectedChange = {
            val newSelected = if (isSelected) null else label.value
            filterGroup.onSelectedChanged(newSelected)
        },
        modifier = modifier
    )
}

@ThemePreviews
@Composable
fun FilterViewPreview() {
    RnmTheme {
        var selectedSpecies by rememberSaveable { mutableStateOf("Alien") }
        var speciesLabelList = rememberSaveableMutableStateListOf(
            "Alien", "Human", "Humanoid", "Robo"
        )

        var selectedGender by rememberSaveable { mutableStateOf<String?>(null) }
        var genderLabelList = rememberSaveableMutableStateListOf(
            "Male", "Female", "Genderless", "Unknown"
        )

        FilterView(
            name = "Characters filter",
            filterGroupList = listOf(
                FilterGroup(
                    name = "Species",
                    selected = selectedSpecies,
                    labelList = speciesLabelList,
                    isListFinished = false,
                    resolveIcon = { text -> RnmIcons.Alien },
                    onAddLabel = { text -> speciesLabelList.add(text) },
                    onSelectedChanged = {},
                ),
                FilterGroup(
                    name = "Gender",
                    selected = selectedGender,
                    labelList = genderLabelList,
                    isListFinished = true,
                    resolveIcon = { text -> RnmIcons.GenderIntersex },
                    onAddLabel = { text -> genderLabelList.add(text) },
                    onSelectedChanged = {},
                )
            )
        )
    }
}