package extensions

import org.gradle.accessors.dm.LibrariesForLibs
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.getByType

internal val Project.libs
    get(): LibrariesForLibs = extensions.getByType()

fun Project.applyDagger() {
    dependencies {
        add("implementation", libs.dagger.core)
        add("kapt", libs.dagger.compiler)
    }
}

fun Project.applyRoom() {
    dependencies {
        add("implementation", libs.androidx.room.ktx)
        add("implementation", libs.androidx.room.runtime)
        add("kapt", libs.androidx.room.compiler)
    }
}

fun Project.applyChucker() {
    dependencies {
        add("releaseImplementation", libs.chucker.library.release)
        add("debugImplementation", libs.chucker.library.debug)
    }
}

fun Project.applyBaseCompose() {
    dependencies {
        add("implementation", platform(libs.androidx.compose.bom))
        add("implementation", libs.androidx.compose.ui)
        add("debugImplementation", libs.androidx.compose.ui.tooling)
        add("implementation", libs.androidx.compose.ui.tooling.preview)
        add("implementation", libs.androidx.compose.material3)
        add("implementation", libs.androidx.activity.compose)
        add("implementation", libs.androidx.navigation.compose)
    }
}