// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.kotlin.android) apply false
    id("org.jetbrains.kotlin.plugin.compose") version "2.0.0"               // this version matches your Kotlin version
    id("androidx.room") version "2.6.1" apply false
    id("com.google.devtools.ksp") version "2.0.0-1.0.23" apply false        //aqui se busca la ultima version https://github.com/google/ksp/releases
}
