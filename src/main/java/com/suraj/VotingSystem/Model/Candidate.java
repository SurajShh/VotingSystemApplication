package com.suraj.VotingSystem.Model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Candidate {
    private String name;
    private int voteCount;
    private List<LocalDateTime> voteHistory; // New field for tracking vote history

    public Candidate(String name) {
        this.name = name;
        this.voteCount = 0;
        this.voteHistory = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public int getVoteCount() {
        return voteCount;
    }

    public List<LocalDateTime> getVoteHistory() {
        return voteHistory;
    }

    public void incrementVote() {
        this.voteCount++;
        this.voteHistory.add(LocalDateTime.now()); // Record timestamp with each vote
    }

    public void setVoteCount(int voteCount) {
        this.voteCount = voteCount;
    }
}