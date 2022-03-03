# Storage API v1

## Required setup
JDK 17

Latest Gradle

Latest Docker

Straight hands

## Required ENV variables (will be updated):

`POSTGRES_DB`
`POSTGRES_DEFAULT_SCHEMA=public`
`POSTGRES_USERNAME`
`POSTGRES_PWD`
`DB_HOST`
`DB_PORT`
`MINIO_URL`
`MINIO_INTERNAL_HOST`
`MINIO_BUCKET_NAME`
`MINIO_USER`
`MINIO_PASSWORD`
`DOCKER_BUILDKIT=0`
`COMPOSE_DOCKER_CLI_BUILD=0`

## To run the project with Gradle for `MacOS/Linux`:
Build the project:
* `./gradlew clean build`

Run tests:
* `./gradlew test`

## NB!
After building and before running the project run DB migrations:
* ` ./gradlew flywayMigrate -i`

## And then...
Run the project:
* `./gradlew bootRun`
