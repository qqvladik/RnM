import extensions.applyCoil

plugins {
    id("scripts.android-module-convention")
    id("scripts.compose-ui-convention")
    id("scripts.dagger-convention")
}

dependencies {
    applyCoil()
    implementation(libs.androidx.lifecycle.runtimeCompose)

    api(project(":episode_detail_api"))
    implementation(project(":episodes_list_api"))
    implementation(project(":character_detail_api"))
    implementation(project(":dependencies"))
}