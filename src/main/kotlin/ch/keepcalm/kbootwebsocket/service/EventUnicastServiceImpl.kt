package ch.keepcalm.kbootwebsocket.service

import ch.keepcalm.kbootwebsocket.model.Event
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux
import reactor.core.publisher.Sinks

@Service
class EventUnicastServiceImpl : EventUnicastService {

//    private val processor: EmitterProcessor<Event> = EmitterProcessor.create<Event>()
    private val processor: Sinks.Many<Event>  = Sinks.many().multicast().onBackpressureBuffer()

    override fun onNext(next: Event?) {
        next?.let {
            processor.tryEmitNext(it)
        }
    }

    override val messages: Flux<Event?>
//        get() = processor.publish().autoConnect()
        get() = processor.asFlux()

}
