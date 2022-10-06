FROM java:8
RUN echo "Asia/Taipei" > /etc/timezone
RUN dpkg-reconfigure -f noninteractive tzdata
RUN mkdir /deploy
WORKDIR /deploy

ARG PROFILE_NAME_ARG
ARG BUILD_JAR_NAME

ENV PROFILE_NAME=${PROFILE_NAME_ARG}

ADD ${BUILD_JAR_NAME}.jar app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]