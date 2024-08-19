plugins {
    id("scripts.android-module-convention")
    id("scripts.compose-ui-convention")
    id("scripts.dagger-convention")
}

dependencies {
    implementation(libs.androidx.paging.runtime)
    implementation(libs.androidx.paging.compose)

    api(project(":character_detail_api"))
    implementation(project(":core"))
    implementation(project(":dependencies"))
}