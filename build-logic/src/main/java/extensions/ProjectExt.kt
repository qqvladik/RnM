package extensions

import com.android.build.gradle.BaseExtension
import org.gradle.accessors.dm.LibrariesForLibs
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.getByType

internal fun Project.android(configuration: BaseExtension.() -> Unit) = configure(configuration)

internal val Project.libs
    get(): LibrariesForLibs = extensions.getByType()

fun Project.applyChucker() {
    dependencies {
        add("releaseImplementation", libs.chucker.library.release)
        add("debugImplementation", libs.chucker.library.debug)
    }
}
