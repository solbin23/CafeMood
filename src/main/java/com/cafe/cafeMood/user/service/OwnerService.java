package com.cafe.cafeMood.user.service;


import com.cafe.cafeMood.common.auth.dto.LoginUser;
import com.cafe.cafeMood.common.exception.BusinessException;
import com.cafe.cafeMood.common.exception.ErrorCode;
import com.cafe.cafeMood.user.domain.Owner;
import com.cafe.cafeMood.user.domain.User;
import com.cafe.cafeMood.user.domain.UserRole;
import com.cafe.cafeMood.user.dto.request.OwnerSignUpRequest;
import com.cafe.cafeMood.user.dto.response.OwnerResponse;
import com.cafe.cafeMood.user.repo.OwnerRepository;
import com.cafe.cafeMood.user.repo.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@Transactional
@RequiredArgsConstructor
public class OwnerService {

    private final UserRepository userRepository;
    private final OwnerRepository ownerRepository;
    private final PasswordEncoder passwordEncoder;

    public OwnerResponse signUp(OwnerSignUpRequest request) {
        if (userRepository.existsByEmail(request.email())){
            throw new BusinessException(ErrorCode.DUPLICATED_EMAIL);
        }
        if (ownerRepository.existsByBusinessNumber(request.businessNumber())){
            throw new BusinessException(ErrorCode.DUPLICATED_BUSINESS_NUMBER);
        }

        String encodePassword = passwordEncoder.encode(request.password());

        User owner = User.createOwner(
                request.email(),
                encodePassword,
                request.name(),
                request.phone(),
                request.serviceTermsConsent(),
                request.privacyConsent(),
                request.marketingConsent()
        );

        User savedOwner = userRepository.save(owner);

        Owner ownerProfile = Owner.create(
                savedOwner,
                request.businessNumber(),
                request.businessName()
        );

        return OwnerResponse.of(ownerRepository.save(ownerProfile));
    }

    @Transactional(readOnly = true)
    public Owner getOwnerProfile(LoginUser loginUser) {
        validateOwner(loginUser);
        return ownerRepository.findByUserId(loginUser.userId())
                .orElseThrow(() -> new BusinessException(ErrorCode.OWNER_NOT_FOUND));
    }

    private void validateOwner(LoginUser loginUser) {
        if (loginUser.role() != UserRole.OWNER) {
            throw new BusinessException(ErrorCode.FORBIDDEN);
        }
    }
}
