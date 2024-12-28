plugins {
    id("scripts.android-module-convention")
}

dependencies {
    api(libs.androidx.navigation.compose)

    implementation(project(":core"))
    implementation(project(":core_ui"))
}