plugins {
    id("scripts.android-module-convention")
    id("scripts.compose-ui-convention")
    id("scripts.dagger-convention")
}

dependencies {
    api(project(":characters_list_api"))
    implementation(project(":core"))
    implementation(project(":dependencies"))
}