FROM huxiaoningsworld/jdk8:1.0
COPY target/ssm-0.0.1-SNAPSHOT.jar /
WORKDIR /
CMD ["java","-jar","-Dspring.profiles.active=dev","ssm-0.0.1-SNAPSHOT.jar"]