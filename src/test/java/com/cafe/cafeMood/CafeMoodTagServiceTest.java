package com.cafe.cafeMood;

import com.cafe.cafeMood.aggregate.domain.CafeTagAggregate;
import com.cafe.cafeMood.aggregate.repo.CafeTagAggregateRepository;
import com.cafe.cafeMood.cafe.domain.cafe.Cafe;
import com.cafe.cafeMood.cafe.domain.tag.*;
import com.cafe.cafeMood.cafe.dto.response.cafe.CafeTagVoteResultResponse;
import com.cafe.cafeMood.cafe.repo.cafe.CafeRepository;
import com.cafe.cafeMood.cafe.repo.tag.*;
import com.cafe.cafeMood.cafe.service.CafeMoodTagService;
import com.cafe.cafeMood.tag.*;
import com.cafe.cafeMood.tag.domain.Tag;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
@Slf4j
public class CafeMoodTagServiceTest {

    @Mock
    private CafeRepository cafeRepository;

    @Mock
    private TagRepository tagRepository;

    @Mock
    private CafeTagVoteRepository cafeTagVoteRepository;

    @Mock
    private UserCafeTagVoteDailyRepository tagVoteDailyRepository;

    @Mock
    private CafeTagAggregateRepository tagAggregateRepository;

    @Mock
    private CafeTagRepository cafeTagRepository;

    @InjectMocks
    private CafeMoodTagService cafeMoodTagService;

    @Nested
    @DisplayName("vote")
    class VoteTest {
        @Test
        @DisplayName("첫 투표면 count = true, 로고 저장 ,aggregate 생성, top5 동기화 수행")
        void vote_success_firstVote(){
            Long cafeId = 1L;
            Long tagId = 2L;
            Long userId = 3L;

            Cafe publishedCafe = createPublishedCafe();
            Tag activeTag = createActiveTag();

            given(cafeRepository.findById(cafeId)).willReturn(Optional.of(publishedCafe));

            //verify(cafeRepository).findById(cafeId);
            given(tagRepository.findById(tagId)).willReturn(Optional.of(activeTag));

            //daily insert 성공
            given(tagVoteDailyRepository.save(any(UserCafeTagVoteDaily.class))).willAnswer(invocation -> invocation.getArgument(0));

            given(tagAggregateRepository.findByCafeIdAndTagIdForUpdate(cafeId, tagId)).willReturn(Optional.empty());

            given(tagAggregateRepository.save(any(CafeTagAggregate.class))).willAnswer(invocation -> invocation.getArgument(0));

            CafeTagAggregate aggregate = CafeTagAggregate.create(cafeId, tagId);
            aggregate.increase(1,1);

            given(tagAggregateRepository.findTopByCafeId(cafeId,5)).willReturn(List.of(aggregate));

            given(cafeTagRepository.findByCafeIdAndTagId(cafeId,tagId)).willReturn(Optional.empty());

            given(cafeTagRepository.save(any(CafeTag.class))).willAnswer(invocation -> invocation.getArgument(0));
            given(cafeTagRepository.findByCafeIdOrderByScoreDesc(cafeId)).willReturn(List.of(CafeTag.create(cafeId,tagId,1)));

            given(tagRepository.findAllById(List.of(tagId))).willReturn(List.of(activeTag));


            CafeTagVoteResultResponse result = cafeMoodTagService.vote(cafeId, tagId, userId);
            assertThat(result.count()).isTrue();
            verify(cafeRepository).findById(cafeId);
            verify(tagRepository).findById(tagId);
            verify(cafeTagVoteRepository).save(any(CafeTagVote.class));

        }
    }


    private Cafe createPublishedCafe() {

        Cafe cafe = Cafe.create("카페무드테스트","테스트","031-333-2222");
        cafe.publish();
        log.info("created cafe = {}",cafe);

        return cafe;
    }

    private Tag createActiveTag(){
        return Tag.create("조용한","quiet");
    }
}
