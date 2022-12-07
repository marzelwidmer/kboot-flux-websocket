package ch.keepcalm.kbootwebsocket.web

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.reactive.HandlerAdapter
import org.springframework.web.reactive.HandlerMapping
import org.springframework.web.reactive.handler.SimpleUrlHandlerMapping
import org.springframework.web.reactive.socket.WebSocketHandler
import org.springframework.web.reactive.socket.server.support.WebSocketHandlerAdapter
import java.util.Map

@Configuration
class WebSocketConfig @Autowired constructor(private val webSocketHandler: WebSocketHandler) {

    @Bean
    fun handlerMapping(): HandlerMapping {
        val path = "/push"
        val map = Map.of(path, webSocketHandler)
        return SimpleUrlHandlerMapping(map, -1)
    }


    @Bean
    fun fooBarHandlerMapping(): HandlerMapping {
        val path = "/foobar"
        val map = Map.of(path, webSocketHandler)
        return SimpleUrlHandlerMapping(map, -1)
    }


    @Bean
    fun wsHandlerAdapter(): HandlerAdapter {
        return WebSocketHandlerAdapter()
    }
}
