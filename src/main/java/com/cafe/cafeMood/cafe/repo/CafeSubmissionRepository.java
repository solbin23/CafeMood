package com.cafe.cafeMood.cafe.repo;

import com.cafe.cafeMood.cafe.domain.submission.CafeSubmission;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CafeSubmissionRepository extends JpaRepository<CafeSubmission, Long> {

    List<CafeSubmission> findByOwnerUserIdOrderByIdDesc(String ownerUserId);
}
