package com.cafe.cafeMood.tag.service;


import com.cafe.cafeMood.tag.domain.TagCategory;
import com.cafe.cafeMood.tag.domain.TagStatus;
import com.cafe.cafeMood.tag.dto.TagResponse;
import com.cafe.cafeMood.tag.repo.CafeTagRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly=true)
public class TagService {

    private final CafeTagRepository tagRepository;

    public List<TagResponse> getTags(TagCategory category) {
        if (category == null) {
            return tagRepository.findByStatus(TagStatus.ACTIVE)
                    .stream()
                    .map(TagResponse::from)
                    .toList();
        }

        return tagRepository.findByCategoryAndStatus(category,TagStatus.ACTIVE)
                .stream()
                .map(TagResponse::from)
                .toList();
    }
}
