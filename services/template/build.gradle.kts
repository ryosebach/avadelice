import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

buildscript {
    val kotlinVersion by extra { "1.3.41" }
    val shadowVersion by extra { "5.0.0" }

    repositories {
        mavenCentral()
        maven(url = "https://plugins.gradle.org/m2/")
    }

    dependencies {
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:$kotlinVersion")
        classpath("com.github.jengelman.gradle.plugins:shadow:$shadowVersion")
    }
}

subprojects {
    apply(plugin = "kotlin")
    apply(plugin = "com.github.johnrengelman.shadow")

    repositories {
        mavenCentral()
        jcenter()
    }

    val compileKotlin: KotlinCompile by tasks
    compileKotlin.kotlinOptions {
        jvmTarget = "1.8"
    }

    val compileTestKotlin: KotlinCompile by tasks
    compileTestKotlin.kotlinOptions {
        jvmTarget = "1.8"
    }

    val ktlint by configurations.creating

    dependencies {
        "implementation"("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
        "implementation"("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.2.1")

        "implementation"("org.koin:koin-core:1.0.2")

        "implementation"("ch.qos.logback:logback-classic:1.2.3")

        "testImplementation"("org.assertj:assertj-core:3.12.2")
        "testImplementation"("io.mockk:mockk:1.9.3")

        "testImplementation"("org.jetbrains.kotlin:kotlin-test")
        "testImplementation"("org.jetbrains.kotlin:kotlin-test-junit")
        "testImplementation"("org.koin:koin-test:1.0.2")

        "testImplementation"(kotlin("script-runtime"))
        ktlint("com.pinterest:ktlint:0.34.2")
    }

    tasks.register<JavaExec>("ktlint") {
        group = "verification"
        classpath = ktlint
        main = "com.pinterest.ktlint.Main"
        args("src/**/*.kt")
    }

    tasks.register<JavaExec>("ktlintFormat") {
        group = "verification"
        classpath = ktlint
        main = "com.pinterest.ktlint.Main"
        args("-F", "src/**/*.kt")
    }

    tasks.named("check") {
        dependsOn("ktlint")
    }
}

tasks.register<Exec>("startDatabase") {
    group = "application"
    commandLine("docker")
    args(
        "run",
        "--rm",
        "--name", "template-database",
        "-e", "MYSQL_ROOT_PASSWORD=template-secret",
        "-e", "MYSQL_DATABASE=template",
        "-p", "13306:3306",
        "-v", "$projectDir/db:/docker-entrypoint-initdb.d",
        "-v", "$projectDir/docker/mysql/conf.d:/etc/mysql/conf.d",
        "-d", "mysql:5.6"
    )
    doLast {
        logger.info("database started.")
    }
}

tasks.register<Exec>("stopDatabase") {
    group = "application"
    commandLine("docker")
    args("stop", "template-database")
    doLast {
        logger.info("database stopped.")
    }
}
