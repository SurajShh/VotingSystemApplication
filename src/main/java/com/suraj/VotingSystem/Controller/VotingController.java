package com.suraj.VotingSystem.Controller;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.suraj.VotingSystem.Service.VotingService;

@RestController
@RequestMapping("/api")
public class VotingController {

    @Autowired
    private VotingService votingService;

    @PostMapping("/entercandidate")
    public String enterCandidate(@RequestParam String name) {
        return votingService.addCandidate(name) ? "Candidate added successfully" : "Candidate already exists";
    }

    @PostMapping("/castvote")
    public String castVote(@RequestParam String name) {
        return votingService.castVote(name) ? "Vote cast successfully" : "Candidate not found";
    }

    @GetMapping("/countvote")
    public String countVote(@RequestParam String name) {
        Integer voteCount = votingService.getVoteCount(name);
        return voteCount != null ? "Vote count for " + name + ": " + voteCount : "Candidate not found";
    }

    @GetMapping("/listvote")
    public Map<String, Integer> listVote() {
        return votingService.listVotes();
    }

    @GetMapping("/getwinner")
    public String getWinner() {
        String winner = votingService.getWinner();
        return winner != null ? "Winner: " + winner : "No candidates available";
    }

    @DeleteMapping("/removecandidate")
    public String removeCandidate(@RequestParam String name) {
        return votingService.removeCandidate(name) ? "Candidate removed successfully" : "Candidate not found";
    }

    @PostMapping("/clearvotes")
    public String clearAllVotes() {
        votingService.clearAllVotes();
        return "All votes have been reset to zero";
    }

    @GetMapping("/statistics")
    public Map<String, Object> getStatistics() {
        return votingService.getStatistics();
    }

    @GetMapping("/votehistory")
    public List<LocalDateTime> getVoteHistory(@RequestParam String name) {
        List<LocalDateTime> history = votingService.getVoteHistory(name);
        if (history == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Candidate not found");
        }
        return history;
    }
}
