plugins {
    id("scripts.android-module-convention")
    id("scripts.room-convention")
    id("scripts.dagger-convention")
}

dependencies {
    implementation(libs.androidx.paging.runtime)

    api(project(":database_api"))
    implementation(project(":core"))
}