import extensions.applyDagger

plugins {
    id("scripts.application-convention")
    id("scripts.compose-ui-convention")
}

dependencies {
    applyDagger()

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtimeCompose)
    implementation(libs.retrofit.core)
}