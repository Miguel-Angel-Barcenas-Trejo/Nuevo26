// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.kotlin.android) apply false
    //firebase --------------------------------------------------------------------
    id("com.google.gms.google-services") version "4.4.2" apply false
    // ----------------------------------------------------------------------------
}

// 👇 Este bloque es necesario si usas dependencias con classpath (como google-services)
buildscript {
    repositories {
        google()
        mavenCentral()
    }
    dependencies {
        classpath("com.google.gms:google-services:4.4.2") // Usa la misma versión que arriba
    }
}