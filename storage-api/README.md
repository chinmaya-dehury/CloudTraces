# Storage API v1

## Required setup
JDK 17

Latest Gradle

Latest Docker

Straight hands

## Required ENV variables (will be updated):

`POSTGRES_VERSION`

`POSTGRES_DB`

`POSTGRES_USERNAME`

`POSTGRES_PWD`

`DB_HOST`

`DB_PORT`

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
