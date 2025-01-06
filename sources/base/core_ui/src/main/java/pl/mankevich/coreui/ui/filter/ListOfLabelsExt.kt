package pl.mankevich.coreui.ui.filter

fun List<String>.addLabelIfUnique(label: String): List<String> {
    if (label.isBlank()) return this
    if (this.any { it.equals(label, ignoreCase = true) }) return this
    return this.plus(label)
}