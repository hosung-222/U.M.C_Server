package com.example.umcmatchingcenter.repository;

import com.example.umcmatchingcenter.domain.Branch;
import org.springframework.stereotype.Repository;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

@Repository
public class EmitterRepository {
    private final Map<String, SseEmitter> emitters = new ConcurrentHashMap<>();

    public SseEmitter save(String emitterId, SseEmitter sseEmitter) {
        emitters.put(emitterId, sseEmitter);
        return sseEmitter;
    }

    public Map<String, SseEmitter> findAllEmitterStartWithByMemberId(String memberName) {
        return emitters.entrySet().stream()
                .filter(entry -> entry.getKey().startsWith(memberName))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    public Map<String, SseEmitter> findAllEmitterEndWithBranchId(Branch branch) {
        return emitters.entrySet().stream()
                .filter(entry -> entry.getKey().endsWith(branch.getName()))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    public void deleteById(String emmiterId) {
        emitters.remove(emmiterId);
    }

    public void deleteAllEmitterStartWithId(String memberName) {
        emitters.forEach(
                (key, emitter) -> {
                    if (key.startsWith(memberName)) {
                        emitters.remove(key);
                    }
                }
        );
    }

}
