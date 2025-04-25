plugins {
    id("scripts.android-module-convention")
    id("scripts.compose-ui-convention")
}

kotlin {
    compilerOptions.freeCompilerArgs = listOf(
        "-opt-in=androidx.compose.animation.ExperimentalSharedTransitionApi",
        "-opt-in=androidx.compose.material3.ExperimentalMaterial3Api",
    )
}

dependencies {
    implementation(libs.androidx.compose.material3.adaptive)
    implementation(libs.androidx.compose.material3.adaptive.navigationSuite)
}