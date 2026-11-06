plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.ksp)
    alias(libs.plugins.room)
    alias(libs.plugins.hilt)
}

val mockitoAgent = configurations.create("mockitoAgent")

android {
    namespace = "br.com.usinasantafe.pcp"
    compileSdk {
        version = release(37)
    }

    defaultConfig {
        applicationId = "br.com.usinasantafe.pcp"
        minSdk = 24
        targetSdk = 37
        versionCode = 2
        versionName = "5.01"

        testInstrumentationRunner = "br.com.usinasantafe.pcp.InstrumentationTestRunner"

    }

    buildTypes {
        release {
            optimization {
                enable = false
            }
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    buildFeatures {
        compose = true
        buildConfig = true
        resValues = true
    }

    productFlavors {
        flavorDimensions += "version"
        create("dev") {
            dimension = "version"
            applicationIdSuffix = ".dev"
            manifestPlaceholders["appName"] = "PCP-DEV"
            resValue("string", "base_url", "https://app.usinasantafe.com.br/pcpcompdev/view/")
        }
        create("qa") {
            dimension = "version"
            applicationIdSuffix = ".qa"
            manifestPlaceholders["appName"] = "PCP-QA"
            resValue("string", "base_url", "https://app.usinasantafe.com.br/pcpcompqa/view/")
        }
        create("prod") {
            dimension = "version"
            applicationIdSuffix = ".prod"
            manifestPlaceholders["appName"] = "PCP"
            resValue("string", "base_url", "https://app.usinasantafe.com.br/pcpcompprod/versao_5_00/view/")
        }
    }

    sourceSets {
        getByName("androidTest") {
            java.directories.add("src/androidTest/java")
            res.directories.add("src/androidTest/res")
            manifest.srcFile("src/androidTest/AndroidManifest.xml")
        }
    }

    testOptions {
        unitTests {
            isIncludeAndroidResources = true
        }
    }
}

room {
    schemaDirectory("$projectDir/schemas")
}

dependencies {
    // --- Compose ---
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.activity.compose)
    implementation(libs.androidx.compose.material3)
    implementation(libs.androidx.compose.material.icons.extended)
    implementation(libs.androidx.compose.ui)
    implementation(libs.androidx.compose.ui.graphics)
    implementation(libs.androidx.compose.ui.tooling.preview)
    implementation(libs.androidx.material3)
    debugImplementation(libs.androidx.compose.ui.tooling)
    debugImplementation(libs.androidx.compose.ui.test.manifest)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.compose.ui.test.junit4)

    // --- Core & Lifecycle ---
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.lifecycle.compose.viewmodel)
    implementation(libs.androidx.lifecycle.compose.runtime)
    implementation(libs.androidx.navigation.compose)
    implementation(libs.androidx.tracing)

    // --- Hilt & Injection ---
    implementation(libs.hilt.android.core)
    implementation(libs.androidx.hilt.navigation.compose)
    implementation(libs.hilt.ext.work)
    ksp(libs.hilt.compiler)
    ksp(libs.hilt.ext.compiler)
    kspTest(libs.hilt.compiler)
    kspAndroidTest(libs.hilt.compiler)

    // --- Room ---
    implementation(libs.room.runtime)
    implementation(libs.room.ktx)
    ksp(libs.room.compiler)

    // --- WorkManager ---
    implementation(libs.work.runtime)

    // --- Coroutines ---
    implementation(libs.kotlinx.coroutines.android)

    // --- Network & Serialization ---
    implementation(libs.retrofit)
    implementation(libs.retrofit.gson)
    implementation(libs.okhttp.logging.interceptor)
    implementation(libs.kotlinx.serialization.json)

    // --- Utils ---
    implementation(libs.guava)
    implementation(libs.timber)

    // --- Local Unit Tests (test) ---
    testImplementation(libs.junit)
    testImplementation(libs.mockito.kotlin)
    testImplementation(libs.kotlinx.coroutines.test)
    testImplementation(libs.core.ktx)
    testImplementation(libs.robolectric)
    testImplementation(libs.retrofit)
    testImplementation(libs.retrofit.gson)
    testImplementation(libs.okhttp.logging.interceptor)
    testImplementation(libs.okhttp.mock.webserver)
    testImplementation(libs.room.testing)
    testImplementation(libs.work.testing)
    testImplementation(libs.hilt.android.testing)
    testImplementation(kotlin("test"))

    // Mockito Java Agent
    mockitoAgent(libs.mockito.core) {
        isTransitive = false
    }

    // --- Instrumentation Tests (androidTest) ---
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.navigation.testing)
    androidTestImplementation(libs.okhttp.mock.webserver)
    androidTestImplementation(libs.room.testing)
    androidTestImplementation(libs.work.testing)
    androidTestImplementation(libs.hilt.android.testing)
    androidTestImplementation(libs.mockito.kotlin)
    androidTestImplementation(libs.mockito.android)
    androidTestImplementation(kotlin("test"))

    implementation(libs.google.play.services.location)
    implementation(libs.google.maps.compose)
    androidTestImplementation(libs.androidx.rules)
    implementation(libs.coil.compose)
}

tasks.withType<Test>().configureEach {
    jvmArgs("-javaagent:${mockitoAgent.asPath}")
}