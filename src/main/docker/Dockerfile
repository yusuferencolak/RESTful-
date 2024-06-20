FROM tomcat:10.1.23-jdk21-temurin-jammy

ADD .war /D:/apache-tomcat-10.1.24/apache-tomcat-10.1.24/webapps

ENV SUTTON_DB_HOST_PORT="localhost:8080"

EXPOSE 8080

CMD ["catalina.sh", "run"]
