package com.example.umcmatchingcenter.validation.validator;

import com.example.umcmatchingcenter.apiPayload.code.status.ErrorStatus;
import com.example.umcmatchingcenter.domain.Member;
import com.example.umcmatchingcenter.service.memberService.MemberQueryService;
import com.example.umcmatchingcenter.validation.annotation.ExistMember;
import java.util.Optional;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MemberExistsValidator implements ConstraintValidator<ExistMember, Long> {

    private final MemberQueryService memberQueryService;

    @Override
    public void initialize(ExistMember constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(Long value, ConstraintValidatorContext context) {
        Member target = memberQueryService.findMember(value);

//        if (target.isEmpty()){
//            context.disableDefaultConstraintViolation();
//            context.buildConstraintViolationWithTemplate(ErrorStatus.MEMBER_NOT_FOUND.toString()).addConstraintViolation();
//            return false;
//        }
        return true;
    }
}
