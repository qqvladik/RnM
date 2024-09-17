plugins {
    id("scripts.android-module-convention")
    id("scripts.dagger-convention")
    alias(libs.plugins.compose)
    alias(libs.plugins.atomicfu)
}

dependencies {
    implementation(libs.androidx.lifecycle.viewModelCompose)
    implementation(libs.androidx.lifecycle.runtimeCompose)
    implementation(libs.androidx.navigation.compose)
    implementation(libs.androidx.paging.runtime)
    implementation(libs.atomicfu)
}