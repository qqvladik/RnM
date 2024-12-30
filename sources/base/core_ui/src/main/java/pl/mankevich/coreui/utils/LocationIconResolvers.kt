package pl.mankevich.coreui.utils

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import pl.mankevich.designsystem.icons.RnmIcons

val locationTypeIconResolver: @Composable (String) -> ImageVector = { type ->
    if (type == "Planet") RnmIcons.Planet else RnmIcons.MapPin
}

val locationDimensionIconResolver: @Composable (String) -> ImageVector = { RnmIcons.CubeFocus }
