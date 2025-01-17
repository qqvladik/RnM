plugins {
    id("scripts.android-module-convention")
    id("scripts.compose-ui-convention")
}

dependencies {
    implementation(libs.androidx.compose.material3.adaptive)
    implementation(libs.androidx.compose.material3.adaptive.navigationSuite)
}