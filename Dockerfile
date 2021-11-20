FROM openjdk:17.0.1-jdk
EXPOSE 8083
ARG JAR_FILE=target/*.jar
COPY elastic-apm-agent-1.27.0.jar apm-agent.jar
COPY ${JAR_FILE} app.jar
ENTRYPOINT [ "java", \
             "-javaagent:/apm-agent.jar", \
             "-Delastic.apm.service_name=crypto-auth-app", \
             "-Delastic.apm.server_urls=http://apm-server:8200", \
             "-Delastic.apm.application_packages=com.crypto", \
             "-jar", \
             "-Dspring.profiles.active=docker", \
             "/app.jar"]