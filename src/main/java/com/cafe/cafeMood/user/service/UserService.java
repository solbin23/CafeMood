package com.cafe.cafeMood.user.service;


import com.cafe.cafeMood.common.exception.BusinessException;
import com.cafe.cafeMood.common.exception.ErrorCode;
import com.cafe.cafeMood.user.domain.User;
import com.cafe.cafeMood.user.dto.request.SignUpRequest;
import com.cafe.cafeMood.user.dto.response.UserResponse;
import com.cafe.cafeMood.user.repo.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService {

    private final UserRepository userRepository;

    @Transactional
    public UserResponse signUpUser(SignUpRequest request) {
        validateDuplicated(request.loginId(), request.email());
        User user = User.createOwner(
                request.loginId(),
                request.password(),
                request.name(),
                request.email(),
                request.phone()
        );
        return UserResponse.from(userRepository.save(user));
    }
    @Transactional
    public UserResponse signUpOwner(SignUpRequest request) {
        validateDuplicated(request.loginId(), request.email());
        User owner = User.createOwner(
                request.loginId(),
                request.password(),
                request.name(),
                request.email(),
                request.phone()
        );
        return UserResponse.from(userRepository.save(owner));
    }

    private void validateDuplicated(String loginId, String email){
        if (userRepository.existsByLoginId(loginId)){
            throw new BusinessException(ErrorCode.DUPLICATED_LOGIN_ID);
        }
        if (userRepository.existsByEmail(email)){
            throw new BusinessException(ErrorCode.DUPLICATED_EMAIL);
        }
    }
}
