package pl.mankevich.rnm

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
        const val lifecycleRuntimeCompose =
            "androidx.lifecycle:lifecycle-runtime-compose:${Versions.lifecycleRuntimeCompose}"
    }

    object DI {
        const val hiltAndroid = "com.google.dagger:hilt-android:${Versions.hilt}"
        const val hiltCompiler = "com.google.dagger:hilt-compiler:${Versions.hilt}"
        const val czerwinskiHiltExt = "it.czerwinski.android.hilt:hilt-extensions:${Versions.czerwinskiHilt}"
        const val czerwinskiHiltProcessor = "it.czerwinski.android.hilt:hilt-processor:${Versions.czerwinskiHilt}"
    }

    object Network {
        const val apolloKotlin = "com.apollographql.apollo3:apollo-runtime:${Versions.apolloKotlin}"
        const val chuckerDebug = "com.github.chuckerteam.chucker:library:3.5.2"
        const val chuckerRelease = "com.github.chuckerteam.chucker:library-no-op:3.5.2"
    }

    object Compose {
        const val ui = "androidx.compose.ui:ui:${Versions.compose}"
        const val preview = "androidx.compose.ui:ui-tooling-preview:${Versions.compose}"
        const val material = "androidx.compose.material:material:${Versions.materialCompose}"
        const val material3 = "androidx.compose.material3:material3:${Versions.materialCompose3}"

        const val navigation = "androidx.navigation:navigation-compose:${Versions.navigationCompose}"
        const val hiltNavigation = "androidx.hilt:hilt-navigation-compose:${Versions.hiltNavigationCompose}"
        const val activity = "androidx.activity:activity-compose:${Versions.activityCompose}"
        const val paging = "androidx.paging:paging-compose:${Versions.pagingCompose}"
    }

    object Paging {
        const val pagingRuntime = "androidx.paging:paging-runtime:${Versions.paging}"
    }

    object Testing {
        const val junit = "junit:junit:${Versions.junit}"
    }

    object AndroidTesting {
        const val extJunit = "androidx.test.ext:junit:${Versions.testExtJunit}}"
        const val espressoCore = "androidx.test.espresso:espresso-core:${Versions.espresso}"
        const val composeTestJunit = "androidx.compose.ui:ui-test-junit4:${Versions.compose}"
    }

    object DebugCompose {
        const val uiTooling = "androidx.compose.ui:ui-tooling:${Versions.compose}"
        const val uiTestManifest = "androidx.compose.ui:ui-test-manifest:${Versions.compose}"
    }
}