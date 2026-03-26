package com.cafe.cafeMood.tag;


import com.cafe.cafeMood.tag.domain.TagCategory;
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
            return tagRepository.findByActiveTrueOrderBySortOrderAsc()
                    .stream()
                    .map(TagResponse::from)
                    .toList();
        }

        return tagRepository.findByCategoryAndActiveTrueOrderBySortOrderAsc(category)
                .stream()
                .map(TagResponse::from)
                .toList();
    }
}
