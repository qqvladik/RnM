package pl.mankevich.coreui.utils

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import pl.mankevich.designsystem.icons.RnmIcons

val characterStatusIconResolver: @Composable (String) -> ImageVector = { status ->
    if (status == "Dead") RnmIcons.Skull else RnmIcons.Pulse
}

val characterSpeciesIconResolver: @Composable (String) -> ImageVector = { species ->
    when (species) {
        "Human" -> RnmIcons.Person
        "Robot" -> RnmIcons.Robot
        else -> RnmIcons.Alien
    }
}

val characterGenderIconResolver: @Composable (String) -> ImageVector = { gender ->
    when (gender) {
        "Male" -> RnmIcons.GenderMale
        "Female" -> RnmIcons.GenderFemale
        "Genderless" -> RnmIcons.Genderless
        else -> RnmIcons.GenderIntersex
    }
}

val characterTypeIconResolver: @Composable (String) -> ImageVector = { RnmIcons.Blocks }
