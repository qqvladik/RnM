package extensions

import org.gradle.api.Project
import org.gradle.api.artifacts.VersionCatalog
import org.gradle.api.artifacts.VersionCatalogsExtension
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.getByType

internal val Project.libs
    get(): VersionCatalog = extensions.getByType<VersionCatalogsExtension>().named("libs")

fun Project.applyDagger() {
    dependencies {
        add("implementation", libs.findLibrary("dagger-core").get())
        add("kapt", libs.findLibrary("dagger-compiler").get())
    }
}

fun Project.applyRoom() {
    dependencies {
        add("implementation", libs.findLibrary("androidx-room-ktx").get())
        add("implementation", libs.findLibrary("androidx-room-runtime").get())
        add("kapt", libs.findLibrary("androidx-room-compiler").get())
    }
}

fun Project.applyChucker() {
    dependencies {
        add("releaseImplementation", libs.findLibrary("chucker-library-release").get())
        add("debugImplementation", libs.findLibrary("chucker-library-debug").get())
    }
}

fun Project.applyBaseCompose() {
    dependencies {
        add("implementation", platform(libs.findLibrary("androidx-compose-bom").get()))
        add("implementation", libs.findLibrary("androidx-compose-ui").get())
        add("debugImplementation", libs.findLibrary("androidx-compose-ui-tooling").get())
        add("implementation", libs.findLibrary("androidx-compose-ui-tooling-preview").get())
        add("implementation", libs.findLibrary("androidx-compose-material3").get())
        add("implementation", libs.findLibrary("androidx-activity-compose").get())
        add("implementation", libs.findLibrary("androidx-navigation-compose").get())
    }
}