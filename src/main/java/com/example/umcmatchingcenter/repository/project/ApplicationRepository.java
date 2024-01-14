package com.example.umcmatchingcenter.repository.project;

import com.example.umcmatchingcenter.domain.Project;
import com.example.umcmatchingcenter.domain.mapping.Application;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ApplicationRepository extends JpaRepository<Application, Long> {

    Optional<Application> findByMember_Id(Long memberId);

}
