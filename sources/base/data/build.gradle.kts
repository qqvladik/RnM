plugins {
    id("scripts.android-module-convention")
    id("scripts.dagger-convention")
}
dependencies {
    implementation(libs.androidx.paging.runtime)

    api(project(":data_api"))
    implementation(project(":core"))
    implementation(project(":storage_api"))
    implementation(project(":network_api"))
}