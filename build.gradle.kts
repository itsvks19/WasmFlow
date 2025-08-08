import com.vanniktech.maven.publish.MavenPublishBaseExtension
import groovy.util.Node
import groovy.util.NodeList
import org.jetbrains.dokka.gradle.DokkaTask

plugins {
    // this is necessary to avoid the plugins to be loaded multiple times
    // in each subproject's classloader
    alias(libs.plugins.androidApplication) apply false
    alias(libs.plugins.androidLibrary) apply false
    alias(libs.plugins.composeHotReload) apply false
    alias(libs.plugins.composeMultiplatform) apply false
    alias(libs.plugins.composeCompiler) apply false
    alias(libs.plugins.kotlinJvm) apply false
    alias(libs.plugins.kotlinMultiplatform) apply false
}

buildscript {
    dependencies {
        classpath(libs.vanniktech.publish.plugin)
        classpath(libs.dokka)
    }

    repositories {
        mavenCentral()
        gradlePluginPortal()
        google()
    }
}

apply(plugin = "com.vanniktech.maven.publish.base")

allprojects {
    group = project.property("GROUP") as String
    version = project.property("VERSION_NAME") as String

    repositories {
        mavenCentral()
        google()
    }

    tasks.withType<DokkaTask>().configureEach {
        dokkaSourceSets.configureEach {
            reportUndocumented.set(false)
            skipDeprecated.set(true)
            jdkVersion.set(21)
        }

        if (name == "dokkaHtml") {
            outputDirectory.set(file("${rootDir}/docs/${project.name}"))
            pluginsMapConfiguration.set(
                mapOf(
                    "org.jetbrains.dokka.base.DokkaBase" to """
          {
            "customStyleSheets": [
              "${rootDir.toString().replace('\\', '/')}/docs/css/dokka-logo.css"
            ]
          }
          """.trimIndent()
                )
            )
        }
    }

    plugins.withId("com.vanniktech.maven.publish.base") {
        configure<PublishingExtension> {
            repositories {
                val internalUrl = providers.gradleProperty("internalUrl")
                if (internalUrl.isPresent) {
                    maven {
                        name = "internal"
                        setUrl(internalUrl)
                        credentials(PasswordCredentials::class)
                    }
                }
            }
        }
        val publishingExtension = extensions.getByType(PublishingExtension::class.java)
        configure<MavenPublishBaseExtension> {
            publishToMavenCentral(automaticRelease = true)
            signAllPublications()
            pom {
                description.set("A WebAssembly runtime for Android, Java, and Kotlin Multiplatform.")
                name.set(project.name)
                url.set("https://github.com/itsvks19/WasmFlow")
                licenses {
                    license {
                        name.set("The Apache Software License, Version 2.0")
                        url.set("http://www.apache.org/licenses/LICENSE-2.0.txt")
                        distribution.set("repo")
                    }
                }
                scm {
                    url.set("https://github.com/itsvks19/WasmFlow")
                    connection.set("scm:git:git://github.com/itsvks19/WasmFlow.git")
                    developerConnection.set("scm:git:ssh://git@github.com/itsvks19/WasmFlow.git")
                }
                developers {
                    developer {
                        id.set("itsvks19")
                        name.set("Vivek")
                    }
                }
            }

            val mavenPublications = publishingExtension.publications.withType<MavenPublication>()
            mavenPublications.configureEach {
                if (name != "jvm") return@configureEach
                val jvmPublication = this
                val kmpPublication = mavenPublications.getByName("kotlinMultiplatform")
                kmpPublication.pom.withXml {
                    val root = asNode()
                    val dependencies = (root["dependencies"] as NodeList).firstOrNull() as Node?
                        ?: root.appendNode("dependencies")
                    for (child in dependencies.children().toList()) {
                        dependencies.remove(child as Node)
                    }
                    dependencies.appendNode("dependency").apply {
                        appendNode("groupId", jvmPublication.groupId)
                        appendNode("artifactId", jvmPublication.artifactId)
                        appendNode("version", jvmPublication.version)
                        appendNode("scope", "compile")
                    }
                }
            }
        }
    }
}
