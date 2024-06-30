plugins {
    scripts.`android-module-convention`
}

dependencies {
    applyDagger()
    applyRoom()

    implementation(Libs.Kotlin.coreKtx)

    api(project(":storage_api"))
    implementation(project(":core"))
}