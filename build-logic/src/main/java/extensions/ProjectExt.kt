package extensions

import com.android.build.gradle.BaseExtension
import org.gradle.accessors.dm.LibrariesForLibs
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.the

internal fun Project.android(configuration: BaseExtension.() -> Unit) = configure(configuration)

internal val Project.libs
    get(): LibrariesForLibs = the()

fun Project.applyChucker() {
    dependencies {
        add("releaseImplementation", libs.chucker.library.release)
        add("debugImplementation", libs.chucker.library.debug)
    }
}

fun Project.applyCoil() {
    dependencies {
        add("implementation", libs.coil.kt.compose)
        add("implementation", libs.coil.kt.network.okhttp)
    }
}
