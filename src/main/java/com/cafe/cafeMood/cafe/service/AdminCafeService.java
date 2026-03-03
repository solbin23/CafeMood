package com.cafe.cafeMood.cafe.service;

import com.cafe.cafeMood.cafe.domain.Cafe;
import com.cafe.cafeMood.cafe.dto.request.AdminCafeCreateRequest;
import com.cafe.cafeMood.cafe.dto.request.AdminCafeUpdateRequest;
import com.cafe.cafeMood.cafe.dto.response.AdminCafeResponse;
import com.cafe.cafeMood.cafe.repo.CafeRepository;
import com.cafe.cafeMood.cafe.validation.AdminCafeValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AdminCafeService {

    private final CafeRepository cafeRepo;
    private final AdminCafeValidator adminCafeValidator;


    @Transactional
    public AdminCafeResponse createCafe(AdminCafeCreateRequest req) {
        return null;
    }

    @Transactional
    public AdminCafeResponse updateCafe(Long id, AdminCafeUpdateRequest req) {
        adminCafeValidator.validateUpdate(req);

        Cafe cafe = cafeRepo.findById(id)
                .orElseThrow(()-> new IllegalArgumentException("카페를 찾을 수 없습니다."));

        if (req.instagramUrl() != null) {
            throw new IllegalArgumentException("이미 존재하는 instagramUrl 입니다.");
        }

        if (req.name() != null) {
            cafe
        }
    }

}
