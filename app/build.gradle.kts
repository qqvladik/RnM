plugins {
    id("scripts.application-convention")
    id("scripts.compose-ui-convention")
    id("scripts.dagger-convention")
    alias(libs.plugins.kotlin.serialization)
}

dependencies {
    implementation(libs.androidx.activity.compose)
    implementation(libs.androidx.navigation.compose)
    implementation(libs.kotlinx.serialization.json)
}