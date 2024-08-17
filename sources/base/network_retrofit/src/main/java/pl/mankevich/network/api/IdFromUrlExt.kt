package pl.mankevich.network.api

fun String?.obtainId(): Int? {
    if (this.isNullOrBlank()) return null
    return this.substringAfterLast('/').toInt()
}