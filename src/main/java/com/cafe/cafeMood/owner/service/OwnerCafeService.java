package com.cafe.cafeMood.owner.service;

import com.cafe.cafeMood.cafe.domain.cafe.Cafe;
import com.cafe.cafeMood.cafe.dto.request.CafeCreateRequest;
import com.cafe.cafeMood.cafe.dto.response.CafeResponse;
import com.cafe.cafeMood.cafe.repo.cafe.CafeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class OwnerCafeService {

    private final CafeRepository cafeRepository;

    @Transactional
    public CafeResponse createMyCafe(Long ownerId, CafeCreateRequest request) {
        Cafe cafe = Cafe.create(
                ownerId,
                request.name(),
                request.address(),
                request.phoneNumber(),
                request.shortDesc()
        );
        return CafeResponse.from(cafeRepository.save(cafe));
    }
}
