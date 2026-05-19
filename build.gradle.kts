// Top-level build file where you can add configuration options common to all sub-projects/modules.
buildscript {
    dependencies {
        classpath(libs.androidGradlePlugin)
        classpath(libs.junit5Plugin)
        classpath(libs.ktlintGradle)
        classpath(libs.jacocoCore)
    }
}

plugins {
    alias(libs.plugins.androidApplication).apply(false)
    alias(libs.plugins.hiltAndroid).apply(false)
    alias(libs.plugins.junit5).apply(false)
    alias(libs.plugins.ksp).apply(false)
    alias(libs.plugins.ktlint).apply(false)
    alias(libs.plugins.compose.compiler) apply false
}
