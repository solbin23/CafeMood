//package com.cafe.cafeMood.cafe.service;
//
//
//
//import com.cafe.cafeMood.cafe.repo.tag.TagRepository;
//import lombok.RequiredArgsConstructor;
//import org.springframework.data.redis.core.StringRedisTemplate;
//import org.springframework.stereotype.Service;
//
//import java.time.Duration;
//import java.time.LocalDate;
//import java.time.ZoneId;
//import java.time.ZonedDateTime;
//import java.time.format.DateTimeFormatter;
//
//
//@Service
//@RequiredArgsConstructor
//public class CafeTagRedisService {
//
//    private static final ZoneId KST = ZoneId.of("Asia/Seoul");
//    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.BASIC_ISO_DATE;
//    private static final int TOP_N = 5;
//
//    private final StringRedisTemplate redisTemplate;
//    private final TagRepository tagRepository;
//
//    /**
//     * 같은 유저가 같은 가페에 같은 태그를 오늘 처음 누른 경우만 true
//     */
//    public boolean tryVoteDaily(Long cafeId, Long userId, Long tagId) {
//        String key = dailyVoteKey(cafeId,userId, tagId);
//
//        Duration ttl = durationUntilKstMidnight();
//        Boolean success = redisTemplate.opsForValue().setIfAbsent(key, "1",ttl);
//        return Boolean.TRUE.equals(success);
//    }
//    /**
//     * 태그 점수 1 증가
//     */
//    public void increaseTagScore(Long cafeId, Long tagId){
//        String key = buildCafeTagScoreKey(cafeId);
//        redisTemplate.opsForZSet().incrementScore(key,String.valueOf(tagId),1.0);
//    }
//
////    /**
////     * 현재 레디스 기준 TOP5 조회
////     */
////    public List<MoodTagResponse> getTop5MoodTags(Long cafeId) {
////        String key = buildCafeTagScoreKey(cafeId);
////
////        Set<ZSetOperations.TypedTuple<String>> tuples =
////                redisTemplate.opsForZSet().reverseRangeByScoreWithScores(key,0,TOP_N -1);
////    }
//
//    private String dailyVoteKey(Long cafeId, Long userId, Long tagId) {
//        String today = LocalDate.now(KST).format(DATE_FORMATTER);
//        return "vote:daily" + today + ":cafe:" + cafeId + ":user:" + userId + ":tag:" + tagId;
//    }
//
//    private String buildCafeTagScoreKey(Long cafeId){
//        return "cafe:" + cafeId + ":tag:score";
//    }
//
//    private Duration durationUntilKstMidnight(){
//        ZonedDateTime now = ZonedDateTime.now(KST);
//        ZonedDateTime nexMidnight = now.toLocalDate().plusDays(1).atStartOfDay(KST);
//
//        return Duration.between(now, nexMidnight);
//    }
//}
