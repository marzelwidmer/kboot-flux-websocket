package ch.keepcalm.kbootwebsocket.service

import ch.keepcalm.kbootwebsocket.model.Event
import reactor.core.publisher.Flux


interface EventUnicastService {
    fun onNext(next: Event?)
    val messages: Flux<Event?>
}
