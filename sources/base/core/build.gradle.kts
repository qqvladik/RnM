import extensions.applyDagger

plugins {
    id("scripts.android-module-convention")
    alias(libs.plugins.compose)
}

dependencies {
    applyDagger()

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.viewModelCompose)
    implementation(libs.androidx.navigation.compose)
}