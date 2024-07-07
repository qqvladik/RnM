plugins {
    id("scripts.android-module-convention")
    id("scripts.room-convention")
    id("scripts.dagger-convention")
}

dependencies {
    api(project(":storage_api"))
    implementation(project(":core"))
}