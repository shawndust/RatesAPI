import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
	id("org.springframework.boot") version "2.4.10"
	id("io.spring.dependency-management") version "1.0.11.RELEASE"
	kotlin("jvm") version "1.4.32"
	kotlin("plugin.spring") version "1.4.32"
}

group = "com.example"
version = "0.0.1-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_11

repositories {
	mavenCentral()
}

dependencies {
	implementation("org.springframework.boot:spring-boot-starter-data-jdbc")
	implementation("org.springframework.boot:spring-boot-starter-web")
	implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
	implementation("org.jetbrains.kotlin:kotlin-reflect")
	implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
	implementation("com.google.code.gson:gson:2.8.8")
	implementation("org.webjars:angular-datetime:2.0.1")
	implementation("com.github.tsohr:json:0.0.2")
	implementation("com.yashoid:jsonparsable:1.0.4")
	implementation("org.testng:testng:7.1.0")
	implementation("junit:junit:4.13.1")
	implementation("com.ubertob.kondor:kondor-outcome:1.6.5")
	runtimeOnly("com.h2database:h2")
	testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.6.2")
	testImplementation("io.mockk:mockk:1.9.3")
	testImplementation("org.junit.jupiter:junit-jupiter:5.6.2")
	testImplementation("org.junit.jupiter:junit-jupiter-api")
	testImplementation("io.mockk:mockk:1.9.3")
	testImplementation("org.springframework.boot:spring-boot-starter-test") {
		exclude(module = "junit")
		exclude(module="junit-vintage-engine")
		exclude(module = "mockito-core")
	}

}

tasks.withType<KotlinCompile> {
	kotlinOptions {
		freeCompilerArgs = listOf("-Xjsr305=strict")
		jvmTarget = "11"
	}
}

tasks.withType<Test> {
	useJUnitPlatform()
}

