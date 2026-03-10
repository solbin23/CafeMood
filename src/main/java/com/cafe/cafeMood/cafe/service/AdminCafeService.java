package com.cafe.cafeMood.cafe.service;

import com.cafe.cafeMood.cafe.domain.cafe.Cafe;
import com.cafe.cafeMood.cafe.dto.request.AdminCafeCreateRequest;
import com.cafe.cafeMood.cafe.dto.request.AdminCafeUpdateRequest;
import com.cafe.cafeMood.cafe.dto.response.AdminCafeResponse;
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
    private final AdminCafeValidator adminCafeValidator;
    private final CafeSubmissionRepository cafeSubmissionRepo;


    @Transactional
    public AdminCafeResponse create(AdminCafeCreateRequest req) {
        adminCafeValidator.validateCreate(req);

        Cafe cafe = Cafe.create(req.name(), req.shortDesc(), req.phone());

        Cafe savedCafe = cafeRepo.save(cafe);
        return AdminCafeResponse.from(savedCafe);
    }

    @Transactional
    public AdminCafeResponse update(Long id, AdminCafeUpdateRequest req) {
        adminCafeValidator.validateUpdate(req);

        Cafe updateCafe = cafeRepo.findById(id)
                .orElseThrow(()-> new IllegalArgumentException("cafe not found : " + id));

        if (req.name() != null && req.name().isEmpty()) {
            updateCafe.updateName(req.name());
        }

        if(req.phone() != null && req.phone().isEmpty()) {
            updateCafe.updatePhone(req.phone());
        }
        return AdminCafeResponse.from(updateCafe);
    }


    @Transactional
    public AdminCafeResponse publish(Long id){
        Cafe publishCafe = cafeRepo.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("cafe not found"+ id));

        publishCafe.publish();

        return AdminCafeResponse.from(publishCafe);
    }

    @Transactional
    public AdminCafeResponse hide(Long id){
        Cafe hideCafe = cafeRepo.findById(id)
                .orElseThrow(()-> new IllegalArgumentException("cafe not found"+ id));

        hideCafe.hide();
        return AdminCafeResponse.from(hideCafe);
    }

}
