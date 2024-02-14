package com.example.umcmatchingcenter.repository;

import com.example.umcmatchingcenter.domain.MatchingSchedule;
import com.example.umcmatchingcenter.domain.Member;
import com.example.umcmatchingcenter.domain.Project;
import com.example.umcmatchingcenter.domain.mapping.ProjectVolunteer;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProjectVolunteerRepository extends JpaRepository<ProjectVolunteer, Long> {

    List<ProjectVolunteer> findAllByMemberOrderByRound(Member member);

    Optional<ProjectVolunteer> findByMemberId(Long memberId);

    Optional<ProjectVolunteer> findByMemberAndProject(Member member, Project project);

}
