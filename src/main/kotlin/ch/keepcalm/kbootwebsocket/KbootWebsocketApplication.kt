package ch.keepcalm.kbootwebsocket

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.scheduling.annotation.EnableScheduling

@SpringBootApplication
@EnableScheduling
class KbootWebsocketApplication

fun main(args: Array<String>) {
	runApplication<KbootWebsocketApplication>(*args)
}
