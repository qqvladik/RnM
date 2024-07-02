plugins {
    id("scripts.android-module-convention")
}

dependencies {
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.navigation.compose)

    implementation(project(":core"))
}