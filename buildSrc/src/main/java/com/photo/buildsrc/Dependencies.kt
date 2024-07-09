
object Dependencies {

    val junit = "junit:junit:4.13.2"
    val kotlinxCoroutinesTest = "org.jetbrains.kotlinx:kotlinx-coroutines-test:1.6.4"
    val coroutines = "org.jetbrains.kotlinx:kotlinx-coroutines-android:1.3.9"
    val jdk7 = "org.jetbrains.kotlin:kotlin-stdlib-jdk7:${Versions.kotlinVersion}"
    val kotlinBom = "org.jetbrains.kotlin:kotlin-bom:${Versions.kotlinVersion}"

    object AndroidX {
        val core = "androidx.core:core:${Versions.coreKtx}"
        val coreKtx = "androidx.core:core-ktx:${Versions.coreKtx}"
        val appcompat = "androidx.appcompat:appcompat:${Versions.appcompat}"
        val annotation = "androidx.annotation:annotation:${Versions.annotation}"
        val coreTesting = "androidx.arch.core:core-testing:2.1.0"
        val activity = "androidx.activity:activity-ktx:1.1.0"
        val fragment = "androidx.fragment:fragment-ktx:1.1.0"
        val workRuntimeKtx = "androidx.work:work-runtime-ktx:2.7.1"
        val window = "androidx.window:window:1.0.0"
    }

    object Android {
        val extensions = "android.arch.lifecycle:extensions:1.1.1"
    }
    object Room {
        val runtime = "androidx.room:room-runtime:${Versions.room}"
        val roomKtx = "androidx.room:room-ktx:${Versions.room}"
        val compiler = "androidx.room:room-compiler:${Versions.room}"
    }
    object Paging {
        val pagingRuntime = "androidx.paging:paging-runtime:3.1.1"
        val pagingCompose= "androidx.paging:paging-compose:1.0.0-alpha14"
    }
    object Gson {
        val gson = "com.google.code.gson:gson:2.8.9"
    }

    object Hilt {
        val android = "com.google.dagger:hilt-android:${Versions.hiltVersion}"
        val compiler = "com.google.dagger:hilt-compiler:${Versions.hiltVersion}"
        val androidCompiler = "com.google.dagger:hilt-android-compiler:${Versions.hiltVersion}"
        val androidTesting = "com.google.dagger:hilt-android-testing:${Versions.hiltVersion}"
    }
    object Compose {
        val composeRuntime ="androidx.compose.runtime:runtime:${Versions.compose}"
        val composeUi = "androidx.compose.ui:ui:${Versions.compose}"
        val composeFoundation = "androidx.compose.foundation:foundation:${Versions.compose}"
        val composeFoundationLayout = "androidx.compose.foundation:foundation-layout:${Versions.compose}"
        val composeMaterial = "androidx.compose.material:material:${Versions.compose}"
        val composeUiTooling = "androidx.compose.ui:ui-tooling:${Versions.compose}"
        val composePreview = "androidx.compose.ui:ui-tooling-preview:${Versions.compose}"
        val composeActivity = "androidx.activity:activity-compose:1.4.0"
        val composeCoil = "io.coil-kt:coil-compose:2.2.0"
        val navigation  ="androidx.navigation:navigation-compose:2.5.3"
        val hilt =  "androidx.hilt:hilt-navigation-compose:1.0.0"
    }

    object SquareUp {
        val retrofit = "com.squareup.retrofit2:retrofit:2.9.0"
        val okhttp = "com.squareup.okhttp3:okhttp:4.8.1"
        val okhttpLogging =  "com.squareup.okhttp3:logging-interceptor:4.9.3"
        val converterGson = "com.squareup.retrofit2:converter-gson:2.9.0"
    }

}