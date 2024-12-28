plugins {
    id("scripts.android-module-convention")
}

dependencies {
    implementation(libs.androidx.compose.runtime)

    api(project(":core"))
    api(project(":core_ui"))
    api(project(":domain_api"))
}