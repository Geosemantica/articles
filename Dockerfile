FROM eclipse-temurin:21-jdk
WORKDIR /app

COPY web/target/web-1.0.0-SNAPSHOT.jar /app/article-service.jar

ENV JVM_PARAMS='-XX:+UseG1GC -Xmx1024m -Xms1024m'
EXPOSE 8090
CMD java $JVM_PARAMS -jar /app/article-service.jar