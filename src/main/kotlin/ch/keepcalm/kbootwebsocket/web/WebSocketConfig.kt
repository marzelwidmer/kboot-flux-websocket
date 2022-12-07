package ch.keepcalm.kbootwebsocket.web

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.reactive.HandlerAdapter
import org.springframework.web.reactive.HandlerMapping
import org.springframework.web.reactive.handler.SimpleUrlHandlerMapping
import org.springframework.web.reactive.socket.WebSocketHandler
import org.springframework.web.reactive.socket.server.support.WebSocketHandlerAdapter

@Configuration
class WebSocketConfig(private val webSocketHandler: WebSocketHandler) {

    @Bean
    fun handlerMapping(): HandlerMapping {
        val map = mapOf("/counter" to webSocketHandler)
        return SimpleUrlHandlerMapping(map, -1)
    }

    @Bean
    fun wsHandlerAdapter(): HandlerAdapter {
        return WebSocketHandlerAdapter()
    }
}
