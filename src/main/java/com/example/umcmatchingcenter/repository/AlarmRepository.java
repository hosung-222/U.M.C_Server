package com.example.umcmatchingcenter.repository;

import com.example.umcmatchingcenter.domain.Alarm;
import com.example.umcmatchingcenter.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AlarmRepository extends JpaRepository<Alarm, Long> {

    List<Alarm>  findAllByMember(Member member);
}
