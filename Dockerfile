# 1º Stage 
FROM 751178672067.dkr.ecr.us-east-2.amazonaws.com/maven:3.8.3-adoptopenjdk-11 as builder
COPY ./ /app
WORKDIR /app
RUN mvn clean package

#2º Stage 
FROM 751178672067.dkr.ecr.us-east-2.amazonaws.com/adoptopenjdk:11-jre-hotspot
# ARG JAR_FILE=target/*.jar
COPY --from=builder /app/target/*.jar com.educacional.applicationsms.application.jar
EXPOSE 8080 8081 8082 8083
# ENTRYPOINT ["java", "-jar", "com.educacional.applicationsms.application.jar"]
CMD java -jar com.educacional.applicationsms.application.jar