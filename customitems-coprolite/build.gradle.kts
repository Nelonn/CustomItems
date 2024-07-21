import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar

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
    compileOnly(fileTree("libs/compile"))
    implementation(fileTree("libs/implement"))
    compileOnly("org.spongepowered:mixin:0.8.5")
    compileOnly("org.jetbrains:annotations:24.1.0")
}

tasks.named<JavaCompile>("compileJava") {
    options.encoding = "UTF-8"
}

tasks.named<Copy>("processResources") {
    filteringCharset = "UTF-8"
    filesMatching("coprolite.plugin.json") {
        expand("version" to version)
    }
}

tasks.named<ShadowJar>("shadowJar") {
    archiveClassifier.set("")
}

tasks.named("assemble").configure {
    dependsOn("reobfJar")
    dependsOn("shadowJar")
}

tasks.reobfJar {
    remapperArgs.add("--mixin")
}
