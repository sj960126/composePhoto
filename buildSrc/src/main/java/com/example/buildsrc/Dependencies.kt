
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
    }

    object Android {
        val extensions = "android.arch.lifecycle:extensions:1.1.1"
    }

    object Google {
        val gson = "com.google.code.gson:gson:2.8.9"

        object Hilt {
            val android = "com.google.dagger:hilt-android:${Versions.hiltVersion}"
            val compiler = "com.google.dagger:hilt-compiler:${Versions.hiltVersion}"
            val androidCompiler = "com.google.dagger:hilt-android-compiler:${Versions.hiltVersion}"
            val androidTesting = "com.google.dagger:hilt-android-testing:${Versions.hiltVersion}"
        }

    }
    object Compose {
        val composeRuntime ="androidx.compose.runtime:runtime"
        val composeUi = "androidx.compose.ui:ui"
        val composeFoundation = "androidx.compose.foundation:foundation"
        val composeFoundationLayout = "androidx.compose.foundation:foundation-layout"
        val composeMaterial = "androidx.compose.material:material"
        val composeLiveData = "androidx.compose.runtime:runtime-livedata"
        val composeUiTooling = "androidx.compose.ui:ui-tooling"
        val composeActivity = "androidx.activity:activity-compose:1.4.0"
        val composeCoil = "io.coil-kt:coil-compose:2.2.0"
        val material3 = "androidx.compose.material3:material3:1.1.2"
        val navigation  ="androidx.navigation:navigation-compose:2.5.3"
        val hilt =  "androidx.hilt:hilt-navigation-compose:1.0.0"
        val pager = "com.google.accompanist:accompanist-pager:0.24.3-alpha"
        val pagerIndicators ="com.google.accompanist:accompanist-pager-indicators:0.24.3-alpha"
    }

    object SquareUp {
        val retrofit = "com.squareup.retrofit2:retrofit:2.9.0"
        val okhttp = "com.squareup.okhttp3:okhttp:4.8.1"
        val okhttpLogging =  "com.squareup.okhttp3:logging-interceptor:4.9.3"
        val converterGson = "com.squareup.retrofit2:converter-gson:2.9.0"
    }

    object ThirdParty {

        object Github {
            val glide = "com.github.bumptech.glide:glide:${Versions.glide}"
            val okhttp3 = "com.github.bumptech.glide:okhttp3-integration:${Versions.glide}"
            val glideCompiler = "com.github.bumptech.glide:compiler:${Versions.glide}"
        }
    }

}