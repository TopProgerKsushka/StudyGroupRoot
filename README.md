# Развёртывание в Docker
```
$ git clone https://github.com/TopProgerKsushka/StudyGroupRoot.git

$ cd StudyGroupRoot

$ make

$ docker build -t labutina .

$ docker run --rm -p 8080:8092 -p 8500:8500 -p 8600:8600/udp -p 8761:8761 labutina

>>> Starting consul
>>> Starting WildFly with group service and EJB
>>> Starting Eureka
>>> Starting config server
>>> Starting isu instance 1
>>> Starting isu instance 2
>>> Starting isu instance 3
>>> Starting Zuul proxy
>>> Starting client application

>>> READY TO RUN <<<
```

## Запуск
http://localhost:8080/?skipsplash – приложение

http://localhost:8500/ – Consul

http://localhost:8761/ – Eureka


## Остальные порты
(необходимо дополнительно пробросить в `docker run -p`)

**8093** – group service

**8094** – isu service (через Zuul proxy, реальные порты инстансов выдаются рандомно)

**8888** – config server