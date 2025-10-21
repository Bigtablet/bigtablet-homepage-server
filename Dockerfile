FROM openjdk:21
ARG JAR_FILE=build/libs/*.jar
COPY ${JAR_FILE} app.jar
ENV TZ=Asia/Seoul
ENTRYPOINT ["java","-Duser.timezone=Asia/Seoul","-jar","app.jar"]
HEALTHCHECK --interval=30s --timeout=5s --retries=5 CMD curl -f http://127.0.0.1:8080/actuator/health || exit 1