package com.cafe.cafeMood.cafe.repo;

import com.cafe.cafeMood.cafe.domain.Cafe;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CafeRepository extends JpaRepository<Cafe, Long> {
}
