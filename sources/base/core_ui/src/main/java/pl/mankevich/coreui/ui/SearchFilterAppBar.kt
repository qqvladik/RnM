package pl.mankevich.coreui.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import pl.mankevich.coreui.ui.filter.FilterGroup
import pl.mankevich.coreui.ui.filter.FilterView
import pl.mankevich.coreui.utils.characterSpeciesIconResolver
import pl.mankevich.coreui.utils.characterStatusIconResolver
import pl.mankevich.designsystem.component.IconButton
import pl.mankevich.designsystem.component.SearchField
import pl.mankevich.designsystem.icons.RnmIcons
import pl.mankevich.designsystem.theme.PADDING
import pl.mankevich.designsystem.theme.RnmTheme
import pl.mankevich.designsystem.theme.ThemePreviews

@Composable
fun SearchFilterAppBar(
    searchValue: String,
    searchPlaceholder: String = "Search...",
    filterName: String,
    filterGroupList: List<FilterGroup>,
    onSearchChange: (String) -> Unit,
    onSearchClear: () -> Unit,
    onBackPress: (() -> Unit)? = null,
    padding: Dp = PADDING,
    modifier: Modifier = Modifier,
) {
    Column(modifier = modifier) {
        Spacer(modifier = Modifier.height(padding))

        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth(),
        ) {
            if (onBackPress != null) {
                IconButton(
                    onClick = onBackPress,
                    imageVector = RnmIcons.CaretLeft,
                    contentDescription = "Show filters",
                    iconSize = 20.dp,
                    modifier = Modifier
                        .fillMaxHeight()
                        .aspectRatio(1.2f)
                )
            } else {
                Spacer(modifier = Modifier.width(padding))
            }

            SearchField(
                value = searchValue,
                onValueChange = onSearchChange,
                onClearClick = onSearchClear,
                placeholder = searchPlaceholder,
                modifier = Modifier.weight(1f),
            )

            Spacer(modifier = Modifier.width(padding))
        }

        Spacer(modifier = Modifier.height(padding))

        FilterView(
            name = filterName,
            filterGroupList = filterGroupList,
            scrollablePadding = padding,
            modifier = Modifier
                .height(32.dp)
                .padding(end = padding)
        )

        Spacer(modifier = Modifier.height(padding))
    }
}

@ThemePreviews
@Composable
fun SearchFilterAppBarPreview() {
    RnmTheme {
        SearchFilterAppBar(
            searchValue = "",
            filterName = "Filters",
            filterGroupList = listOf(
                FilterGroup(
                    name = "Status",
                    labelList = listOf("Alive", "Dead", "Unknown"),
                    selected = "Alive",
                    isListFinished = true,
                    resolveIcon = characterStatusIconResolver,
                    onSelectedChanged = {},
                ),
                FilterGroup(
                    name = "Species",
                    labelList = listOf(
                        "Human",
                        "Alien",
                        "Humanoid",
                        "Poopybutthole",
                        "Mythological Creature",
                        "Animal",
                        "Disease",
                        "Robot",
                        "Cronenberg",
                        "Planet"
                    ),
                    isListFinished = false,
                    resolveIcon = characterSpeciesIconResolver,
                    onSelectedChanged = {},
                )
            ),
            onSearchChange = {},
            onSearchClear = {},
            modifier = Modifier
                .fillMaxWidth()
                .height(108.dp),
        )
    }
}