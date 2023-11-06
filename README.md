# MP4 Analyser
CastLabs

## Running

We can run this project using Docker or by installing Java JDK 17.

#### Docker way

[Docker][]: Download and install Docker.

In project root directory run:

```
docker build -t mp4analyzer . && docker run -p 8080:8080 --name mp4analyzer mp4analyzer
```

#### Running the project without Docker

1. [JDK][]: Download and install Java JDK 17.

After installing Java run the project using the following command:

```

./mvnw spring-boot:run

```

## Executing Requests

App is running in port 8080.

Mp4 Analyzer endpoint is:

```
GET: http://localhost:8080/api/v1/analyze-mp4?videoUrl={your-url}
```

Replace `{your-url}` with your video url.

* Running using web browser
```
http://localhost:8080/api/v1/analyze-mp4?videoUrl=http://demo.castlabs.com/tmp/text0.mp4
```
* Running using `curl`
```
curl -G -d "videoUrl={your-url} http://localhost:8080/api/v1/analyze-mp4 
```
Example:
```
curl -G -d "videoUrl=http://demo.castlabs.com/tmp/text0.mp4 http://localhost:8080/api/v1/analyze-mp4 
```

Format curl json response by installing json `(npm i -g json)`
```
curl -G -d "videoUrl=http://demo.castlabs.com/tmp/text0.mp4 http://localhost:8080/api/v1/analyze-mp4 | json 
```

## Run Tests

To launch your application's tests, run:

```
./mvnw verify
```

[JDK]: https://www.oracle.com/java/technologies/javase-downloads.html
[Docker]: https://www.docker.com/products/docker-desktop
