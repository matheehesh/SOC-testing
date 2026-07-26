package com.lanka.matching.controller;

import com.lanka.matching.dto.MatchRequest;
import com.lanka.matching.dto.MatchResponse;
import com.lanka.matching.model.MatchResult;
import com.lanka.matching.service.MatchingService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/matches")
public class MatchController {
    private final MatchingService service;

    public MatchController(MatchingService service) {
        this.service = service;
    }

    @PostMapping
    MatchResponse match(@RequestBody MatchRequest request) {
        return service.match(request);
    }

    @GetMapping("/{jobId}")
    List<MatchResult> history(@PathVariable Long jobId) {
        return service.getByJobId(jobId);
    }
}
