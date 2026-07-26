package com.lanka.matching.dto;

import java.util.List;

public record MatchRequest(Long jobId, String district, List<String> requiredSkills, List<String> workerSkills) {
}

