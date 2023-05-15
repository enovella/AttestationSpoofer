import android.annotation.SuppressLint

plugins {
    id("com.android.application")
}

android {
    namespace = "es.swer45.attestationspoofer"
    compileSdk = 33

    defaultConfig {
        applicationId = "es.swer45.attestationspoofer"
        minSdk = 26
        targetSdk = 33
        versionCode = 1
        versionName = "1.0"
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
}

dependencies {
    compileOnly("de.robv.android.xposed:api:82")
    implementation("org.bouncycastle:bcprov-jdk18on:1.73")
    implementation("com.google.guava:guava:31.1-android")
}