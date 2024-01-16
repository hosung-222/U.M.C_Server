package com.example.umcmatchingcenter.repository;

import com.example.umcmatchingcenter.domain.Alarm;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AlarmRepository extends JpaRepository<Alarm, Long> {

}
