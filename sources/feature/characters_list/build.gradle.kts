import extensions.applyDagger

plugins {
    id("scripts.android-module-convention")
    id("scripts.compose-ui-convention")
}

dependencies {
    applyDagger()

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtimeCompose)
    implementation(libs.retrofit.core)

    api(project(":characters_list_api"))
    implementation(project(":core"))
    implementation(project(":dependencies"))
}