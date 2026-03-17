package com.cafe.cafeMood.cafe.dto.response.cafe;

import com.cafe.cafeMood.cafe.dto.response.MoodTagResponse;

import java.util.List;

public record CafeTagVoteResultResponse(boolean count, List<MoodTagResponse> moodTagList) {
}
