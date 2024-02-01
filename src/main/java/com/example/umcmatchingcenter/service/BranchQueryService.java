package com.example.umcmatchingcenter.service;

import com.example.umcmatchingcenter.apiPayload.code.status.ErrorStatus;
import com.example.umcmatchingcenter.apiPayload.exception.handler.MemberHandler;
import com.example.umcmatchingcenter.domain.Branch;
import com.example.umcmatchingcenter.repository.BranchRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class BranchQueryService {

    private final BranchRepository branchRepository;

    public Branch findBranchByName(String name){
        return branchRepository.findByName(name)
                .orElseThrow(() -> new MemberHandler(ErrorStatus.BRANCH_NOT_FOUND));
    }
}
