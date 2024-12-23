object Configs {
    private const val versionMajor = 1
    private const val versionMinor = 0
    private const val versionPatch = 0
    private const val versionQualifier = "alpha1"

    private fun generateVersionCode(): Int {
        return versionMajor * 10000 + versionMinor * 100 + versionPatch
    }

    private fun generateVersionName(): String {
        return "$versionMajor.$versionMinor.$versionPatch"
    }

    const val applicationPackage = "pl.mankevich.rnm"
    val versionCode = generateVersionCode()
    val versionName = generateVersionName()
    const val minSdk = 26
    const val targetSdk = 35
    const val compileSdk = 35
    const val androidJunitRunner = "androidx.test.runner.AndroidJUnitRunner"
    val freeCompilerArgs = listOf(
//        "-Xjvm-default=all",
//        "-Xopt-in=kotlin.RequiresOptIn",
//        "-Xopt-in=kotlin.Experimental",
//        "-Xopt-in=kotlinx.coroutines.ExperimentalCoroutinesApi",
//        "-Xopt-in=kotlinx.coroutines.InternalCoroutinesApi",
//        "-Xopt-in=kotlinx.coroutines.FlowPreview",
        "-Xopt-in=androidx.compose.material.ExperimentalMaterialApi",
//        "-Xopt-in=com.google.accompanist.navigation.material.ExperimentalMaterialNavigationApi",
//        "-Xopt-in=androidx.compose.animation.ExperimentalAnimationApi"
    )

}