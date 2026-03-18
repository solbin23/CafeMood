package com.cafe.cafeMood.cafe.service;

import com.cafe.cafeMood.cafe.domain.cafe.Cafe;
import com.cafe.cafeMood.cafe.domain.cafe.CafeStatus;

import com.cafe.cafeMood.cafe.dto.response.cafe.CafeResponse;

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
        return cafeRepository.findAllByStatusNot(CafeStatus.DELETED)
                .stream()
                .map(CafeResponse::from)
                .toList();
    }


    public List<CafeResponse> getPublishCafe(Long cafeId) {
        return cafeRepository.findAllByStatus(CafeStatus.PUBLISHED)
                .stream()
                .map(CafeResponse::from)
                .toList();

    }

    @Transactional
    public List<CafeResponse> getHiddenCafe() {
        return cafeRepository.findAllByStatus(CafeStatus.HIDDEN)
                .stream().map(CafeResponse::from).toList();
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


    private List<CafeResponse> getCafesByStatus(CafeStatus status) {
        return cafeRepository.findAllByStatus(status)
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
