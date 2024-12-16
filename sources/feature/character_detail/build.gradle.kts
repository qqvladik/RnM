import extensions.applyCoil

plugins {
    id("scripts.android-module-convention")
    id("scripts.compose-ui-convention")
    id("scripts.dagger-convention")
}

dependencies {
    implementation(libs.androidx.lifecycle.runtimeCompose)
    implementation(libs.androidx.paging.runtime)
    implementation(libs.androidx.paging.compose)

    api(project(":character_detail_api"))
    api(project(":characters_list_api"))
    implementation(project(":core"))
    implementation(project(":dependencies"))
    implementation(project(":design_system"))
    applyCoil()
}