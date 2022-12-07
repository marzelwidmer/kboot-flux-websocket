package ch.keepcalm.kbootwebsocket.web

import ch.keepcalm.kbootwebsocket.service.Event
import ch.keepcalm.kbootwebsocket.service.CounterEventService
import com.fasterxml.jackson.core.JsonProcessingException
import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.stereotype.Component
import org.springframework.web.reactive.socket.WebSocketHandler
import org.springframework.web.reactive.socket.WebSocketMessage
import org.springframework.web.reactive.socket.WebSocketSession
import reactor.core.publisher.Mono

@Component
class DefaultWebSocketHandler(private val counterEventService: CounterEventService, private val objectMapper: ObjectMapper) : WebSocketHandler {

    override fun handle(session: WebSocketSession): Mono<Void> {
        val messages = session.receive() // .doOnNext(message -> { read message here or in the block below })
            .flatMap { message: WebSocketMessage? -> counterEventService.messages }
            .flatMap { event: Event? ->
                try {
                    return@flatMap Mono.just(objectMapper.writeValueAsString(event))
                } catch (exception: JsonProcessingException) {
                    return@flatMap Mono.error<String>(exception)
                }
            }.mapNotNull { payload: String? -> payload?.let { session.textMessage(it) } }

        return session.send(messages)
    }
}
