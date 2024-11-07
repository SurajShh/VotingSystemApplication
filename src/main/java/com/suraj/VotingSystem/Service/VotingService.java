package com.suraj.VotingSystem.Service;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.suraj.VotingSystem.Model.Candidate;

@Service
public class VotingService {
    private final Map<String, Candidate> candidates = new ConcurrentHashMap<>();

    public boolean addCandidate(String name) {
        if (candidates.containsKey(name)) {
            return false;
        }
        candidates.put(name, new Candidate(name));
        return true;
    }

    public boolean castVote(String name) {
        Candidate candidate = candidates.get(name);
        if (candidate == null) {
            return false;
        }
        candidate.incrementVote();
        return true;
    }

    public Integer getVoteCount(String name) {
        Candidate candidate = candidates.get(name);
        return candidate != null ? candidate.getVoteCount() : null;
    }

    public Map<String, Integer> listVotes() {
        Map<String, Integer> result = new ConcurrentHashMap<>();
        for (Map.Entry<String, Candidate> entry : candidates.entrySet()) {
            result.put(entry.getKey(), entry.getValue().getVoteCount());
        }
        return result;
    }

    public String getWinner() {
        Optional<Candidate> winner = candidates.values().stream()
                .max(Comparator.comparingInt(Candidate::getVoteCount));
        return winner.map(Candidate::getName).orElse(null);
    }

    public boolean removeCandidate(String name) {
        return candidates.remove(name) != null;
    }

    public void clearAllVotes() {
        candidates.values().forEach(candidate -> candidate.setVoteCount(0));
    }

    public Map<String, Object> getStatistics() {
        int totalVotes = candidates.values().stream().mapToInt(Candidate::getVoteCount).sum();
        double avgVotes = candidates.isEmpty() ? 0 : (double) totalVotes / candidates.size();

        Map<String, Double> votePercentages = candidates.values().stream()
                .collect(Collectors.toMap(
                        Candidate::getName,
                        candidate -> (totalVotes == 0) ? 0 : (candidate.getVoteCount() * 100.0) / totalVotes));

        Map<String, Object> stats = new HashMap<>();
        stats.put("totalVotes", totalVotes);
        stats.put("averageVotes", avgVotes);
        stats.put("votePercentages", votePercentages);

        return stats;
    }

    public List<LocalDateTime> getVoteHistory(String name) {
        Candidate candidate = candidates.get(name);
        return (candidate != null) ? candidate.getVoteHistory() : null;
    }
}
