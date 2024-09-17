plugins {
    id("scripts.android-module-convention")
}

dependencies {
    implementation(libs.androidx.navigation.compose)

    implementation(project(":core"))
    api(project(":domain_api")) //TODO think about api, maybe it is better to use implementation in every module
}