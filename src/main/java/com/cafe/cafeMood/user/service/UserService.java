package com.cafe.cafeMood.user.service;


import com.cafe.cafeMood.common.exception.BusinessException;
import com.cafe.cafeMood.common.exception.ErrorCode;
import com.cafe.cafeMood.user.domain.User;
import com.cafe.cafeMood.user.dto.request.LoginRequest;
import com.cafe.cafeMood.user.dto.request.SignUpRequest;
import com.cafe.cafeMood.user.dto.response.LoginResponse;
import com.cafe.cafeMood.user.dto.response.UserResponse;
import com.cafe.cafeMood.user.repo.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public UserResponse signUpUser(SignUpRequest request) {
        validateDuplicated(request.loginId(), request.email());

        String encodedPassword = passwordEncoder.encode(request.password());
        User user = User.user(
                request.loginId(),
                encodedPassword,
                request.name(),
                request.email(),
                request.phone()
        );
        return UserResponse.from(userRepository.save(user));
    }
    @Transactional
    public UserResponse signUpOwner(SignUpRequest request) {
        validateDuplicated(request.loginId(), request.email());
        String encodedPassword = passwordEncoder.encode(request.password());
        User owner = User.owner(
                request.loginId(),
                encodedPassword,
                request.name(),
                request.email(),
                request.phone()
        );
        return UserResponse.from(userRepository.save(owner));
    }

    @Transactional
    public LoginResponse login(LoginRequest request) {
        User login = userRepository.findByLoginId(request.loginId())
                .orElseThrow(() -> new BusinessException(ErrorCode.USER_NOT_FOUND));

        if (!passwordEncoder.matches(request.password(), login.getPassword())){
            throw new BusinessException(ErrorCode.INVALID_PASSWORD);
        }

        return LoginResponse.of(
                login.getId(),
                login.getLoginId(),
                login.getRole()
        );
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
