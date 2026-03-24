package com.cafe.cafeMood.user.service;


import com.cafe.cafeMood.common.auth.dto.LoginUser;
import com.cafe.cafeMood.common.exception.BusinessException;
import com.cafe.cafeMood.common.exception.ErrorCode;
import com.cafe.cafeMood.user.domain.User;
import com.cafe.cafeMood.common.auth.dto.LoginRequest;
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


    public void signUp(SignUpRequest signUpRequest) {
        if (userRepository.existsByEmail(signUpRequest.email())) {
            throw  new BusinessException(ErrorCode.DUPLICATED_EMAIL);
        }

        String encodedPassword = passwordEncoder.encode(signUpRequest.password());

        User user = User.create(signUpRequest.email(), encodedPassword,signUpRequest.name(),signUpRequest.phone());


        userRepository.save(user);
    }

    @Transactional
    public UserResponse getMyInfo(LoginUser loginUser) {

        User user = userRepository.findById(loginUser.userId()).orElseThrow(() -> new BusinessException(ErrorCode.USER_NOT_FOUND));

        return new UserResponse(user.getId(),user.getName(),user.getEmail(),user.getPhone(),user.getRole());
    }

}
