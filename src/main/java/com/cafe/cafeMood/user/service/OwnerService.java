package com.cafe.cafeMood.user.service;


import com.cafe.cafeMood.user.repo.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class OwnerService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
}
