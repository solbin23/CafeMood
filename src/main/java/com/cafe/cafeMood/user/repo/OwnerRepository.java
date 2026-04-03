package com.cafe.cafeMood.user.repo;

import com.cafe.cafeMood.user.domain.Owner;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface OwnerRepository extends JpaRepository<Owner, Long> {
    boolean existsByBusinessNumber(String businessNumber);
    Optional<Owner> findByUserId(Long userId);
}
