package com.cafe.cafeMood.cafe.dto.response;

import com.cafe.cafeMood.cafe.domain.submission.CafeSubmission;
import com.cafe.cafeMood.cafe.domain.submission.CafeSubmissionStatus;

import java.time.Instant;

public record CafeSubmissionResponse(Long id,
                                     Long ownerUserId,
                                     String name,
                                     String shortDesc,
                                     String address,
                                     CafeSubmissionStatus status,
                                     String reviewComment,
                                     Long approvedCafeId,
                                     Instant createDate,
                                     Instant updateDate) {

    public static CafeSubmissionResponse from(CafeSubmission submission) {
        return new CafeSubmissionResponse(
                submission.getId(),
                submission.getOwnerUserId(),
                submission.getName(),
                submission.getShortDesc(),
                submission.getAddress(),
                submission.getStatus(),
                submission.getReviewComment(),
                submission.getApprovedCafeId(),
                submission.getCreateDate(),
                submission.getUpdateDate()
        );
    }
}
