plugins {
    id("scripts.application-convention")
    id("scripts.compose-ui-convention")
    id("scripts.dagger-convention")
    alias(libs.plugins.kotlin.serialization)
}

kotlin {
    compilerOptions.freeCompilerArgs = listOf(
        "-opt-in=androidx.compose.animation.ExperimentalSharedTransitionApi",
    )
}

dependencies {
    implementation(libs.androidx.core.splashscreen)

    implementation(libs.androidx.activity.compose)
    implementation(libs.androidx.navigation.compose)
    implementation(libs.kotlinx.serialization.json)
}