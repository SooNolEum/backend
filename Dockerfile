# gradle:7.3.1-jdk17 이미지를 기반으로 함
FROM krmp-d2hub-idock.9rum.cc/goorm/gradle:8.2.1-jdk17

# 작업 디렉토리 설정
WORKDIR /root

# Spring 소스 코드를 이미지에 복사
COPY education-0.0.1-SNAPSHOT.jar /root

# gradle 빌드 시 proxy 설정을 gradle.properties에 추가
RUN echo "systemProp.http.proxyHost=krmp-proxy.9rum.cc\nsystemProp.http.proxyPort=3128\nsystemProp.https.proxyHost=krmp-proxy.9rum.cc\nsystemProp.https.proxyPort=3128" > /root/.gradle/gradle.properties

# RUN chmod +x gradlew

# gradlew를 이용한 프로젝트 필드
# RUN ./gradlew build

# 빌드 결과 jar 파일을 실행
CMD ["java", "-jar", "/root/education-0.0.1-SNAPSHOT.jar"]