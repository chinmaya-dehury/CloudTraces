import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id("org.springframework.boot") version "2.6.2"
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
    val dbPwd = System.getenv("POSTGRES_PWD")

    url = "jdbc:postgresql://$dbHost:$dbPort/$dbName"
    user = dbUser
    password = dbPwd
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
    implementation("org.postgresql:postgresql:42.3.1")
    implementation("org.hibernate:hibernate-core:5.6.4.Final")
    implementation("javax.persistence:javax.persistence-api:2.2")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("org.springframework.security:spring-security-test")
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
