package com.cafe.cafeMood.cafe.repo.cafe;

import com.cafe.cafeMood.cafe.domain.cafe.Cafe;
import com.cafe.cafeMood.cafe.domain.cafe.CafeStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CafeRepository extends JpaRepository<Cafe, Long> {

    Optional<Cafe> findByIdAndStatusNot(Long id, CafeStatus cafeStatus);

    List<Cafe> findAllByOwnerIdAndStatusNot(Long ownerId, CafeStatus cafeStatus);

    List<Cafe> findAllByStatusNot(CafeStatus cafeStatus);

    List<Cafe> findAllByStatus(CafeStatus cafeStatus);

}
