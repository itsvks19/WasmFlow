import com.vanniktech.maven.publish.JavadocJar
import com.vanniktech.maven.publish.KotlinMultiplatform
import com.vanniktech.maven.publish.MavenPublishBaseExtension
import org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi
import org.jetbrains.kotlin.gradle.ExperimentalWasmDsl
import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.targets.js.webpack.KotlinWebpackConfig

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidLibrary)
    id("org.jetbrains.dokka")
    id("com.vanniktech.maven.publish.base")
}

kotlin {
    androidTarget {
        @OptIn(ExperimentalKotlinGradlePluginApi::class)
        compilerOptions {
            jvmTarget.set(JvmTarget.JVM_21)
        }

        publishLibraryVariants("release")
    }

    jvm()

    @OptIn(ExperimentalWasmDsl::class)
    wasmJs {
        binaries.executable()
        browser {
            val rootDirPath = project.rootDir.path
            val projectDirPath = project.projectDir.path
            commonWebpackConfig {
                devServer = (devServer ?: KotlinWebpackConfig.DevServer()).apply {
                    static = (static ?: mutableListOf()).apply {
                        // Serve sources to debug inside browser
                        add(rootDirPath)
                        add(projectDirPath)
                    }
                }
            }
        }

        generateTypeScriptDefinitions()

        compilerOptions {
            freeCompilerArgs.add("-Xwasm-attach-js-exception")
        }
    }

    sourceSets {
        val commonMain by getting

        // Must be defined before androidMain and jvmMain
        val commonJvmAndroid = create("commonJvmAndroid") {
            dependsOn(commonMain)

            dependencies {
                implementation(libs.chicory.runtime)
                implementation(libs.chicory.wasi)
                implementation(libs.chicory.wabt)
                implementation(libs.chicory.wasm.tools)
                implementation(libs.chicory.annotations)
            }
        }

        val androidMain by getting {
            dependsOn(commonJvmAndroid)
            dependencies {

            }
        }

        val jvmMain by getting {
            dependsOn(commonJvmAndroid)
            dependencies {

            }
        }

        commonMain.dependencies {
            implementation(libs.okio)
            implementation(libs.kotlinx.coroutinesCore)
            implementation(libs.kotlinx.io.core)
        }

        commonTest.dependencies {
            implementation(libs.kotlin.test)
        }

        wasmJsMain.dependencies {
            implementation(libs.kotlinx.browser)
        }
    }

    jvmToolchain(21)
}

android {
    namespace = "com.wasmflow.shared"
    compileSdk = libs.versions.android.compileSdk.get().toInt()
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_21
        targetCompatibility = JavaVersion.VERSION_21
    }
    defaultConfig {
        minSdk = libs.versions.android.minSdk.get().toInt()
    }

    dependencies {

    }
}

configure<MavenPublishBaseExtension> {
    configure(
        KotlinMultiplatform(javadocJar = JavadocJar.Dokka("dokkaGfm")),
    )
}
