FROM adoptopenjdk/openjdk12:latest

# add directly the jar
ADD target/*.jar /app.jar

# to create a modification date
RUN sh -c 'touch /app.jar'

# creates a mount point
VOLUME /tmp

# sets timezone
ENV TZ=America/Sao_Paulo
RUN ln -snf /usr/share/zoneinfo/$TZ /etc/localtime && echo $TZ > /etc/timezone

CMD ["java", "-jar", "/app.jar"]

EXPOSE 8081