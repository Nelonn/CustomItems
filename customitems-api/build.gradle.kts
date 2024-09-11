plugins {
    `java-library`
    id("io.papermc.paperweight.userdev")
    id("io.github.goooler.shadow")
}

java.toolchain.languageVersion.set(JavaLanguageVersion.of(21))

repositories {
    mavenCentral()
    maven("https://maven.fabricmc.net/") // Mixin
}

paperweight.reobfArtifactConfiguration = io.papermc.paperweight.userdev.ReobfArtifactConfiguration.MOJANG_PRODUCTION

dependencies {
    paperweight.paperDevBundle(project.properties["paper_build"].toString())
    implementation(fileTree("../libs/implement"))
    compileOnly("org.spongepowered:mixin:0.8.5")
    compileOnly("org.jetbrains:annotations:24.1.0")
}

tasks.withType<JavaCompile> {
    options.release.set(21)
    options.encoding = "UTF-8"
}

tasks {
    processResources {
        filteringCharset = "UTF-8"
        filesMatching("coprolite.plugin.json") {
            expand("version" to version)
        }
    }

    shadowJar {
        archiveClassifier.set("")
    }

    assemble {
        //dependsOn("reobfJar")
        dependsOn("shadowJar")
    }
}
