package com.cafe.cafeMood.cafe.service;


import com.cafe.cafeMood.cafe.domain.cafe.Cafe;
import com.cafe.cafeMood.cafe.domain.cafe.CafeStatus;
import com.cafe.cafeMood.cafe.dto.request.CafeCreateRequest;
import com.cafe.cafeMood.cafe.dto.request.CafeUpdateRequest;
import com.cafe.cafeMood.cafe.dto.response.CafeResponse;
import com.cafe.cafeMood.cafe.repo.cafe.CafeRepository;
import com.cafe.cafeMood.common.exception.BusinessException;
import com.cafe.cafeMood.common.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class OwnerCafeService {
    private final CafeRepository cafeRepository;

    @Transactional
    public CafeResponse createCafe(Long ownerId,CafeCreateRequest request) {
        Cafe cafe = Cafe.create(
                ownerId,
                request.name(),
                request.address(),
                request.phoneNumber(),
                request.shortDesc()
        );
        Cafe savedCafe = cafeRepository.save(cafe);
        return CafeResponse.from(savedCafe);
    }

    public CafeResponse getCafe(Long cafeId){
        Cafe cafe = cafeRepository.findByIdAndStatusNot(cafeId, CafeStatus.DELETED)
                .orElseThrow(()-> new BusinessException(ErrorCode.CAFE_NOT_FOUND));

        return CafeResponse.from(cafe);
    }

    @Transactional
    public CafeResponse updateCafe(Long cafeId, CafeUpdateRequest request) {
        Cafe cafe = cafeRepository.findByIdAndStatusNot(cafeId, CafeStatus.DELETED)
                .orElseThrow(()-> new BusinessException(ErrorCode.CAFE_NOT_FOUND));

        cafe.updateInfo(
                request.name(),
                request.address(),
                request.phone(),
                request.shortDesc()
        );

        return CafeResponse.from(cafe);
    }

    @Transactional
    public CafeResponse requestApproval(Long cafeId){
        Cafe cafe = cafeRepository.findByIdAndStatusNot(cafeId, CafeStatus.DELETED)
                .orElseThrow(()-> new BusinessException(ErrorCode.CAFE_NOT_FOUND));

        cafe.requestApproval();
        return CafeResponse.from(cafe);
    }

    @Transactional
    public void deleteCafe(Long cafeId,String deletedBy){
        Cafe cafe = cafeRepository.findByIdAndStatusNot(cafeId, CafeStatus.DELETED)
                .orElseThrow(()-> new BusinessException(ErrorCode.CAFE_NOT_FOUND));

        cafe.delete(deletedBy);
    }
}
