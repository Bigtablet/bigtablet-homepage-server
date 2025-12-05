FROM eclipse-temurin:25-jdk
ARG JAR_FILE=build/libs/*.jar
COPY ${JAR_FILE} /app.jar
ENV TZ=Asia/Seoul
ENV JAVA_TOOL_OPTIONS="-Duser.timezone=Asia/Seoul -Dfile.encoding=UTF-8"
ENTRYPOINT ["java","-jar","/app.jar"]
HEALTHCHECK --interval=30s --timeout=5s --retries=5 CMD curl -f http://127.0.0.1:8080/actuator/health || exit 1