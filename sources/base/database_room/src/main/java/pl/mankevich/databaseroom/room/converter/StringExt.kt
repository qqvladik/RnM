package pl.mankevich.databaseroom.room.converter

fun String.buildQueryParam(key: String): String {
    return this.ifBlank { null }?.let { "&$key=$it" }.orEmpty()
}

fun String.extractValue(key: String): String {
    return this
        .substringAfter("&$key=", "")
        .substringBefore("&")
}