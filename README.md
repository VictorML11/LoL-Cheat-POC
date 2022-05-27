# LoL-Cheat-POC
Multi infrastructure POC using C++ Kafka and Java with LibGDX as UI

This is a POC about reading memory of league of legends with c++ and sending the data through kafka to a Java Spring Service which has LibGDX integrated
to do the drawings and the logic of cheats

It is very important to tune the producers and consumers to reach a high throughput otherwhise we wouldn't have the data on real time.
The first main issue  that i found is that. The LibGdx is printing 144 FPS and the producer was slower than the prints, that meant that the data that was not present and not printed
causing a miss sync with the game.

The second issue to address is the throughput speed of producer. For instance, if the throughput of producer is very huge and the consumer can not read all the data in time
it causes lag... The principal challenge here is to find the balance.

Topics used: prueba & heroes

Using docker images: https://github.com/streamthoughts/kafka-monitoring-stack-docker-compose.git

This is an example of the KPIs

![image](https://user-images.githubusercontent.com/26772237/170611961-b1646f66-ec79-4bab-a4f3-8ca8b456f4b5.png)


