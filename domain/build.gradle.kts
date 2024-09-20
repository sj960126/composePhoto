plugins {
    id ("com.android.library")
    id ("org.jetbrains.kotlin.android")
    id("kotlin-kapt")
}

android {
    namespace = "com.photo.domain"
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
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }
}

dependencies {
    implementationRetrofitOkHttp()
    implementaionPaging()
    implementaionCoroutines()
    implementationKotlin()
    implementationHilt()
}