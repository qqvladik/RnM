package scripts

import com.android.build.api.dsl.ApplicationExtension
import com.android.build.gradle.BaseExtension
import com.android.build.gradle.LibraryExtension
import extensions.addComposeConfig
import extensions.applyBaseCompose

//TODO check
// https://github.com/gradle/gradle/issues/15383
// https://github.com/android/nowinandroid/pull/1504
// https://github.com/android/nowinandroid/pull/1504/commits/f8197db250dbfd4c091043e5f19f88834d45847d#diff-f727ec5737ffef096c85b8f4245a5957130b97106b7f57d636ed4a1ba06e6613
//internal fun PluginManager.apply(pluginProvider: Provider<PluginDependency>) {
//    apply(pluginProvider.get().pluginId)
//}
//pluginManager.apply(libs.findPlugin("kotlin-compose").get())

plugins {
    id("org.jetbrains.kotlin.plugin.compose")
}

fun android(configuration: BaseExtension.() -> Unit) = configure(configuration)

android {
    val extension = try {
        extensions.getByType<LibraryExtension>()
    } catch (e: UnknownDomainObjectException) {
        extensions.getByType<ApplicationExtension>()
    }
    extension.addComposeConfig()

    applyBaseCompose()
}