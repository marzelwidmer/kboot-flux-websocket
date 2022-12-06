package ch.keepcalm.kbootwebsocket.service

import ch.keepcalm.kbootwebsocket.model.Event
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component
import java.util.concurrent.atomic.AtomicInteger

@Component
class EventGenerator (private val eventUnicastService: EventUnicastService) {

    private val counter: AtomicInteger = AtomicInteger(0)

    @Scheduled(initialDelay = 1000, fixedDelay = 1000)
    fun generateEvent() {
        val count: Int = counter.getAndIncrement()
        val event = Event("event", count)
        eventUnicastService.onNext(event)
    }
}
