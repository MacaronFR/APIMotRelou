ARG VERSION=19.0.1

FROM amazoncorretto:${VERSION} as BUILD

COPY . /src
WORKDIR /src
RUN ./gradlew --no-daemon shadowJar

FROM amazoncorretto:${VERSION}

COPY --from=BUILD /src/build/libs/motrelou-2.0-all.jar /bin/runner/run.jar
WORKDIR /bin/runner

CMD ["java","-jar","run.jar"]