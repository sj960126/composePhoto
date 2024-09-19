plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    id("kotlin-kapt")
}

android {
    namespace = "com.photo.main"
    compileSdk = ConfigData.compileSdkVersion

    defaultConfig {
        minSdk = ConfigData.minSdkVersion
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.2.0"
    }
    buildFeatures {
        compose = true
    }
}

dependencies {
    implementation(project(":core_presentation"))
    implementation(project(":feature:search"))
    implementation(project(":feature:detail"))
    implementation(project(":feature:bookmark"))
    implementation(project(":data"))
    implementation(project(":domain"))
    implementationKotlin()
    implementationAndroidXCore()
    implementationCompose()
    implementaionCoroutines()
    implementationHilt()
    implementationGson()
}