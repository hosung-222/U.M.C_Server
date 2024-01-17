package com.example.umcmatchingcenter.repository;

import com.example.umcmatchingcenter.domain.Alarm;
import com.example.umcmatchingcenter.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AlarmRepository extends JpaRepository<Alarm, Long> {

    List<Alarm>  findAllByMember(Member member);

    @Modifying
    @Query("update Alarm a set a.isConfirmed = 1 where a.member = :member")
    int updateAlarmIsConfirmed(@Param("member") Member member);

}
