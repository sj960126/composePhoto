import org.gradle.api.artifacts.dsl.DependencyHandler

// 기본 AndroidX Core
fun DependencyHandler.implementationAndroidXCore() {
    add("implementation", Dependencies.AndroidX.coreKtx)
    add("implementation", Dependencies.AndroidX.appcompat)
    add("implementation", Dependencies.AndroidX.annotation)
}


// Retrofit, OkHttp, Gson
fun DependencyHandler.implementationRetrofitOkHttp() {
    add("implementation", Dependencies.SquareUp.retrofit)
    add("implementation", Dependencies.SquareUp.okhttp)
    add("implementation", Dependencies.SquareUp.okhttpLogging)
    add("implementation", Dependencies.SquareUp.converterGson)
    add("implementation", Dependencies.Google.gson)
}

// glide
fun DependencyHandler.implementationGlide() {
    add("implementation", Dependencies.ThirdParty.Github.glide)
    add("implementation", Dependencies.ThirdParty.Github.okhttp3)
    add("kapt", Dependencies.ThirdParty.Github.glideCompiler)
}

// Hilt
fun DependencyHandler.implementationHilt() {
    add("implementation", Dependencies.Google.Hilt.android)
    add("kapt", Dependencies.Google.Hilt.compiler)
    add("kapt", Dependencies.Google.Hilt.androidCompiler)
    add("testImplementation", Dependencies.Google.Hilt.androidTesting)
    add("kaptTest", Dependencies.Google.Hilt.androidCompiler)
}


// Coroutines
fun DependencyHandler.implementaionCoroutines() {
    add("implementation", Dependencies.coroutines)
}
fun DependencyHandler.implementationCompose() {
    add("implementation", Dependencies.Compose.composeUi)
    add("implementation", Dependencies.Compose.composeRuntime)
    add("implementation", Dependencies.Compose.composeFoundation)
    add("implementation", Dependencies.Compose.composeFoundationLayout)
    add("implementation", Dependencies.Compose.composeMaterial)
    add("implementation", Dependencies.Compose.composeLiveData)
    add("implementation", Dependencies.Compose.composeUiTooling)
    add("implementation", Dependencies.Compose.composeActivity)
    add("implementation", Dependencies.Compose.composeCoil)
    add("implementation", Dependencies.Compose.material3)
    add("implementation", Dependencies.Compose.navigation)
    add("implementation", Dependencies.Compose.hilt)
    add("implementation", Dependencies.Compose.pager)
    add("implementation", Dependencies.Compose.pagerIndicators)
}
