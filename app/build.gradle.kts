import java.util.Properties
import java.io.FileInputStream
import java.io.InputStreamReader

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.android.hilt)
    alias(libs.plugins.compose.compiler)
    id("maven-publish")
    id("kotlin-parcelize")
    alias(libs.plugins.room)
    alias(libs.plugins.ksp)
}

// Load properties
val localProperties = Properties().apply {
    load(FileInputStream(rootProject.file("local.properties")))
}

android {
    compileSdk = Sdk.compile
    namespace = "com.khs.figle_m"

    defaultConfig {
        applicationId = "com.khs.figle_m"
        minSdk = Sdk.min
        targetSdk = Sdk.target
        versionCode = 30
        versionName = "3.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        buildConfigField("String", "NEXON_API_KEY", localProperties["NEXON_API_KEY"].toString())
    }

    signingConfigs {
        create("release") {
            storeFile = file("/Users/khs/Desktop/Project/Release/FIGLE_M/FIGLE_M.jks")
            storePassword = "kimgks32!"
            keyAlias = "FIGLE_M"
            keyPassword = "kimgks32!"
        }
        getByName("debug") {
            storeFile = file("/Users/khs/Desktop/Project/Release/FIGLE_M/FIGLE_M.jks")
            storePassword = "kimgks32!"
            keyAlias = "FIGLE_M"
            keyPassword = "kimgks32!"
        }
    }

    buildTypes {
        release {
            manifestPlaceholders["enableCrashReporting"] = "true"
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
            signingConfig = signingConfigs.getByName("release")
        }
        debug {
            manifestPlaceholders["enableCrashReporting"] = "false"
            signingConfig = signingConfigs.getByName("debug")
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    kotlinOptions {
        jvmTarget = Jvm.target
    }

    buildFeatures {
        viewBinding = true
        buildConfig = true
        compose = true
    }

    room {
        schemaDirectory("$projectDir/schemas")
    }
}

dependencies {
    implementation(project(":domain"))
    implementation(project(":data"))

    // Kotlin Coroutines
    implementation(libs.kotlinx.coroutines.android)
    implementation(libs.kotlinx.coroutines.core)

    // Hilt
    implementation(libs.hilt.android)
    ksp(libs.hilt.compiler)
    implementation(libs.androidx.hilt.navigation.compose)

    // AndroidX
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.core.splashscreen)
    implementation(libs.androidx.fragment.ktx)
    implementation(libs.androidx.annotation)
    implementation(libs.androidx.appcompat)
    implementation(libs.androidx.constraintlayout)
    implementation(libs.androidx.recyclerview)

    // Compose UI
    implementation(libs.androidx.compose.ui)
    implementation(libs.androidx.compose.material3)
    implementation(libs.androidx.compose.ui.tooling)
    implementation(libs.androidx.activity.compose)
    implementation(libs.androidx.navigation.compose)
    implementation(libs.androidx.paging.compose)

    implementation(libs.androidx.constraint.compose)
    implementation(libs.androidx.constraintlayout.core)

    // Lifecycle
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.lifecycle.runtime.compose)
    implementation(libs.androidx.lifecycle.viewmodel.ktx)
    implementation(libs.androidx.lifecycle.viewmodel.compose)

    // Glide
    implementation(libs.github.glide)
    annotationProcessor(libs.compiler)

    // Retrofit2
    implementation(libs.retrofit)
    implementation(libs.converter.scalars)
    implementation(libs.converter.moshi)
    implementation(libs.converter.gson)
    implementation(libs.logging.interceptor)

    // Moshi
    implementation(libs.moshi.kotlin)

    // WilliamChart
    implementation(libs.williamchart)

    // Room
    implementation(libs.androidx.room.runtime)
    annotationProcessor(libs.androidx.room.compiler)
    ksp(libs.androidx.room.compiler)
    implementation(libs.androidx.room.ktx)

    // ViewPager Indicator
    implementation(libs.dotsindicator)

    // MPAndroidChart
    implementation(libs.mpandroidchart)

    // Jsoup
    implementation(libs.jsoup)

    implementation(libs.material)

//    // Firebase
//    implementation(libs.firebase.bom)
//    implementation(libs.firebase.crashlytics)
//    implementation(libs.firebase.analytics)

    // DataStore
    implementation(libs.androidx.datastore.preferences)
}