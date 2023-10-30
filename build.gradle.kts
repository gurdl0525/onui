import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id("org.springframework.boot") version "2.7.16-SNAPSHOT"
    id("io.spring.dependency-management") version "1.0.15.RELEASE"
    kotlin("jvm") version "1.6.21"
    kotlin("plugin.spring") version "1.6.21"
    kotlin("plugin.jpa") version "1.6.21"
}

group = "com.example"
version = "0.0.1-SNAPSHOT"

java {
    sourceCompatibility = JavaVersion.VERSION_17
}

repositories {
    mavenCentral()
    maven { url = uri("https://repo.spring.io/milestone") }
    maven { url = uri("https://repo.spring.io/snapshot") }
}

extra["springCloudVersion"] = "2021.0.8"

dependencies {

    // orm
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")

    // redis
    implementation("org.springframework.boot:spring-boot-starter-data-redis")

    // mysql
    runtimeOnly("com.mysql:mysql-connector-j")

    // security
    implementation("org.springframework.boot:spring-boot-starter-security")

    // validation
    implementation("org.springframework.boot:spring-boot-starter-validation")

    // web
    implementation("org.springframework.boot:spring-boot-starter-web")

    // kotlin
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
    implementation("org.jetbrains.kotlin:kotlin-reflect")

    // feign client
    implementation("org.springframework.cloud:spring-cloud-starter-openfeign")
    implementation("io.github.openfeign:feign-httpclient")

    // firebase
    implementation("com.google.firebase:firebase-admin:9.2.0")

    // logging
    implementation("io.github.microutils:kotlin-logging-jvm:2.0.10")

    // jwt
    implementation("io.jsonwebtoken:jjwt:0.9.1")

    // cloud
    implementation("org.springframework.cloud:spring-cloud-dependencies:2021.0.5")

    // s3
    implementation("com.amazonaws:aws-java-sdk-s3:1.12.232")
}

dependencyManagement {
    imports {
        mavenBom("org.springframework.cloud:spring-cloud-dependencies:${property("springCloudVersion")}")
    }
}

tasks.withType<KotlinCompile> {
    kotlinOptions {
        freeCompilerArgs += "-Xjsr305=strict"
        jvmTarget = "17"
    }
}

allOpen {
    annotation("javax.persistence.Entity")
    annotation("org.springframework.data.redis.core.RedisHash")
    annotation("javax.persistence.MappedSuperclass")
}

noArg {
    annotation("javax.persistence.Entity")
    annotation("org.springframework.data.redis.core.RedisHash")
    annotation("javax.persistence.MappedSuperclass")
}