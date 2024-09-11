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

dependencies {
    paperweight.paperDevBundle(project.properties["paper_build"].toString())
    compileOnly(fileTree("../libs/compile"))
    implementation(fileTree("../libs/implement"))
    implementation(project(":customitems-api"))
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
        dependsOn(":customitems-api:shadowJar")
        archiveClassifier.set("")
    }

    reobfJar {
        remapperArgs.add("--mixin")
    }

    assemble {
        dependsOn("reobfJar")
        dependsOn("shadowJar")
    }
}
