plugins {
    id("com.android.application")
    id("kotlin-android")
    id("kotlin-android-extensions")
    id("com.google.gms.google-services")
    id("com.google.firebase.crashlytics")
}

android {
    signingConfigs {
        create("release") {
            storeFile = file("keystore.jks")
            storePassword = "04021993"
            keyAlias = "loi"
            keyPassword = "04021993"
        }
    }
    compileSdk = 34

    defaultConfig {
        applicationId = "com.roy93group.cintalauncher"
        minSdk = 26
        targetSdk = 34
        versionCode = 20231224
        versionName = "2023.12.24"
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
//        signingConfig = signingConfigs.getByName("debug")
        multiDexEnabled = true
    }

    buildTypes {
        getByName("release") {
            isDebuggable = false
            isMinifyEnabled = true
            isShrinkResources = true
//            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
            proguardFiles(getDefaultProguardFile("proguard-android.txt"), "proguard-rules.pro")
            signingConfig = signingConfigs.getByName("release")
        }
    }
    namespace = "com.roy93group.launcher"
}

dependencies {
    implementation("com.github.tplloi:base:4.6.45")
    implementation("com.willowtreeapps:fuzzywuzzy-kotlin-jvm:0.9.0")
    implementation("io.posidon:android.launcherUtils:30aa020c1a")
    implementation("io.posidon:android.libduckduckgo:22.0")
    implementation("io.posidon:android.rsslib:22.0")
    implementation("io.posidon:android.conveniencelib:22.0")
    implementation("androidx.palette:palette-ktx:1.0.0")
    //noinspection BomWithoutPlatform
    implementation("com.google.firebase:firebase-bom:32.2.3")
    implementation("com.google.firebase:firebase-analytics-ktx:21.3.0")
    implementation("com.google.firebase:firebase-crashlytics-ktx:18.4.1")
    implementation("com.google.firebase:firebase-config-ktx:21.4.1")
    implementation("com.github.cdflynn:turn-layout-manager:v1.3.1")
    implementation("com.github.Chrisvin:RubberPicker:v1.5")
//    https://github.com/SimformSolutionsPvtLtd/SSBiometricsAuthentication
    implementation("androidx.biometric:biometric:1.2.0-alpha05")
    // debugImplementation because LeakCanary should only run in debug builds.
    debugImplementation("com.squareup.leakcanary:leakcanary-android:2.12")
}

configurations.all {
    resolutionStrategy.cacheChangingModulesFor(0, "seconds")
}
