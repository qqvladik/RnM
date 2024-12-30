package pl.mankevich.coreui.utils

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import pl.mankevich.designsystem.icons.RnmIcons

val episodeEpisodeIconResolver: @Composable (String) -> ImageVector = { RnmIcons.MonitorPlay }

val episodeSeasonIconResolver: @Composable (String) -> ImageVector = { RnmIcons.VideoLibrary }
