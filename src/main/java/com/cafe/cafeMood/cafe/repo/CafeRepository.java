package com.cafe.cafeMood.cafe.repo;

import com.cafe.cafeMood.cafe.domain.Cafe;
import com.cafe.cafeMood.cafe.domain.CafeStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CafeRepository extends JpaRepository<Cafe, Long> {
    List<Cafe> findByStatusOrderByIdDesc(CafeStatus status);
}
