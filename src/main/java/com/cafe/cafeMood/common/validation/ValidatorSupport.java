package com.cafe.cafeMood.common.validation;

import java.util.ArrayList;
import java.util.List;

public abstract class ValidatorSupport {
    private final List<FieldError> errors = new ArrayList<>();

    protected void clear() {
        errors.clear();
    }

    protected void require(boolean condition, String field, String message) {
        if(!condition) {
            errors.add(new FieldError(field, message));
        }
    }

    protected  boolean isBlank(String str) {
        return str == null || str.trim().isEmpty();
    }

    protected void throwIfInvalid(){
        if(!errors.isEmpty()) {
            throw new ValidationException(List.copyOf(errors));
        }
    }
}
