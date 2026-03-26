package com.cafe.cafeMood.tag.controller;

import com.cafe.cafeMood.tag.TagService;
import com.cafe.cafeMood.tag.domain.TagCategory;
import com.cafe.cafeMood.tag.dto.TagResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/cafe/tags")
public class TagController {

    private final TagService service;

    @GetMapping
    public ResponseEntity<List<TagResponse>> getTags(@RequestParam(required = false)TagCategory tagCategory) {

        return ResponseEntity.ok(service.getTags(tagCategory));
    }
}
