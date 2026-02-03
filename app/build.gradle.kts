import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.io.FileInputStream
import java.util.Properties

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    id("com.google.dagger.hilt.android") version "2.59.1"
    id("io.sentry.android.gradle") version "6.0.0"
    id("com.google.devtools.ksp")
}


// 读取 local.properties 文件
val configProperties = Properties()
configProperties.load(FileInputStream(rootProject.file("config.properties")))

val currentDateTime: LocalDateTime = LocalDateTime.now()
val formatter: DateTimeFormatter = DateTimeFormatter.ofPattern("yyMMddHH")
val versionCodeValue = currentDateTime.format(formatter).toInt()

android {
    namespace = "site.chenc.study_compose"
    compileSdk {
        version = release(36)
    }

    defaultConfig {
        applicationId = "site.chenc.study_compose"
        minSdk = 30
        targetSdk = 36
        versionCode = versionCodeValue
        versionName = configProperties.getProperty("app.version", "")
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

        ndk {
            abiFilters.addAll(listOf("arm64-v8a"))
        }
    }

    flavorDimensions += "environment"

    val baseUrlConfig = mapOf(
        "dev" to configProperties.getProperty("dev.base_url", ""),
        "qa" to configProperties.getProperty("qa.base_url", ""),
        "uat" to configProperties.getProperty("uat.base_url", ""),
        "prod" to configProperties.getProperty("prod.base_url", "")
    )

    productFlavors {
        baseUrlConfig.forEach { (flavorName, baseUrl) ->
            create(flavorName) {
                dimension = "environment"
                buildConfigField("String", "BASE_URL", "\"$baseUrl\"")
                if (flavorName == "dev") applicationIdSuffix = ".dev"
                if (flavorName == "uat") applicationIdSuffix = ".uat"
                if (flavorName == "qa") applicationIdSuffix = ".test"
            }
        }
    }

    buildTypes {
        release {
            isMinifyEnabled = true
            isShrinkResources = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }

        debug {
            isMinifyEnabled = false
            isDebuggable = true
            isShrinkResources = false
            applicationIdSuffix = ".debug"
        }

        all {
            manifestPlaceholders["sentryDsn"] = configProperties.getProperty("sentryDsn", "")
        }
    }

    val javaVersion = JavaVersion.VERSION_11
    compileOptions {
        sourceCompatibility = javaVersion
        targetCompatibility = javaVersion
    }

    kotlin {
        compilerOptions {
            jvmTarget = JvmTarget.fromTarget(javaVersion.toString())
        }
    }

    buildFeatures {
        compose = true
        buildConfig = true
    }
}

androidComponents {
    beforeVariants { variantBuilder ->
        // 过滤掉不需要的构建变体
        val variantName = variantBuilder.name
        if (variantName in listOf("devRelease", "qaDebug", "uatDebug", "prodDebug")) {
            variantBuilder.enable = false
        }
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)

    implementation(libs.androidx.navigation.compose) // 导航库
    implementation(libs.retrofit)       // Retrofit
    implementation(libs.converter.gson) // Gson 转换器
    implementation(libs.kotlinx.coroutines.android) // 协程
    implementation(libs.androidx.material.icons.core)

    implementation(libs.okhttp)
    implementation(libs.logging.interceptor)

    implementation(libs.androidx.lifecycle.viewmodel.ktx)
    implementation(libs.hilt.android)
    implementation(libs.androidx.runtime.livedata)
    implementation(libs.androidx.lifecycle.viewmodel.compose)
    implementation(libs.androidx.hilt.navigation.compose)

    implementation(libs.sentry.android)
    implementation(libs.sentry.compose.android)
    implementation(libs.accompanist.permissions)
    implementation(libs.coil.compose)
    implementation(libs.coil.network.okhttp)
    implementation(libs.lottie.compose)

    implementation(libs.androidx.camera.core)
    implementation(libs.androidx.camera.camera2)
    implementation(libs.androidx.camera.lifecycle)
    implementation (libs.androidx.camera.video)
    implementation(libs.androidx.camera.view)

    implementation(libs.barcode.scanning)
    implementation(libs.androidx.core.splashscreen)
    implementation(libs.androidx.room.runtime)
    implementation(libs.androidx.room.ktx)
    implementation(libs.androidx.paging.runtime)
    implementation(libs.androidx.paging.compose)

    ksp(libs.androidx.room.compiler)
    ksp(libs.hilt.compiler)


    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
}
