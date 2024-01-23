package com.example.umcmatchingcenter.validation.validator;

import com.example.umcmatchingcenter.apiPayload.code.status.ErrorStatus;
import com.example.umcmatchingcenter.domain.Project;
import com.example.umcmatchingcenter.domain.enums.ProjectStatus;
import com.example.umcmatchingcenter.service.ProjectService.ProjectQueryService;
import com.example.umcmatchingcenter.validation.annotation.CompleteProject;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class ProjectCompleteValidator implements ConstraintValidator<CompleteProject, Long> {

    private final ProjectQueryService projectQueryService;

    @Override
    public void initialize(CompleteProject constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(Long projectId, ConstraintValidatorContext context) {
        Optional<Project> target = Optional.ofNullable(projectQueryService.findCompleteProject(projectId, ProjectStatus.COMPLETE));

        if (target.isEmpty()){
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(ErrorStatus.PROJECT_NOT_COMPLETE.toString()).addConstraintViolation();
            return false;
        }
        return true;
    }
}