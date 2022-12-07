package ch.keepcalm.kbootwebsocket

import ch.keepcalm.kbootwebsocket.service.CounterEventService
import ch.keepcalm.kbootwebsocket.service.Event
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.http.MediaType
import org.springframework.http.codec.ServerSentEvent
import org.springframework.scheduling.annotation.EnableScheduling
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Flux
import java.time.Duration
import java.time.LocalTime


@SpringBootApplication
@EnableScheduling
class KbootWebsocketApplication

fun main(args: Array<String>) {
    runApplication<KbootWebsocketApplication>(*args)
}


@RestController
class SSEcontroller(private val service: CounterEventService) {

    @GetMapping("/stream-sse", produces = [MediaType.TEXT_EVENT_STREAM_VALUE])
    fun streamSSE(): Flux<ServerSentEvent<String>>? {
        return Flux.interval(Duration.ofSeconds(1))
            .map { sequence: Long ->
                ServerSentEvent.builder<String>()
                    .id(sequence.toString())
                    .event("periodic-event")
                    .data("SSE - " + LocalTime.now().toString())
                    .build()
            }
    }

    @GetMapping("/stream-sse-events", produces = [MediaType.TEXT_EVENT_STREAM_VALUE])
    fun streamEvents(): Flux<ServerSentEvent<String>>? {

        return Flux.interval(Duration.ofSeconds(1))
            .map { sequence: Long ->
                ServerSentEvent.builder<String>()
                    .id(sequence.toString())
                    .event("periodic-event")
                    .data("SSE - " + LocalTime.now().toString())
                    .build()
            }
    }
}
