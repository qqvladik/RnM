plugins {
    id("scripts.android-module-convention")
    id("scripts.compose-ui-convention")
    id("scripts.dagger-convention")
}

dependencies {
    implementation(libs.androidx.lifecycle.runtimeCompose)
    implementation(libs.androidx.paging.runtime)
    implementation(libs.androidx.paging.compose)

    api(project(":episodes_list_api"))
    implementation(project(":episode_detail_api"))
    implementation(project(":dependencies"))
}