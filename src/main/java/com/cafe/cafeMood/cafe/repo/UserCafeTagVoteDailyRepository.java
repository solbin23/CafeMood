package com.cafe.cafeMood.cafe.repo;

import com.cafe.cafeMood.cafe.domain.UserCafeTagVoteDaily;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserCafeTagVoteDailyRepository extends JpaRepository<UserCafeTagVoteDaily, UserCafeTagVoteDaily.Pk> {
}
