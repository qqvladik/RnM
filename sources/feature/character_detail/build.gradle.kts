import extensions.applyCoil

plugins {
    id("scripts.android-module-convention")
    id("scripts.compose-ui-convention")
    id("scripts.dagger-convention")
}

dependencies {
    applyCoil()

    implementation(libs.androidx.lifecycle.runtimeCompose)
    implementation(libs.androidx.navigation.compose)

    api(project(":character_detail_api"))
    api(project(":characters_list_api"))
    implementation(project(":core"))
    implementation(project(":core_ui"))
    implementation(project(":dependencies"))
    implementation(project(":design_system"))
}