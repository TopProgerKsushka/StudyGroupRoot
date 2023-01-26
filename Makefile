build:
	@chmod +x ./gradlew
	@./gradlew client:bootJar group:ejb:jar group:service:bootWar isu:eureka:bootJar isu:instance:bootJar isu:zuul:bootJar isu:config:bootJar

clean:
	@./gradlew clean
