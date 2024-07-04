package scripts

import com.android.build.api.dsl.ApplicationExtension
import com.android.build.gradle.BaseExtension
import com.android.build.gradle.LibraryExtension
import extensions.addComposeConfig
import extensions.applyBaseCompose

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