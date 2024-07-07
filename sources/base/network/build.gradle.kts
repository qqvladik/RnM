import extensions.applyChucker

plugins {
    id("scripts.android-module-convention")
    id("scripts.dagger-convention")
}

dependencies {
    applyChucker()

    implementation(libs.retrofit.core)
    implementation(libs.retrofit.converter.gson)
    implementation(libs.retrofit.converter.scalars)
    implementation(libs.okhttp.logging.interceptor)

    api(project(":network_api"))
    implementation(project(":core"))
}