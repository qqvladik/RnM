plugins {
    id("scripts.android-module-convention")
    id("scripts.dagger-convention")
}
dependencies {
    implementation(libs.androidx.paging.runtime)

    api(project(":domain_api"))
    implementation(project(":core"))
    implementation(project(":data_api"))
}