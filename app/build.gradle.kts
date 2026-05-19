plugins {
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.junit5)
    alias(libs.plugins.ksp)
    alias(libs.plugins.ktlint)
    alias(libs.plugins.compose.compiler)
    alias(libs.plugins.jacoco)
    alias(libs.plugins.hiltAndroid)
}

jacoco {
    toolVersion = libs.versions.jacoco.get()
}

tasks.register<JacocoReport>("jacocoTestReport") {
    dependsOn("testDevDebugUnitTest", "createDevDebugCoverageReport")

    reports {
        xml.required = true
        html.required = true
    }

    val fileFilter = listOf("**/R.class", "**/R\$*.class", "**/BuildConfig.*", "**/Manifest*.*", "**/*Test*.*", "android/**/*.*", "**/hilt/**")
    val debugTree =
        fileTree(layout.buildDirectory.dir("intermediates/built_in_kotlinc/devDebug/compileDevDebugKotlin/classes")) {
            exclude(fileFilter)
        }
    val mainSrc = "${project.projectDir}/src/main/java"

    sourceDirectories.setFrom(files(listOf(mainSrc)))
    classDirectories.setFrom(files(listOf(debugTree)))

    // I don't think this works
    executionData.setFrom(
        fileTree(layout.buildDirectory) {
            include(
                "outputs/code_coverage/devDebugAndroidTest/connected/**/*.ec",
                "outputs/unit_test_code_coverage/devDebugUnitTest/testDevDebugUnitTest.exec",
            )
        },
    )
}

android {
    namespace = "com.cjmobileapps.quidditchplayersandroid"
    compileSdk = 36

    defaultConfig {
        applicationId = "com.cjmobileapps.quidditchplayersandroid"
        minSdk = 26
        targetSdk = 36
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }

        javaCompileOptions {
            annotationProcessorOptions {
                arguments += mapOf("room.schemaLocation" to "$projectDir/schemas")
            }
        }
    }

    buildTypes {
        debug {
            applicationIdSuffix = ".debug"
            isDebuggable = true
            isMinifyEnabled = false
            // IMPORTANT: If testCoverageEnabled and Unit test break you can not see errors
            enableUnitTestCoverage = true
            enableAndroidTestCoverage = true
        }
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro",
            )
        }
    }

    flavorDimensions += "version"

    productFlavors {
        create("dev") {
            buildConfigField("String", "BASE_URL", "\"http://10.0.2.2:8080/\"")
            dimension = "version"
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    buildFeatures {
        compose = true
        buildConfig = true
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

kotlin {
    compilerOptions {
        jvmTarget.set(org.jetbrains.kotlin.gradle.dsl.JvmTarget.JVM_17)
    }
}

composeCompiler {
    reportsDestination = layout.buildDirectory.dir("compose_compiler")
}

dependencies {

    // Android Compose
    implementation(libs.activity.compose)
    implementation(platform(libs.compose.bom))
    implementation(libs.compose.ui)
    implementation(libs.compose.ui.tooling.preview)
    implementation(libs.material3)
    implementation(libs.material.icons.core)
    implementation(libs.navigation.compose)
    implementation(libs.coil.compose)

    // Android Compose Testing
    androidTestImplementation(libs.junit)
    androidTestImplementation(libs.espresso)
    androidTestImplementation(platform(libs.compose.bom))
    androidTestImplementation(libs.ui.test.junit4)
    debugImplementation(libs.ui.tooling)
    debugImplementation(libs.ui.test.manifest)

    // Hilt
    implementation(libs.hilt)
    ksp(libs.hilt.compiler)
    implementation(libs.hilt.navigation.compose)

    // Retrofit 2
    implementation(libs.retrofit)
    implementation(libs.logging.interceptor)
    implementation(libs.converter.gson)

    // Coroutines
    implementation(libs.coroutines.adapter)
    testImplementation(libs.coroutines.test)

    // Junit 5
    testImplementation(libs.junit.jupiter)
    testImplementation(libs.mockito.kotlin)

    // Junit 4
    implementation(libs.junit.ktx)
    implementation(libs.test.rules)
    testImplementation(libs.junit4)
    testRuntimeOnly(libs.vintage.engine)
    testRuntimeOnly(libs.junit.platform.launcher)

    // Timber logger
    implementation(libs.timber)

    // Room
    implementation(libs.room.runtime)
    annotationProcessor(libs.room.compiler)
    ksp(libs.room.compiler)
    implementation(libs.room.ktx)
}
