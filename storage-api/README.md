# Storage API v1

Storage API - RESTful API, responsible for uploading/downloading trace data files to MiniO S3 bucket 
and creating tables from uploaded trace data files.

## Required setup
JDK 17

Latest Gradle

Latest Docker

Straight hands

## Required ENV variables:

`POSTGRES_DB=`

`POSTGRES_DEFAULT_SCHEMA=public`

`POSTGRES_USERNAME=`

`POSTGRES_PASSWORD=`

`DB_HOST=`

`DB_PORT=`

`MINIO_URL=`

`MINIO_INTERNAL_HOST=`

`MINIO_BUCKET_NAME=`

`MINIO_USER=`

`MINIO_PASSWORD=`

`DOCKER_BUILDKIT=0`

`COMPOSE_DOCKER_CLI_BUILD=0`

`DATA_COLLECTOR_URL=`

`DATA_COLLECTOR_USERNAME=`

`DATA_COLLECTOR_PASSWORD=`


## 1. Start up postgres and minio containers
* `docker-compose up -d`

## 1.1 To start containers separately
* `docker-compose up -d postgres`
* `docker-compose up -d minio`

## 1.2 Configure MiniO
So that postgres could create the table from uploaded trace file, postgres requests the file directly from MiniO bucket.
To make it work, it must be a bit configured:
 1. go to MiniO web console (default `http://localhost:9001`)
 2. Create or manage existing bucket defined in `MINIO_BUCKET_NAME` var
 3. Set `Access policy` to `public`

## 2. To run the project with Gradle for `MacOS/Linux`:
Build the project:
* `./gradlew clean build`

Run tests:
* `./gradlew test`

## 3. NB!
After building and before running the project run DB migrations:
* ` ./gradlew flywayMigrate -i`

## 4. And then...
Run the project:
* `./gradlew bootRun`

## API docs
* for Storage API docs go to `http://localhost:6001/swagger-ui/index.html#/`
