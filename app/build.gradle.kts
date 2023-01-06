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
    compileSdk = 33

    defaultConfig {
        applicationId = "com.roy93group.cintalauncher"
        minSdk = 26
        targetSdk = 33
        versionCode = 20230102
        versionName = "2023.01.02"
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        signingConfig = signingConfigs.getByName("debug")
        multiDexEnabled = true
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = true
            isShrinkResources = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    namespace = "com.roy93group.launcher"
}

dependencies {
    implementation("com.github.tplloi:base:4.6.19")
    implementation("com.willowtreeapps:fuzzywuzzy-kotlin-jvm:0.9.0")
    implementation("io.posidon:android.launcherUtils:30aa020c1a")
    implementation("io.posidon:android.libduckduckgo:22.0")
    implementation("io.posidon:android.rsslib:22.0")
    implementation("io.posidon:android.conveniencelib:22.0")
    implementation("androidx.palette:palette-ktx:1.0.0")
    implementation("com.google.firebase:firebase-bom:31.1.1")
    implementation("com.google.firebase:firebase-analytics-ktx:21.2.0")
    implementation("com.google.firebase:firebase-crashlytics-ktx:18.3.2")
    implementation("com.google.firebase:firebase-config-ktx:21.2.0")
    implementation("com.github.cdflynn:turn-layout-manager:v1.3.1")
    implementation("com.github.Chrisvin:RubberPicker:v1.5")

    // debugImplementation because LeakCanary should only run in debug builds.
    debugImplementation("com.squareup.leakcanary:leakcanary-android:2.10")
}

configurations.all {
    resolutionStrategy.cacheChangingModulesFor(0, "seconds")
}
