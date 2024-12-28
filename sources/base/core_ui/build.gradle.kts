import extensions.applyCoil

plugins {
    id("scripts.android-module-convention")
    id("scripts.compose-ui-convention")
    id("scripts.dagger-convention")
    alias(libs.plugins.atomicfu)
}

dependencies {
    applyCoil()
    implementation(libs.androidx.lifecycle.viewModelCompose)
    implementation(libs.androidx.lifecycle.runtimeCompose)
    implementation(libs.androidx.navigation.compose)
    implementation(libs.androidx.paging.runtime)
    implementation(libs.atomicfu)

    api(project(":design_system"))
}