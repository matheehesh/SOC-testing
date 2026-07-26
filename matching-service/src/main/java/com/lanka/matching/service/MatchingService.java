package com.lanka.matching.service;

import com.lanka.matching.dto.MatchRequest;
import com.lanka.matching.dto.MatchResponse;
import com.lanka.matching.model.MatchResult;
import com.lanka.matching.repository.MatchResultRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class MatchingService {
    private final MatchResultRepository results;

    public MatchingService(MatchResultRepository results) {
        this.results = results;
    }

    public MatchResponse match(MatchRequest request) {
        List<String> required = request.requiredSkills() == null ? List.of() : request.requiredSkills();
        List<String> worker = request.workerSkills() == null ? List.of() : request.workerSkills();
        long overlap = required.stream().filter(worker::contains).count();
        int score = required.isEmpty() ? 50 : (int) ((overlap * 100) / required.size());
        String label = score >= 70 ? "Strong match" : score >= 40 ? "Possible match" : "Weak match";
        MatchResult result = new MatchResult();
        result.setJobId(request.jobId());
        result.setDistrict(request.district());
        result.setScore(score);
        result.setRecommendation(label);
        result.setMatchedAt(LocalDateTime.now());
        results.save(result);
        return new MatchResponse(request.jobId(), score, label);
    }

    public List<MatchResult> getByJobId(Long jobId) {
        return results.findByJobId(jobId);
    }
}
