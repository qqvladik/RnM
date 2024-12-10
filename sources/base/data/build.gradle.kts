plugins {
    id("scripts.android-module-convention")
    id("scripts.dagger-convention")
}
dependencies {
    implementation(libs.androidx.paging.runtime)

    api(project(":data_api"))
    implementation(project(":core"))
    implementation(project(":database_api"))
    implementation(project(":remote_api"))
}