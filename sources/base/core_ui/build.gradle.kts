import extensions.applyCoil

plugins {
    id("scripts.android-module-convention")
    id("scripts.compose-ui-convention")
}

dependencies {
    applyCoil()
    implementation(libs.androidx.navigation.compose)

    implementation(project(":core"))
    implementation(project(":design_system"))
}