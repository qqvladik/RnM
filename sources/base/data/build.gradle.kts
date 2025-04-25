plugins {
    id("scripts.android-module-convention")
    id("scripts.dagger-convention")
}

kotlin {
    compilerOptions.freeCompilerArgs = listOf(
        "-opt-in=kotlinx.coroutines.ExperimentalCoroutinesApi",
        "-opt-in=androidx.paging.ExperimentalPagingApi",
    )
}

dependencies {
    implementation(libs.androidx.paging.runtime)

    api(project(":data_api"))
    implementation(project(":core"))
    implementation(project(":database_api"))
    implementation(project(":remote_api"))
}