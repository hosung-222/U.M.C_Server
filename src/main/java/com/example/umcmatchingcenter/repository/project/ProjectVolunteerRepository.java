package com.example.umcmatchingcenter.repository.project;

import com.example.umcmatchingcenter.domain.mapping.ProjectVolunteer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProjectVolunteerRepository extends JpaRepository<ProjectVolunteer, Long> {

    Optional<ProjectVolunteer> findByMemberId(Long memberId);

}
