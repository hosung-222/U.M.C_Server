package com.example.umcmatchingcenter.service;

import com.example.umcmatchingcenter.domain.Uuid;
import com.example.umcmatchingcenter.repository.UuidRepository;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class UuidService {

    private final UuidRepository uuidRepository;

    public Uuid makeUuid(){
        String randomUuid = UUID.randomUUID().toString();
        Uuid savedUuid = Uuid.builder()
                .uuid(randomUuid)
                .build();
        return uuidRepository.save(savedUuid);
    }
}
