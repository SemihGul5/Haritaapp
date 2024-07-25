plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)
    alias(libs.plugins.google.android.libraries.mapsplatform.secrets.gradle.plugin)
    id("androidx.navigation.safeargs.kotlin")
    id("com.google.gms.google-services")
}

android {
    namespace = "com.abrebostudio.haritaapp"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.abrebostudio.haritaapp"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
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
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }
    buildFeatures {
        viewBinding = true
        dataBinding = true
    }

}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    implementation(libs.play.services.maps)
    implementation(libs.androidx.navigation.fragment.ktx)
    implementation(libs.androidx.navigation.ui.ktx)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    implementation (libs.androidx.lifecycle.viewmodel)
    implementation(libs.androidx.activity.ktx)
    implementation (libs.androidx.fragment.ktx)

    implementation(platform("com.google.firebase:firebase-bom:33.1.2"))
    implementation("com.google.firebase:firebase-analytics")
    implementation(libs.google.firebase.auth)
    implementation(libs.firebase.firestore)
    implementation(libs.firebase.storage)

    implementation ("com.squareup.picasso:picasso:2.71828")

    implementation ("com.google.android.gms:play-services-maps:18.0.0")
    implementation ("com.google.maps.android:android-maps-utils:2.3.0")

    implementation ("com.github.bumptech.glide:glide:4.13.2")

    implementation ("com.squareup.retrofit2:retrofit:2.6.0")
    implementation ("com.google.code.gson:gson:2.9.0")
    implementation ("com.squareup.retrofit2:converter-gson:2.5.0")
    implementation (libs.google.android.maps.utils)



}