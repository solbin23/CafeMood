package com.cafe.cafeMood.cafe.dto.request;

import java.util.List;

public record AdminCafeTagsRequest(
        List<Long> tagId
) {
    public  AdminCafeTagsRequest {
        if(tagId == null) tagId = List.of(); {

        }
    }
}
