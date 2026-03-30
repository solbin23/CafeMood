package com.cafe.cafeMood.cafe.service;

import com.cafe.cafeMood.cafe.domain.cafe.Cafe;
import com.cafe.cafeMood.cafe.domain.cafe.CafeStatus;

import com.cafe.cafeMood.cafe.dto.response.CafeResponse;

import com.cafe.cafeMood.cafe.repo.cafe.CafeRepository;

import com.cafe.cafeMood.common.exception.BusinessException;
import com.cafe.cafeMood.common.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.function.Consumer;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AdminCafeService {

    private final CafeRepository cafeRepository;

    public List<CafeResponse> getAllCafes(){
        return getCafesByStatusNot(CafeStatus.DELETED);
    }


    public List<CafeResponse> getPublishCafes() {
        return getCafesByStatus(CafeStatus.PUBLISHED);
    }

    public List<CafeResponse> getHiddenCafes() {
        return getCafesByStatus(CafeStatus.HIDDEN);
    }

    public List<CafeResponse> getSuspendedCafes() {
        return getCafesByStatus(CafeStatus.SUSPENDED);
    }

    public CafeResponse getCafe(Long cafeId){
        return CafeResponse.from(findActiveCafe(cafeId));
    }

    @Transactional
    public CafeResponse publishCafe(Long cafeId) {
        return changeCafeStatus(cafeId, Cafe::publish);
    }

    @Transactional
    public CafeResponse HideCafe(Long cafeId) {
        return changeCafeStatus(cafeId,Cafe::hide);
    }

    @Transactional
    public CafeResponse suspendCafe(Long cafeId) {
       return changeCafeStatus(cafeId,Cafe::suspend);
    }

    @Transactional
    public void deleteCafe(Long cafeId, String deletedBy) {
       Cafe cafe = findActiveCafe(cafeId);
       cafe.delete(deletedBy);
    }


    private List<CafeResponse> getCafesByStatus(CafeStatus status) {
        return cafeRepository.findByStatus(status)
                .stream()
                .map(CafeResponse::from)
                .toList();
    }

    private List<CafeResponse> getCafesByStatusNot(CafeStatus status) {
        return cafeRepository.findAllByStatusNot(status)
                .stream()
                .map(CafeResponse::from)
                .toList();
    }

    private Cafe findActiveCafe(Long cafeId) {
        return cafeRepository.findByIdAndStatusNot(cafeId, CafeStatus.DELETED)
                .orElseThrow(() -> new BusinessException(ErrorCode.CAFE_NOT_FOUND));
    }

    private CafeResponse changeCafeStatus(Long cafeId, Consumer<Cafe> statusChange) {
        Cafe cafe = findActiveCafe(cafeId);
        statusChange.accept(cafe);
        return CafeResponse.from(cafe);
    }
}
