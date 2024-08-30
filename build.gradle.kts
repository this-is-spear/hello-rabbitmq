plugins {
    id("java")
    kotlin("jvm")
}

group = "tis"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

val slf4jVersion = "2.0.16"
val amqpClientVersion = "5.17.1"

dependencies {
    // https://mvnrepository.com/artifact/org.slf4j/slf4j-api
    implementation("org.slf4j:slf4j-api:$slf4jVersion")
    // https://mvnrepository.com/artifact/org.slf4j/slf4j-simple
    implementation("org.slf4j:slf4j-simple:$slf4jVersion")
    // https://mvnrepository.com/artifact/com.rabbitmq/amqp-client
    implementation("com.rabbitmq:amqp-client:$amqpClientVersion")
    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    implementation(kotlin("stdlib-jdk8"))
}

tasks.test {
    useJUnitPlatform()
}
kotlin {
    jvmToolchain(17)
}
