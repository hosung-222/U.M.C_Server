package com.example.umcmatchingcenter.repository;

import com.example.umcmatchingcenter.domain.Uuid;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
@Repository
public interface UuidRepository extends JpaRepository<Uuid, Long> {
}
