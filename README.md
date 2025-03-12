# Voting API Application

## Overview
This application is a voting system API implemented in Java with Spring Boot. It provides endpoints to manage candidates, cast votes, view results, and access voting statistics. It is designed to handle multiple users voting simultaneously.

## Requirements
- Java 11 or higher
- Maven
- Spring Boot

## Setup Instructions
1. Clone the repository:

   git clone <repository-url>
   cd VotingSystemApplication

2. Build the project with Maven:

    mvn clean install

3. Run the application:

    mvn spring-boot:run

4. Access the API at http://localhost:8080/api


Features Implemented:
    1. Enter Candidate (/entercandidate?name=<candidate_name>): Adds a new candidate with an initial vote count of 0.
    2.Cast Vote (/castvote?name=<candidate_name>): Casts a vote for the specified candidate.
    3.Count Vote (/countvote?name=<candidate_name>): Retrieves the current vote count for the specified candidate.
    4.List All Votes (/listvote): Returns a list of all candidates with their respective vote counts.
    5.Get Winner (/getwinner): Returns the candidate with the highest votes.
    6.Remove Candidate (/removecandidate?name=<candidate_name>): Removes a specified candidate from the system.
    7.Clear All Votes (/clearallvotes): Resets all vote counts to zero.
    8.Get Voting Statistics (/getstatistics): Provides detailed statistics, such as total votes and percentages for each candidate.
    9.Vote History (/votehistory?name=<candidate_name>): Retrieves the vote history with timestamps for the specified candidate.
