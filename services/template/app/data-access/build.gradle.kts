dependencies {
    implementation(project(":app:common"))
    implementation(project(":app:domain"))
    implementation(project(":app:use-case"))
    
    implementation("org.jetbrains.exposed:exposed:0.16.1")
    implementation("mysql:mysql-connector-java:8.0.16")
    implementation("com.zaxxer:HikariCP:3.3.1")
}
