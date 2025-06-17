plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    //firebase -----------------------------------------------------
    id("com.google.gms.google-services")
    //apply plugin: 'com.google.gms.google-services'
    // -------------------------------------------------------------
}

android {
    namespace = "com.empresa.vclaminationsmantenimiento"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.empresa.vclaminationsmantenimiento"
        minSdk = 23
        targetSdk = 35
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
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
    buildFeatures {
        viewBinding = true
    }
    //---------------------------
    packagingOptions {
        resources {
            excludes += setOf(
                "/META-INF/LICENSE",
                "/META-INF/LICENSE.md",
                "/META-INF/NOTICE",
                "/META-INF/NOTICE.md",
                "/META-INF/DEPENDENCIES"
            )
        }
    }
}
dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.constraintlayout)
    implementation(libs.androidx.lifecycle.livedata.ktx)
    implementation(libs.androidx.lifecycle.viewmodel.ktx)
    implementation(libs.androidx.navigation.fragment.ktx)
    implementation(libs.androidx.navigation.ui.ktx)
    implementation(libs.androidx.activity)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    //firebase --------------------------------------------------------------------
    implementation(platform("com.google.firebase:firebase-bom:33.13.0"))
    implementation("com.google.firebase:firebase-auth")
    //implementation("com.google.firebase:firebase-firestore-ktx:24.10.3")
    implementation("com.google.firebase:firebase-firestore-ktx")
    implementation("com.google.firebase:firebase-database-ktx")
    implementation("com.google.firebase:firebase-messaging")
    implementation("com.google.firebase:firebase-installations")

    // ----------------------------------------------------------------------------
    // iTextG para manipulación de PDFs
    implementation("com.itextpdf:itextg:5.5.10")
    // JavaMail para envío de correos
    implementation ("com.sun.mail:android-mail:1.6.7")
    implementation ("com.sun.mail:android-activation:1.6.7")
    // librería Volley
    implementation ("com.android.volley:volley:1.2.1")

}



