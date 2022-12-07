package ch.keepcalm.kbootwebsocket.web

import ch.keepcalm.kbootwebsocket.model.Event
import ch.keepcalm.kbootwebsocket.service.EventUnicastService
import com.fasterxml.jackson.core.JsonProcessingException
import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.stereotype.Component
import org.springframework.web.reactive.socket.WebSocketHandler
import org.springframework.web.reactive.socket.WebSocketMessage
import org.springframework.web.reactive.socket.WebSocketSession
import reactor.core.publisher.Mono

@Component
class DefaultWebSocketHandler(private val eventUnicastService: EventUnicastService, private val objectMapper: ObjectMapper) : WebSocketHandler {

    override fun handle(session: WebSocketSession): Mono<Void> {

        val messages = session.receive() // .doOnNext(message -> { read message here or in the block below })

            .flatMap { message: WebSocketMessage? -> eventUnicastService.messages }
            .flatMap { o: Event? ->
                try {
                    return@flatMap Mono.just(objectMapper.writeValueAsString(o))
                } catch (e: JsonProcessingException) {
                    return@flatMap Mono.error<String>(e)
                }
            }.map { payload: String? -> session.textMessage(payload!!) }

        return session.send(messages)
    }
}
