plugins {
    id("scripts.android-module-convention")
}

dependencies {
    implementation(libs.androidx.navigation.compose)

    implementation(project(":core"))
    implementation(project(":network_api"))
    implementation(project(":storage_api"))
}