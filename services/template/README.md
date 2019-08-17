# Avadelice Template Service 

API server in Kotlin.  
This project is designed based on [The Twelve Factor App](https://12factor.net/) and The Clean Architecture.

## Run

Exec the following to run.

```shell
./gradlew startDatabase
./gradlew run
```

So you can refer to the following.

http://localhost:17301/v1/health

## Build

Exec the following to build.

```shell
./gradlew shadowJar
```

A tar is generated that contains the executable file.

```shell
./infrastructure/build/libs/template.jar
```

## Test

Exec the following to test.

```shell
./gradlew test
```
