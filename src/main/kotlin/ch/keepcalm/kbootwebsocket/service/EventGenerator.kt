package ch.keepcalm.kbootwebsocket.service

import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux
import reactor.core.publisher.Sinks
import reactor.util.concurrent.Queues
import java.util.concurrent.atomic.AtomicInteger


data class Event constructor(val name: String, val count: Int)

@Component
class EventGenerator(private val counterEventService: CounterEventService) {

    private val counter: AtomicInteger = AtomicInteger(0)

    @Scheduled(initialDelay = 1000, fixedDelay = 1000)
    fun generateEvent() {
        counterEventService.addEvent(Event("event", counter.getAndIncrement()))
    }
}


@Service
class CounterEventService {

    private var sink: Sinks.Many<Event> = Sinks.many().multicast().onBackpressureBuffer<Event>(Queues.SMALL_BUFFER_SIZE, false)

    fun addEvent(event: Event) {
        sink.tryEmitNext(event)
    }

    val messages: Flux<Event?>
        get() = sink.asFlux()
}
