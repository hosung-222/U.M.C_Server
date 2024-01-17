package com.example.umcmatchingcenter.repository;

import com.example.umcmatchingcenter.domain.Alarm;
import com.example.umcmatchingcenter.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AlarmRepository extends JpaRepository<Alarm, Long> {

    List<Alarm> findAllByMember(Member member);

    @Modifying(clearAutomatically = true)
    @Query("update Alarm a set a.isConfirmed = 1 where a.member = :member")
    void updateAlarmIsConfirmed(@Param("member") Member member);

    @Modifying(clearAutomatically = true)
    @Query("delete from Alarm a where a.member = :member and a.isConfirmed = true ")
    int deleteAllByIds(@Param("member") Member member);
}
