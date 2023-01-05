FROM ubuntu:22.04

# Make sure the package repository is up to date.
RUN apt-get update && \
    apt-get install -qy git && \
    apt-get install -y wget && \
# Install JDK 17
    apt-get install -qy openjdk-17-jdk
# Install Maven 3.8.6
RUN  wget https://dlcdn.apache.org/maven/maven-3/3.8.7/binaries/apache-maven-3.8.7-bin.tar.gz -P /tmp && \
    tar xf /tmp/apache-maven-*.tar.gz -C /opt && \
    ln -s /opt/apache-maven-3.8.7 /opt/maven


#Enviornment variables for Maven and java
ENV JAVA_HOME=/usr/lib/jvm/java-1.17.0-openjdk-amd64
ENV M2_HOME=/opt/maven
ENV MAVEN_HOME=/opt/maven
ENV PATH=${M2_HOME}/bin:${PATH}

COPY . .

RUN mvn clean install

WORKDIR ./backend/target/

RUN cp ../src/main/resources/static/serbian-latin.txt .

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "./backend-0.0.1-SNAPSHOT.jar"]

