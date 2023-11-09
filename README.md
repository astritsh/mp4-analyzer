# MP4 Analyser

castLabs

## Running

This project can be run using Docker or Java JDK 17 with Maven.

#### Running using Docker

1. [Docker][]: Download and install Docker.

2. In the project root directory, run the following command:

```
docker build -t mp4analyzer . && docker run -p 8080:8080 --name mp4analyzer mp4analyzer
```

#### Running using Java and Maven

1. [JDK][]: Download and install Java JDK 17.

2. After installing Java, run the project using the following command:

```

./mvnw spring-boot:run

```

The application will be running on port 8080.

## Executing Requests

The Mp4 Analyzer endpoint is:

```
GET: http://localhost:8080/api/v1/analyze-mp4?videoUrl={your-url}
```

Replace `{your-url}` with your video URL.

#### Running using a web browser
```
http://localhost:8080/api/v1/analyze-mp4?videoUrl=http://demo.castlabs.com/tmp/text0.mp4
```
#### Running using curl
```
curl -G -d "videoUrl={your-url}" http://localhost:8080/api/v1/analyze-mp4 
```
Example:
```
curl -G -d "videoUrl=http://demo.castlabs.com/tmp/text0.mp4" http://localhost:8080/api/v1/analyze-mp4 
```

Format the `curl` JSON response by installing `json` (`npm i -g json`)
```
curl -G -d "videoUrl=http://demo.castlabs.com/tmp/text0.mp4" http://localhost:8080/api/v1/analyze-mp4 | json 
```

## Run Tests

To launch your application's tests, run:

```
./mvnw verify
```

[JDK]: https://www.oracle.com/java/technologies/javase-downloads.html
[Docker]: https://www.docker.com/products/docker-desktop
