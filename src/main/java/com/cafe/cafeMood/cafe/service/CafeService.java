package com.cafe.cafeMood.cafe.service;


import com.cafe.cafeMood.cafe.domain.cafe.Cafe;
import com.cafe.cafeMood.cafe.domain.cafe.CafeStatus;
import com.cafe.cafeMood.cafe.dto.response.CafeResponse;
import com.cafe.cafeMood.cafe.repo.cafe.CafeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CafeService {
    private final CafeRepository cafeRepo;

    public List<CafeResponse> getPublishedCafe() {
        List<Cafe> cafes = cafeRepo.findByStatusOrderByIdDesc(CafeStatus.PUBLISHED);
        return cafes.stream().map(CafeResponse::from)
                .toList();
    }

    public CafeResponse getCafe(Long cafeId){

        if(cafeId == null || cafeId <= 0) {
            throw new IllegalArgumentException("cafeId is null or empty");
        }
        Cafe cafe = cafeRepo.findById(cafeId)
                .orElseThrow(()-> new IllegalArgumentException("cafe not found"));

        if (cafe.getStatus() != CafeStatus.PUBLISHED) {
            throw new IllegalArgumentException("cafe is not published");
        }

        return CafeResponse.from(cafe);
    }
}
