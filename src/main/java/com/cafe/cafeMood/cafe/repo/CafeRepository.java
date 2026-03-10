package com.cafe.cafeMood.cafe.repo;

import com.cafe.cafeMood.cafe.domain.cafe.Cafe;
import com.cafe.cafeMood.cafe.domain.cafe.CafeStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CafeRepository extends JpaRepository<Cafe, Long> {
    List<Cafe> findByStatusOrderByIdDesc(CafeStatus status);
}
