echo '>>> Starting consul'
(consul agent -dev -client=0.0.0.0 > /root/logs/consul.log 2>&1 &)
tail -f -n0 /root/logs/consul.log | grep -qe 'successfully established leadership'

echo '>>> Starting WildFly with group service and EJB'
(PATH="/usr/lib/jvm/java-17-openjdk/bin:$PATH" /bin/bash /root/wildfly-27.0.1.Final/bin/standalone.sh > /root/logs/wildfly.log 2>&1 &)
tail -f -n0 /root/logs/wildfly.log | grep -qe 'serviceName=group.war'

echo '>>> Starting Eureka'
(/usr/lib/jvm/java-11-openjdk/bin/java -jar /root/app/isu-eureka.jar > /root/logs/isu-eureka.log 2>&1 &)
tail -f -n0 /root/logs/isu-eureka.log | grep -qe 'Started EurekaServerApplication'

echo '>>> Starting config server'
(/usr/lib/jvm/java-11-openjdk/bin/java -jar /root/app/isu-config.jar > /root/logs/isu-config.log 2>&1 &)
tail -f -n0 /root/logs/isu-config.log | grep -qe 'Started ConfigServerApplication'

echo '>>> Starting isu instance 1'
(/usr/lib/jvm/java-11-openjdk/bin/java -jar /root/app/isu-instance.jar > /root/logs/isu-instance-1.log 2>&1 &)
tail -f -n0 /root/logs/isu-instance-1.log | grep -qe 'Started IsuApplication'

echo '>>> Starting isu instance 2'
(/usr/lib/jvm/java-11-openjdk/bin/java -jar /root/app/isu-instance.jar > /root/logs/isu-instance-2.log 2>&1 &)
tail -f -n0 /root/logs/isu-instance-2.log | grep -qe 'Started IsuApplication'

echo '>>> Starting isu instance 3'
(/usr/lib/jvm/java-11-openjdk/bin/java -jar /root/app/isu-instance.jar > /root/logs/isu-instance-3.log 2>&1 &)
tail -f -n0 /root/logs/isu-instance-3.log | grep -qe 'Started IsuApplication'

sleep 5

echo '>>> Starting Zuul proxy'
(/usr/lib/jvm/java-11-openjdk/bin/java -jar /root/app/isu-zuul.jar > /root/logs/isu-zuul.log 2>&1 &)
tail -f -n0 /root/logs/isu-zuul.log | grep -qe 'Started ZuulProxyApplication'


(/usr/lib/jvm/java-17-openjdk/bin/java -jar /root/app/client.jar > /root/logs/client.log 2>&1 &)
tail -f -n0 /root/logs/client.log | grep -qe 'Started ClientApplication'
sleep 5
echo
echo '>>> READY TO RUN <<<'

tail -f /dev/null
