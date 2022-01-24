import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar
import net.minecraftforge.gradle.user.IReobfuscator
import net.minecraftforge.gradle.user.ReobfMappingType.SEARGE
import net.minecraftforge.gradle.user.TaskSingleReobf

plugins {
    kotlin("jvm") version "1.6.10"
    id("net.minecraftforge.gradle.forge") version "6f53277"
    id("com.github.johnrengelman.shadow") version "6.1.0"
}

version = "1"
group = "me.bongowole.bongobox"

minecraft {
    version = "1.8.9-11.15.1.2318-1.8.9"
    runDir = "run"
    mappings = "stable_22"
    makeObfSourceJar = false
}

repositories {
    mavenLocal()
    mavenCentral()
    maven("https://maven.minecraftforge.net/")
    maven("https://jitpack.io")
}

dependencies {
    implementation(kotlin("stdlib-jdk8"))
}

tasks.apply {
    processResources {
        inputs.property("version", project.version)
        inputs.property("mcversion", project.minecraft.version)

        filesMatching("mcmod.info") {
            expand(mapOf("version" to project.version, "mcversion" to project.minecraft.version))
        }
    }
    jar {
        archiveFileName.set("${project.name} v${project.version}.jar")
        enabled = false
    }
    shadowJar {
        archiveFileName.set("${project.name} v${project.version}.jar")
        duplicatesStrategy = DuplicatesStrategy.EXCLUDE
        exclude(
            "dummyThing",
            "**/module-info.class",
            "META-INF/proguard/**",
            "META-INF/maven/**",
            "META-INF/versions/**"
        )
        reobfJar.get().dependsOn(this)
    }
}