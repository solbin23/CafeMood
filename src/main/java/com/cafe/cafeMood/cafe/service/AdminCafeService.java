package com.cafe.cafeMood.cafe.service;

import com.cafe.cafeMood.cafe.domain.cafe.Cafe;
import com.cafe.cafeMood.cafe.domain.cafe.CafeStatus;
import com.cafe.cafeMood.cafe.domain.submission.CafeSubmission;
import com.cafe.cafeMood.cafe.dto.request.AdminCafeCreateRequest;
import com.cafe.cafeMood.cafe.dto.request.AdminCafeUpdateRequest;
import com.cafe.cafeMood.cafe.dto.response.AdminCafeResponse;
import com.cafe.cafeMood.cafe.dto.response.cafe.CafeResponse;
import com.cafe.cafeMood.cafe.dto.response.cafe.CafeSubmissionResponse;
import com.cafe.cafeMood.cafe.repo.cafe.CafeRepository;
import com.cafe.cafeMood.cafe.repo.submission.CafeSubmissionRepository;
import com.cafe.cafeMood.cafe.validation.AdminCafeValidator;
import com.cafe.cafeMood.common.exception.BusinessException;
import com.cafe.cafeMood.common.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AdminCafeService {

    private final CafeRepository cafeRepository;

    public List<CafeResponse> getAllCafes(){
        return cafeRepository.findAllByStatusNot(CafeStatus.DELETED)
                .stream()
                .map(CafeResponse::from)
                .toList();
    }

    @Transactional
    public CafeResponse publishCafe(Long cafeId) {
        Cafe cafe = cafeRepository.findByIdAndStatusNot(cafeId, CafeStatus.DELETED)
                .orElseThrow(() -> new BusinessException(ErrorCode.CAFE_NOT_FOUND));
        cafe.publish();
        return CafeResponse.from(cafe);
    }

    @Transactional
    public CafeResponse hideCafe(Long cafeId) {
        Cafe cafe = cafeRepository.findByIdAndStatusNot(cafeId, CafeStatus.DELETED)
                .orElseThrow(() -> new BusinessException(ErrorCode.CAFE_NOT_FOUND));
        cafe.hide();
        return CafeResponse.from(cafe);
    }

    @Transactional
    public CafeResponse suspendCafe(Long cafeId) {
        Cafe cafe = cafeRepository.findByIdAndStatusNot(cafeId, CafeStatus.DELETED)
                .orElseThrow(() -> new BusinessException(ErrorCode.CAFE_NOT_FOUND));
        cafe.suspend();
        return CafeResponse.from(cafe);
    }

    @Transactional
    public void deleteCafe(Long cafeId, String deletedBy) {
        Cafe cafe = cafeRepository.findByIdAndStatusNot(cafeId, CafeStatus.DELETED)
                .orElseThrow(() -> new BusinessException(ErrorCode.CAFE_NOT_FOUND));
        cafe.delete(deletedBy);
    }
}
