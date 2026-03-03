package com.cafe.cafeMood.cafe.repo;

import com.cafe.cafeMood.cafe.domain.CafeTagAggregate;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CafeTagAggregateRepository extends JpaRepository<CafeTagAggregate, CafeTagAggregate.Pk> {
}
