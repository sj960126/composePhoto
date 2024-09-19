import org.gradle.api.artifacts.dsl.DependencyHandler

fun DependencyHandler.implementationAndroidXCore() {
    add("implementation", Dependencies.AndroidX.coreKtx)
    add("implementation", Dependencies.AndroidX.appcompat)
    add("implementation", Dependencies.AndroidX.annotation)
    add("implementation", Dependencies.AndroidX.window)
}
fun DependencyHandler.implementationKotlin() {
    add("implementation", Dependencies.Kotlin.jdk7)
    add("implementation", Dependencies.Kotlin.kotlinBom)
    add("implementation", Dependencies.Kotlin.imutable)
}
fun DependencyHandler.implementationRetrofitOkHttp() {
    add("implementation", Dependencies.SquareUp.retrofit)
    add("implementation", Dependencies.SquareUp.okhttp)
    add("implementation", Dependencies.SquareUp.okhttpLogging)
    add("implementation", Dependencies.SquareUp.converterGson)
    add("implementation", Dependencies.Gson.gson)
}

fun DependencyHandler.implementationHilt() {
    add("implementation", Dependencies.Hilt.android)
    add("kapt", Dependencies.Hilt.compiler)
    add("kapt", Dependencies.Hilt.androidCompiler)
    add("testImplementation", Dependencies.Hilt.androidTesting)
    add("kaptTest", Dependencies.Hilt.androidCompiler)
}
fun DependencyHandler.implementaionCoroutines() {
    add("implementation", Dependencies.Kotlin.coroutines)
}
fun DependencyHandler.implementaionPaging() {
    add("implementation", Dependencies.Paging.pagingRuntime)
    add("implementation", Dependencies.Paging.pagingCompose)
}
fun DependencyHandler.implementationRoom() {
    add("implementation", Dependencies.Room.runtime)
    add("implementation", Dependencies.Room.roomKtx)
    add("kapt", Dependencies.Room.compiler)
}
fun DependencyHandler.implementationCompose() {
    add("implementation", Dependencies.Compose.composeUi)
    add("implementation", Dependencies.Compose.composeRuntime)
    add("implementation", Dependencies.Compose.composeFoundation)
    add("implementation", Dependencies.Compose.composeFoundationLayout)
    add("implementation", Dependencies.Compose.composeMaterial)
    add("implementation", Dependencies.Compose.composeUiTooling)
    add("implementation", Dependencies.Compose.composeActivity)
    add("implementation", Dependencies.Compose.composeCoil)
    add("implementation", Dependencies.Compose.navigation)
    add("implementation", Dependencies.Compose.hilt)
}
fun DependencyHandler.implementationGson() {
    add("implementation", Dependencies.Gson.gson)
}
