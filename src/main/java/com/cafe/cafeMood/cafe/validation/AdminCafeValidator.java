package com.cafe.cafeMood.cafe.validation;

import com.cafe.cafeMood.cafe.dto.request.AdminCafeCreateRequest;
import com.cafe.cafeMood.cafe.dto.request.AdminCafeUpdateRequest;
import com.cafe.cafeMood.common.validation.ValidatorSupport;
import org.springframework.stereotype.Component;

@Component
public class AdminCafeValidator extends ValidatorSupport {
    public void validateCreate(AdminCafeCreateRequest req) {
        clear();

        if (req.shortDesc() != null) {
            require(!isBlank(req.shortDesc()),"shortDesc","필수입력 값 입니다.");
        }

        throwIfInvalid();
    }

    public void validateUpdate(AdminCafeUpdateRequest req) {
        clear();

        //정책 : null -> 변경 없음
        //정책 : 전달되면 blank 금지
        rejectBlankIfPresent(req.name(),"name");
        rejectBlankIfPresent(req.shortDesc(),"shortDesc");

        throwIfInvalid();

    }

    private void rejectBlankIfPresent(String value, String field){
        if (value != null) {
            require(!isBlank(value), field,"must not be blank");
        }

    }

}
