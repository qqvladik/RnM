package pl.mankevich.coreui.utils

import androidx.compose.runtime.Composable
import pl.mankevich.designsystem.icons.RnmIcons

@Composable
fun characterStatusIconResolver(status: String) =
    if (status == "Dead") RnmIcons.Skull else RnmIcons.Pulse

@Composable
fun characterGenderIconResolver(gender: String) =
    when (gender) {
        "Male" -> RnmIcons.GenderMale
        "Female" -> RnmIcons.GenderFemale
        "Genderless" -> RnmIcons.Genderless
        else -> RnmIcons.GenderIntersex
    }

@Composable
fun characterSpeciesIconResolver(species: String) =
    when (species) {
        "Human" -> RnmIcons.Person
        "Robot" -> RnmIcons.Robot
        else -> RnmIcons.Alien
    }