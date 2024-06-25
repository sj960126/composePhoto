plugins {
    id ("com.android.library")
    id ("org.jetbrains.kotlin.android")
    id("kotlin-kapt")
}

android {
    namespace = ConfigData.nameSpace
    compileSdk = ConfigData.compileSdkVersion

    defaultConfig {
        minSdk = ConfigData.minSdkVersion
        targetSdk = ConfigData.targetSdkVersion
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
}

dependencies {
    implementation(project(":data-core"))
    implementation(project(":domain"))

    // Hilt
    implementationHilt()
    // Retrofit, OkHttp, Gson
    implementationRetrofitOkHttp()

}