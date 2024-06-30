package pl.mankevich.network.datasource

fun String.obtainId(): Int = this.substringAfterLast('/').toInt()