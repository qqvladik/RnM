import extensions.applyDagger
import extensions.applyRoom

plugins {
    id("scripts.android-module-convention")
}

dependencies {
    applyDagger()
    applyRoom()

    implementation(libs.androidx.core.ktx)

    api(project(":storage_api"))
    implementation(project(":core"))
}