plugins {
    scripts.`android-module-convention`
}

dependencies {
    implementation(Libs.Kotlin.coreKtx)
    implementation(Libs.Compose.navigation)

    implementation(project(":core"))
    implementation(project(":network_api"))
    implementation(project(":storage_api"))
}