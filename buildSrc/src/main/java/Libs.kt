import org.gradle.kotlin.dsl.DependencyHandlerScope

object Libs {

    object Kotlin {
        const val coreKtx = "androidx.core:core-ktx:${Versions.coreKtx}"
        const val coroutinesCore = "org.jetbrains.kotlinx:kotlinx-coroutines-core:${Versions.coroutines}"
        const val coroutinesAndroid = "org.jetbrains.kotlinx:kotlinx-coroutines-android:${Versions.coroutines}"
    }

    object Lifecycle {
        const val lifecycleViewModel = "androidx.lifecycle:lifecycle-viewmodel-ktx:${Versions.lifecycle}"
        const val lifecycleRuntime = "androidx.lifecycle:lifecycle-runtime-ktx:${Versions.lifecycle}"
        const val lifecycleViewModelCompose = "androidx.lifecycle:lifecycle-viewmodel-compose:${Versions.lifecycle}"
        const val lifecycleRuntimeCompose = "androidx.lifecycle:lifecycle-runtime-compose:${Versions.lifecycle}"
    }

    object Dagger { //Don't forget about using kotlin-kapt
        const val dagger = "com.google.dagger:dagger:${Versions.dagger}"
        const val compiler = "com.google.dagger:dagger-compiler:${Versions.dagger}"
    }

    object Room { //Don't forget about using kotlin-kapt
        const val ktx = "androidx.room:room-ktx:${Versions.room}"
        const val runtime = "androidx.room:room-runtime:${Versions.room}"
        const val compiler = "androidx.room:room-compiler:${Versions.room}"
    }

    object Network { //Don’t forget to add INTERNET permissions
        const val retrofit = "com.squareup.retrofit2:retrofit:${Versions.retrofit}"
        const val converterGson = "com.squareup.retrofit2:converter-gson:${Versions.retrofit}"
        const val converterScalars = "com.squareup.retrofit2:converter-scalars:${Versions.retrofit}"
        const val loggingInterceptor = "com.squareup.okhttp3:logging-interceptor:${Versions.okHttp}"

        const val chuckerDebug = "com.github.chuckerteam.chucker:library:${Versions.chucker}"
        const val chuckerRelease = "com.github.chuckerteam.chucker:library-no-op:${Versions.chucker}"
    }

    object Compose {
        const val ui = "androidx.compose.ui:ui:${Versions.compose}"
        const val toolingPreview = "androidx.compose.ui:ui-tooling-preview:${Versions.compose}"
        const val material = "androidx.compose.material:material:${Versions.compose}"
        const val material3 = "androidx.compose.material3:material3:${Versions.materialCompose3}"

        const val navigation = "androidx.navigation:navigation-compose:${Versions.navigationCompose}"
        const val activity = "androidx.activity:activity-compose:${Versions.activityCompose}"
    }

    object DebugCompose {
        const val uiTooling = "androidx.compose.ui:ui-tooling:${Versions.compose}"
        const val uiTestManifest = "androidx.compose.ui:ui-test-manifest:${Versions.compose}"
    }

    object Paging {
        const val pagingRuntime = "androidx.paging:paging-runtime:${Versions.paging}"
        const val paging = "androidx.paging:paging-compose:${Versions.paging}"
    }
}

fun DependencyHandlerScope.applyDagger() {
    dependencies.add("implementation", Libs.Dagger.dagger)
    dependencies.add("kapt", Libs.Dagger.compiler)
}

fun DependencyHandlerScope.applyRoom() {
    dependencies.add("implementation", Libs.Room.ktx)
    dependencies.add("implementation", Libs.Room.runtime)
    dependencies.add("kapt", Libs.Room.compiler)
}

fun DependencyHandlerScope.applyChucker() {
    dependencies.add("releaseImplementation", Libs.Network.chuckerRelease)
    dependencies.add("debugImplementation", Libs.Network.chuckerDebug)
}

fun DependencyHandlerScope.applyBaseCompose() {
    dependencies.add("implementation", Libs.Compose.ui)
    dependencies.add("debugImplementation", Libs.DebugCompose.uiTooling)
    dependencies.add("implementation", Libs.Compose.toolingPreview)
    dependencies.add("implementation", Libs.Compose.material3)
}