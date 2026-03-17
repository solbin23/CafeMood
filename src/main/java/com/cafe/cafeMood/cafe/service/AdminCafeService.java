package com.cafe.cafeMood.cafe.service;

import com.cafe.cafeMood.cafe.domain.cafe.Cafe;
import com.cafe.cafeMood.cafe.domain.submission.CafeSubmission;
import com.cafe.cafeMood.cafe.dto.request.AdminCafeCreateRequest;
import com.cafe.cafeMood.cafe.dto.request.AdminCafeUpdateRequest;
import com.cafe.cafeMood.cafe.dto.response.AdminCafeResponse;
import com.cafe.cafeMood.cafe.dto.response.cafe.CafeSubmissionResponse;
import com.cafe.cafeMood.cafe.repo.cafe.CafeRepository;
import com.cafe.cafeMood.cafe.repo.submission.CafeSubmissionRepository;
import com.cafe.cafeMood.cafe.validation.AdminCafeValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AdminCafeService {

    private final CafeRepository cafeRepo;

}
