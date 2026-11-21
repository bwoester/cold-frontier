plugins {
    // Apply the java-library plugin for API and implementation separation.
    `java-library`
    alias(libs.plugins.lombok)
}

repositories {
    // Use Maven Central for resolving dependencies.
    mavenCentral()
}

val prototypeTestSupport = layout.projectDirectory.dir("src/prototypeTestSupport/java")
val testSourceSet = sourceSets.named("test") {
    java.srcDir(prototypeTestSupport)
}

dependencies {
    // Align all JUnit engines and the launcher, including the Cucumber suite.
    testImplementation(platform(libs.junit.bom))
    testImplementation(libs.junit.jupiter)
    testImplementation(libs.mockito.core)
    testImplementation(libs.mockito.junit.jupiter)
    testImplementation(libs.testcontainers.junit.jupiter)

    testImplementation(libs.cucumber.java)
    testImplementation(libs.cucumber.junit.platform.engine)
    testImplementation(libs.junit.platform.suite)

    testRuntimeOnly(libs.junit.platform.launcher)

    // This dependency is exported to consumers, that is to say found on their compile classpath.
    api(libs.commons.math3)

    // This dependency is used internally, and not exposed to consumers on their own compile classpath.
    implementation(libs.guava)
    implementation(libs.guice)
    implementation(libs.slf4j.api)
    
    // ObjectMapper appears in the public serializer/repository API.
    // Use our own Jackson BOM, never the backend's framework platform.
    api(platform(libs.jackson.bom))
    api(libs.jackson.databind)
    implementation(libs.jackson.core)
    implementation(libs.jackson.annotations)
    implementation(libs.jackson.datatype.jsr310)
    implementation(libs.jackson.module.parameter.names)

    // NATS JetStream client
    implementation(libs.nats.client)
    runtimeOnly(libs.logback.classic)
}

// Apply a specific Java toolchain to ease working on different environments.
java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(21)
    }
}

tasks.withType<JavaCompile>().configureEach {
    options.encoding = "UTF-8"
    options.release = 21
    // Required for jackson-module-parameter-names, which handles (de)serialization of record classes
    options.compilerArgs.add("-parameters")
}

tasks.named<Test>("test") {
    useJUnitPlatform {
        excludeTags("integration")
    }
}

// Tagged tests deliberately share one source set. Gradle 9 requires explicit inputs.
tasks.register<Test>("integrationTest") {
    group = "verification"
    description = "Runs integration tests against services started by Testcontainers."
    testClassesDirs = files(testSourceSet.map { it.output.classesDirs })
    classpath = files(testSourceSet.map { it.runtimeClasspath })
    useJUnitPlatform {
        includeEngines("junit-jupiter")
        includeTags("integration")
    }
    shouldRunAfter(tasks.named("test"))
}

val acceptanceTest = testing.suites.register<JvmTestSuite>("acceptanceTest") {
    useJUnitJupiter(libs.versions.junit.jupiter.get())
    sources.java.srcDir(prototypeTestSupport)
    dependencies {
        implementation(project())
        implementation(platform(libs.junit.bom))
        implementation(libs.cucumber.java)
        implementation(libs.cucumber.junit.platform.engine)
        // Use the Gherkin parser version selected by Cucumber.
        implementation("io.cucumber:gherkin")
        implementation(libs.junit.platform.suite)
    }
    targets.all {
        testTask.configure {
            description = "Runs bound game behavior scenarios against the prototype."
            classpath += files(rootProject.layout.projectDirectory.dir("doc/game-design"))
            useJUnitPlatform {
                includeEngines("junit-platform-suite")
            }
            shouldRunAfter(tasks.named("test"))
        }
    }
}

tasks.named("check") {
    dependsOn(acceptanceTest)
}

tasks.register<JavaExec>("pendingScenarioReport") {
    group = "verification"
    description = "Reports the game behavior examples explicitly deferred from acceptance tests."
    classpath = files(acceptanceTest.map { it.sources.runtimeClasspath })
    mainClass.set("de.bwoester.coldfrontier.acceptance.reporting.PendingScenarioReport")
    args(
        rootProject.layout.projectDirectory.dir("doc/game-design/scenarios").asFile,
        layout.buildDirectory.file("reports/pending-scenarios.md").get().asFile
    )
}
