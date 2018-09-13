# Docker

Sample project for [Ktor](http://ktor.io) running as an application
inside [Docker](https://www.docker.com/).

## Running

Execute this command in the repository's root directory to run this sample:

To build and run a docker image:

```bash
gradlew build
docker build -t ktor-sample-backend .
docker run -m512M --cpus 2 -it -p 8080:8080 --rm ktor-sample-backend
```
 
And navigate to [http://localhost:8080/](http://localhost:8080/) to see the sample home page.  
