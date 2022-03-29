import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id("org.springframework.boot") version "2.4.3"
    id("io.spring.dependency-management") version "1.0.11.RELEASE"
    id("org.flywaydb.flyway") version "8.4.2"
    kotlin("jvm") version "1.6.10"
    kotlin("plugin.spring") version "1.6.10"
}

flyway {
    val dbHost = System.getenv("DB_HOST")
    val dbPort = System.getenv("DB_PORT")
    val dbName = System.getenv("POSTGRES_DB")
    val dbUser = System.getenv("POSTGRES_USERNAME")
    val dbPswd = System.getenv("POSTGRES_PASSWORD")

    url = "jdbc:postgresql://$dbHost:$dbPort/$dbName"
    user = dbUser
    password = dbPswd
}

group = "com.cloudTraceBucket"
version = "1.0.0"
java.sourceCompatibility = JavaVersion.VERSION_17

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-security")
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
    implementation("org.postgresql:postgresql:42.3.3")
    implementation("org.hibernate:hibernate-core:5.6.5.Final")
    implementation("javax.persistence:javax.persistence-api:2.2")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa:2.6.3")
    implementation("com.vladmihalcea:hibernate-types-55:2.14.0")
    implementation("com.jlefebure:spring-boot-starter-minio:1.10")
    implementation("io.minio:minio:8.1.0")
    implementation("commons-io:commons-io:2.11.0")
    implementation("org.springframework.boot:spring-boot-starter-actuator:2.6.3")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin:2.13.1")
    implementation("net.sf.supercsv:super-csv:2.4.0")
    implementation("net.sf.oval:oval:3.2.1")
    implementation("commons-io:commons-io:2.11.0")
    implementation("org.springframework:spring-test:5.3.16")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("org.springframework.security:spring-security-test")
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.8.2")
    testImplementation("org.junit.platform:junit-platform-runner:1.8.2")
}

tasks.withType<KotlinCompile> {
    kotlinOptions {
        freeCompilerArgs = listOf("-Xjsr305=strict")
        jvmTarget = "17"
    }
}

tasks.withType<Test> {
    useJUnitPlatform()
}
