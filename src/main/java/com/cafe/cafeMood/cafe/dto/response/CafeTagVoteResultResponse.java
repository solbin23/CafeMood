package com.cafe.cafeMood.cafe.dto.response;

import java.util.List;

public record CafeTagVoteResultResponse(boolean count, List<MoodTagResponse> moodTagList) {
}
