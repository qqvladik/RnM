plugins {
    id("scripts.android-module-convention")
}

dependencies {
    implementation(libs.androidx.navigation.compose)

    implementation(project(":core")) //TODO remove when add configs
    implementation(project(":core_ui"))
}