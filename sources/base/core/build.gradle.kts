plugins {
    id("scripts.android-module-convention")
    id("scripts.dagger-convention")
    alias(libs.plugins.compose)
}

dependencies {
    implementation(libs.androidx.lifecycle.viewModelCompose)
    implementation(libs.androidx.navigation.compose)
    implementation(libs.androidx.paging.runtime)
}