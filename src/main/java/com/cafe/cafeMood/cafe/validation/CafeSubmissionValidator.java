package com.cafe.cafeMood.cafe.validation;

import com.cafe.cafeMood.cafe.dto.request.cafe.CafeSubmissionCreateRequest;
import com.cafe.cafeMood.common.validation.ValidatorSupport;
import org.springframework.stereotype.Component;

@Component
public class CafeSubmissionValidator extends ValidatorSupport {

    public void validateCreate(CafeSubmissionCreateRequest req){
        clear();

        require(req != null, "request","is requested");

        if (req == null) {
            throwIfInvalid();
            return;
        }

        require(req.name() != null && !req.name().trim().isEmpty(), "name","is required");
        require(req.address() != null && !req.address().trim().isEmpty(), "address","is required");

        throwIfInvalid();
    }
}
