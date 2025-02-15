plugins {
    id("scripts.android-module-convention")
    alias(libs.plugins.kotlin.serialization)
}

dependencies {
    api(libs.androidx.navigation.compose)
    implementation(libs.kotlinx.serialization.json)

    implementation(project(":core"))
    implementation(project(":core_ui"))
}