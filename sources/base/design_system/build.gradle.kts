plugins {
    id("scripts.android-module-convention")
    id("scripts.compose-ui-convention")
}

dependencies {
    implementation(libs.coil.kt.compose) //TODO make coil plugin
    implementation(libs.coil.kt.network.okhttp)
}