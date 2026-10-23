plugins {
    alias(libs.plugins.kotlin.jvm)
    application
}

dependencies {
    testImplementation(kotlin("test"))
    testImplementation(libs.junit.jupiter)
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")


    implementation(libs.pdfbox)
    implementation(libs.tess4j)
    implementation(libs.logback)
    implementation(libs.slf4j.api)

    implementation(project(":lib"))
//    implementation("org.apache.pdfbox:preflight:3.0.6")
//    implementation(enforcedPlatform("org.apache.logging.log4j:log4j-bom:2.25.3"))
//    implementation("org.apache.logging.log4j:log4j-api")
//    implementation("org.apache.logging.log4j:slf4j-impl")
//    implementation("org.apache.logging.log4j:log4j-core")
}

kotlin {
    jvmToolchain(24)
}

application {
    mainClass = "com.swieczorek.receipts.AppKt"
}

tasks.test {
    useJUnitPlatform()
}
