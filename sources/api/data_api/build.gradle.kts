plugins {
    id("scripts.android-module-convention")
}
dependencies {
    implementation(libs.androidx.paging.runtime)

    api(project(":model"))
    implementation(project(":core"))
}