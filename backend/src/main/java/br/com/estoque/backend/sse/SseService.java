package br.com.estoque.backend.sse;

import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

@Service
public class SseService {

    private final List<SseEmitter> emitters = new CopyOnWriteArrayList<>();

    public void addEmitter(SseEmitter sseEmitter){
        emitters.add(sseEmitter);
        sseEmitter.onCompletion(()-> emitters.remove(sseEmitter));
        sseEmitter.onTimeout(()-> emitters.remove(sseEmitter));
        sseEmitter.onError(e-> emitters.remove(sseEmitter));
    }

    public void sendEvents(String event,Object object){
        List<SseEmitter> deadEmitters = new ArrayList<>();
        for(SseEmitter emitter : emitters){
            try {
                System.out.println("ENVIANDO");
                emitter.send(SseEmitter.event().name(event).data(object));
                System.out.println("ENVIANDO"+object);
            } catch (IOException e) {
                emitter.complete();
                emitters.remove(emitter);
                deadEmitters.add(emitter);
            }
        }
        emitters.removeAll(deadEmitters);
    }

}
