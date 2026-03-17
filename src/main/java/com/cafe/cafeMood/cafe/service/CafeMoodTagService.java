package com.cafe.cafeMood.cafe.service;

import com.cafe.cafeMood.cafe.domain.cafe.Cafe;
import com.cafe.cafeMood.cafe.domain.cafe.CafeStatus;
import com.cafe.cafeMood.cafe.domain.tag.*;
import com.cafe.cafeMood.cafe.dto.response.cafe.CafeTagVoteResultResponse;
import com.cafe.cafeMood.cafe.dto.response.MoodTagResponse;
import com.cafe.cafeMood.cafe.repo.cafe.CafeRepository;
import com.cafe.cafeMood.cafe.repo.tag.*;
import com.cafe.cafeMood.tag.*;
import com.cafe.cafeMood.tag.domain.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CafeMoodTagService {


    private static final int TOP_N = 5;
    private static final ZoneId KST = ZoneId.of("Asia/Seoul");

    private final CafeRepository cafeRepository;
    private final TagRepository tagRepository;
    private final CafeTagVoteRepository cafeTagVoteRepository;
    private final UserCafeTagVoteDailyRepository tagVoteDailyRepository;
    private final CafeTagAggregateRepository tagAggregateRepository;
    private final CafeTagRepository cafeTagRepository;

    @Transactional
    public CafeTagVoteResultResponse vote(Long cafeId, Long tagId, Long userId) {
        System.out.println("vote() called");

        if(cafeId == null || cafeId <= 0) {
            throw new IllegalArgumentException("invalid cafe id");
        }

        if (tagId == null || tagId <= 0) {
            throw new IllegalArgumentException("invalid tag id");
        }

        if(userId == null || userId <= 0) {
            throw new IllegalArgumentException("invalid user id");
        }

        System.out.println("findById path");
        Cafe cafe = cafeRepository.findById(cafeId)
                .orElseThrow(() -> new IllegalArgumentException("cafe not found"));

        if (cafe.getStatus() != CafeStatus.PUBLISHED) {
            throw new IllegalArgumentException("cafe status is not published");
        }

        Tag tag = tagRepository.findById(tagId).orElseThrow(() -> new IllegalArgumentException("tag not found"));

        if(!tag.isActive()) {
            throw new IllegalArgumentException("tag is not active");
        }

        boolean counted = tryDailyVote(cafeId, tagId, userId);
        if(!counted) {
            return new CafeTagVoteResultResponse(false, getCurrentMoodTag(cafeId));
        }

        cafeTagVoteRepository.save(CafeTagVote.create(cafeId, tagId, userId));

        increaseAggregateWithLock(cafeId, tagId);
        syncTop5CafeTags(cafeId);
        return new CafeTagVoteResultResponse(true, getCurrentMoodTag(cafeId));
    }


    private boolean tryDailyVote(Long cafeId, Long tagId, Long userId) {
        try{
            tagVoteDailyRepository.save(
                    UserCafeTagVoteDaily.create(
                            cafeId,
                            userId,
                            tagId,
                            LocalDate.now(KST)
                    )
            );
            return true;
        } catch (DataIntegrityViolationException e) {
            //동일한 유저가 같은 카페에 같은 태그를 같은 날 또 누른 경우
            return false;
        }
    }

    /**
     * aggregate row 단위 락
     * 1. 있으면 lock 후 증가
     * 2. 없으면 생성 시도
     * 3. 생성 충돌이면 다시 lock 조회 후 증가
     */
    private void increaseAggregateWithLock(Long cafeId, Long tagId){
        Optional<CafeTagAggregate> lockAggregate = tagAggregateRepository.findByCafeIdAndTagIdForUpdate(cafeId, tagId);
        if(lockAggregate.isPresent()) {
            CafeTagAggregate aggregate = lockAggregate.get();
            aggregate.increase(1,1);
            return;
        }
        try{
            CafeTagAggregate newAggregate =
              CafeTagAggregate.create(cafeId, tagId);

            newAggregate.increase(1,1);
            tagAggregateRepository.save(newAggregate);
            return;
        } catch (DataIntegrityViolationException e) {

        }

        CafeTagAggregate aggregate =
                tagAggregateRepository.findByCafeIdAndTagIdForUpdate(cafeId, tagId)
                        .orElseThrow(() ->
                                new IllegalArgumentException("aggregate not found after duplicate key"));

        aggregate.increase(1,1);
    }


    private void syncTop5CafeTags(Long cafeId) {
        List<CafeTagAggregate> top5 = tagAggregateRepository.findTopByCafeId(cafeId,TOP_N);
        if (top5.isEmpty()) {
            return;
        }

        for (CafeTagAggregate aggregate : top5) {
            CafeTag cafeTag = cafeTagRepository.findByCafeIdAndTagId(cafeId, aggregate.getTagId())
                    .orElseGet(() -> CafeTag.create( cafeId, aggregate.getTagId(),aggregate.getTotalCount()));

            cafeTag.updateScore(aggregate.getTotalCount());
            cafeTagRepository.save(cafeTag);
        }

        List<Long> tagIdList = top5.stream().map(CafeTagAggregate::getTagId).toList();

        cafeTagRepository.deleteByCafeIdAndTagIdNotIn(cafeId, tagIdList);
    }

    @Transactional(readOnly = true)
    public List<MoodTagResponse> getCurrentMoodTag(Long cafeId) {
        List<CafeTag> cafeTags = cafeTagRepository.findByCafeIdOrderByScoreDesc(cafeId);

        List<Long> tagIds = cafeTags.stream().map(CafeTag::getTagId).toList();

        Map<Long, Tag> tagMap = tagRepository.findAllById(tagIds).stream()
                .collect(Collectors.toMap(Tag::getId, tag -> tag));

        return cafeTags.stream()
                .map(cafeTag -> {
                    Tag tag = tagMap.get(cafeTag.getTagId());

                    return new MoodTagResponse(
                            cafeTag.getTagId(),
                            tag != null ? tag.getName() : null,
                            cafeTag.getScore()
                    );
                })
                .toList();
    }

}
