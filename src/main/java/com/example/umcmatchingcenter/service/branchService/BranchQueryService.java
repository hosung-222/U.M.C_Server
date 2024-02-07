package com.example.umcmatchingcenter.service.branchService;

import com.example.umcmatchingcenter.domain.Branch;
import com.example.umcmatchingcenter.repository.BranchRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class BranchQueryService {
    private static final int CURRENT_GENERATION = 5;

    private final BranchRepository branchRepository;

    public List<Branch> getGenerationBranch(){
        return branchRepository.findAllByGeneration(CURRENT_GENERATION);
    }
}
