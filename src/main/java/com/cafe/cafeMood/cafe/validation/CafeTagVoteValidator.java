package com.cafe.cafeMood.cafe.validation;

import com.cafe.cafeMood.common.validation.ValidatorSupport;
import org.springframework.stereotype.Component;

@Component
public class CafeTagVoteValidator extends ValidatorSupport {

    public void validateCreate(Long cafeId, Long tagId){
        clear();

        require(cafeId != null, "cafeId", "is required");
        require(tagId != null, "tagId", "is required");


        require(cafeId > 0,"cafeId", "must be positive");
        require(tagId > 0,"tagId", "must be positive");

            throwIfInvalid();

    }
}
