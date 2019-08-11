import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

val ktorVersion = "1.2.2"
val koinVersion = "1.0.2"
val jacksonVersion = "2.9.9"

plugins {
    application
}

application {
    mainClassName = "io.ktor.server.netty.EngineMain"
}

repositories {
    mavenCentral()
    jcenter()
}

dependencies {
    implementation(project(":app:common"))
    implementation(project(":app:dependency"))
    implementation(project(":app:interface"))

    implementation("io.ktor:ktor-server-netty:$ktorVersion")
    implementation("io.ktor:ktor-locations:$ktorVersion")
    implementation("io.ktor:ktor-jackson:$ktorVersion")
    implementation("org.koin:koin-ktor:$koinVersion")

    implementation("com.fasterxml.jackson.datatype:jackson-datatype-jsr310:$jacksonVersion")
    implementation("com.fasterxml.jackson.datatype:jackson-datatype-joda:$jacksonVersion")
    implementation("com.fasterxml.jackson.core:jackson-databind:$jacksonVersion")

    testImplementation(project(":app:data-access"))
    testImplementation(project(":app:domain"))
    testImplementation(project(":app:use-case"))

    testImplementation("io.ktor:ktor-server-test-host:$ktorVersion")
    testImplementation("org.koin:koin-test:$koinVersion")
    testImplementation("org.assertj:assertj-core:3.11.1")
    testImplementation("io.mockk:mockk:1.8.10.kotlin13")
    testImplementation("com.ninja-squad:DbSetup:2.1.0")
}

tasks.named<JavaExec>("run") {
    args = listOf("-config=src/main/resources/application-local.conf")
}

tasks.named<JavaExec>("runShadow") {
    args = listOf("-config=src/main/resources/application-local.conf")
}

tasks.withType<ShadowJar> {
    archiveBaseName.set(project.rootProject.name)
    archiveClassifier.set("")
}

tasks.withType(KotlinCompile::class).all {
    kotlinOptions {
        freeCompilerArgs = listOf("-Xuse-experimental=kotlin.Experimental")
    }
}
