package ch.keepcalm.kbootwebsocket.service

import ch.keepcalm.kbootwebsocket.model.Event
import org.springframework.stereotype.Service
import reactor.core.publisher.EmitterProcessor
import reactor.core.publisher.Flux

@Service
class EventUnicastServiceImpl : EventUnicastService {

    private val processor: EmitterProcessor<Event> = EmitterProcessor.create<Event>()

    override fun onNext(next: Event?) {
        next?.let { processor.onNext(it) }
    }

    override val messages: Flux<Event?>
        get() = processor.publish().autoConnect()
}
