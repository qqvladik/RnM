import extensions.applyChucker
import extensions.applyDagger

plugins {
    id("scripts.android-module-convention")
}

dependencies {
    applyDagger()
    applyChucker()

    implementation(libs.androidx.core.ktx)
    implementation(libs.retrofit.core)
    implementation(libs.retrofit.converter.gson)
    implementation(libs.retrofit.converter.scalars)
    implementation(libs.okhttp.logging.interceptor)

    api(project(":network_api"))
    implementation(project(":core"))
}