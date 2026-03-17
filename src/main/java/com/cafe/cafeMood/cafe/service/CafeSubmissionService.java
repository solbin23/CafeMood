package com.cafe.cafeMood.cafe.service;


import com.cafe.cafeMood.cafe.domain.submission.CafeSubmission;
import com.cafe.cafeMood.cafe.dto.request.cafe.CafeSubmissionCreateRequest;
import com.cafe.cafeMood.cafe.dto.response.cafe.CafeSubmissionResponse;
import com.cafe.cafeMood.cafe.repo.submission.CafeSubmissionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CafeSubmissionService {

    public final CafeSubmissionRepository cafeSubmissionRepo;

    @Transactional
    public CafeSubmissionResponse createSubmission(Long ownerUserId, CafeSubmissionCreateRequest req) {
        if(ownerUserId == null ||  ownerUserId <= 0) {
            throw new IllegalArgumentException("login required");
        }

        if (req.name() == null || req.name().trim().isEmpty()) {
            throw new IllegalArgumentException("name required");
        }

        if (req.address() == null || req.address().trim().isEmpty()) {
            throw new IllegalArgumentException("address required");
        }

        CafeSubmission cafeSubmission = CafeSubmission.create(
                ownerUserId,
                req.name(),
                req.shortDesc(),
                req.address()
        );

        CafeSubmission saved = cafeSubmissionRepo.save(cafeSubmission);
        return CafeSubmissionResponse.from(saved);
    }


    @Transactional(readOnly = true)
    public List<CafeSubmissionResponse> getMySubmissions(Long ownerUserId) {
        if (ownerUserId == null || ownerUserId <= 0) {
            throw new IllegalArgumentException("login required");
        }
        return cafeSubmissionRepo.findByOwnerUserIdOrderByIdDesc(ownerUserId)
                .stream()
                .map(CafeSubmissionResponse::from)
                .toList();
    }
}

