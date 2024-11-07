package com.suraj.VotingSystem;

import com.suraj.VotingSystem.Controller.VotingController;
import com.suraj.VotingSystem.Service.VotingService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.web.server.ResponseStatusException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class VotingControllerTests {

    @Mock
    private VotingService votingService;

    @InjectMocks
    private VotingController votingController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testEnterCandidate() {
        when(votingService.addCandidate("Ajay")).thenReturn(true);
        assertEquals("Candidate added successfully", votingController.enterCandidate("Ajay"));
    }

    @Test
    void testCastVote() {
        when(votingService.castVote("Ajay")).thenReturn(true);
        assertEquals("Vote cast successfully", votingController.castVote("Ajay"));
    }

    @Test
    void testCountVote() {
        when(votingService.getVoteCount("Ajay")).thenReturn(5);
        assertEquals("Vote count for Ajay: 5", votingController.countVote("Ajay"));
    }

    @Test
    void testGetWinner() {
        when(votingService.getWinner()).thenReturn("Ajay");
        assertEquals("Winner: Ajay", votingController.getWinner());
    }

    // Test for listing all candidates and their votes
    @Test
    void testListVote() {
        Map<String, Integer> candidates = Map.of("Ajay", 3, "Vijay", 2);
        when(votingService.listVotes()).thenReturn(candidates);

        Map<String, Integer> result = votingController.listVote();
        assertEquals(2, result.size());
        assertEquals(3, result.get("Ajay"));
        assertEquals(2, result.get("Vijay"));
    }

    // Test for removing a candidate
    @Test
    void testRemoveCandidate() {
        when(votingService.removeCandidate("Ajay")).thenReturn(true);
        assertEquals("Candidate removed successfully", votingController.removeCandidate("Ajay"));
        
        when(votingService.removeCandidate("Unknown")).thenReturn(false);
        assertEquals("Candidate not found", votingController.removeCandidate("Unknown"));
    }

    // Test for clearing all votes
    @Test
    void testClearAllVotes() {
        doNothing().when(votingService).clearAllVotes();
        assertEquals("All votes have been reset to zero", votingController.clearAllVotes());
        verify(votingService, times(1)).clearAllVotes();
    }

    // Test for getting detailed voting statistics
    @Test
    void testGetStatistics() {
        Map<String, Object> stats = new HashMap<>();
        stats.put("totalVotes", 10);
        stats.put("averageVotes", 2.5);
        stats.put("votePercentages", Map.of("Ajay", 50.0, "Vijay", 50.0));

        when(votingService.getStatistics()).thenReturn(stats);

        Map<String, Object> result = votingController.getStatistics();
        assertEquals(10, result.get("totalVotes"));
        assertEquals(2.5, result.get("averageVotes"));
        assertEquals(Map.of("Ajay", 50.0, "Vijay", 50.0), result.get("votePercentages"));
    }

    // Test for retrieving voting history for a candidate
    @Test
    void testGetVoteHistory() {
        List<LocalDateTime> history = List.of(
            LocalDateTime.of(2024, 11, 7, 10, 0),
            LocalDateTime.of(2024, 11, 7, 11, 0)
        );

        when(votingService.getVoteHistory("Ajay")).thenReturn(history);
        List<LocalDateTime> result = votingController.getVoteHistory("Ajay");
        assertEquals(2, result.size());
        assertEquals(history, result);

        // Test for a candidate with no history
        when(votingService.getVoteHistory("Unknown")).thenReturn(null);
        assertThrows(ResponseStatusException.class, () -> votingController.getVoteHistory("Unknown"));
    }
}
