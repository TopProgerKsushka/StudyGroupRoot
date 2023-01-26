FROM archlinux:latest

WORKDIR /root

RUN mkdir /root/app && mkdir /root/logs
RUN pacman -Sy --noconfirm consul jre11-openjdk jre17-openjdk
RUN curl -sL https://github.com/wildfly/wildfly/releases/download/27.0.1.Final/wildfly-27.0.1.Final.tar.gz | tar -xz

COPY ./client/build/libs/client.jar ./isu/eureka/build/libs/isu-eureka.jar ./isu/config/build/libs/isu-config.jar ./isu/instance/build/libs/isu-instance.jar ./isu/zuul/build/libs/isu-zuul.jar /root/app/
COPY ./wildfly-config/* /root/wildfly-27.0.1.Final/standalone/configuration/
COPY ./group/ejb/build/libs/ejb.jar ./group/service/build/libs/group.war /root/wildfly-27.0.1.Final/standalone/deployments/
COPY ./docker/launch.sh /root/

CMD /bin/bash /root/launch.sh