import extensions.applyCoil

plugins {
    id("scripts.android-module-convention")
    id("scripts.compose-ui-convention")
    id("scripts.dagger-convention")
}

kotlin {
    compilerOptions.freeCompilerArgs = listOf(
        "-opt-in=androidx.compose.animation.ExperimentalSharedTransitionApi",
        "-opt-in=androidx.compose.material3.ExperimentalMaterial3Api",
    )
}

dependencies {
    applyCoil()
    implementation(libs.androidx.lifecycle.runtimeCompose)

    api(project(":character_detail_api"))
    implementation(project(":characters_list_api"))
    implementation(project(":location_detail_api"))
    implementation(project(":episode_detail_api"))
    implementation(project(":dependencies"))
}