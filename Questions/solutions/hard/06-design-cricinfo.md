# Designing a Cricket Information System like CricInfo

## Requirements
1. The Cricinfo system should provide information about cricket matches, teams, players, and live scores.
2. Users should be able to view the schedule of upcoming matches and the results of completed matches.
3. The system should allow users to search for specific matches, teams, or players.
4. Users should be able to view detailed information about a particular match, including the scorecard, commentary, and statistics.
5. The system should support real-time updates of live scores and match information.
6. The system should handle concurrent access to match data and ensure data consistency.
7. The system should be scalable and able to handle a large volume of user requests.
8. The system should be extensible to accommodate new features and enhancements in the future.


```java
class Match {
    private String matchId;
    private String title;
    private Date startTime;
    private String venue;
    private List<Team> teams;
    private MatchStatus status;
    private Scorecard scorecard;
    private List<Commentary> commentaries;
    private List<Statistic> statistics;

    // Constructor, getters, and setters
}

enum MatchStatus {
    UPCOMING, LIVE, COMPLETED
}

class Team {
    private String teamId;
    private String name;
    private List<Player> players;

    // Constructor, getters, and setters
}

class Player {
    private String playerId;
    private String name;
    private String role;

    // Constructor, getters, and setters
}

class Scorecard {
    private List<Inning> innings;

    // Constructor, getters, and setters
}

class Inning {
    private String battingTeam;
    private String bowlingTeam;
    private List<Over> overs;

    // Constructor, getters, and setters
}

class Over {
    private int overNumber;
    private List<Ball> balls;

    // Constructor, getters, and setters
}

class Ball {
    private int ballNumber;
    private String bowler;
    private String batsman;
    private int runs;
    private String wicket;

    // Constructor, getters, and setters
}

class Commentary {
    private String text;
    private Date timestamp;

    // Constructor, getters, and setters
}

class Statistic {
    private String type;
    private String value;

    // Constructor, getters, and setters
}

interface MatchRepository {
    List<Match> getAllMatches();
    Match getMatchById(String matchId);
    void saveMatch(Match match);
    // Other methods for match persistence
}

interface TeamRepository {
    List<Team> getAllTeams();
    Team getTeamById(String teamId);
    // Other methods for team persistence
}

interface PlayerRepository {
    List<Player> getAllPlayers();
    Player getPlayerById(String playerId);
    // Other methods for player persistence
}

class CricinfoService {
    private MatchRepository matchRepository;
    private TeamRepository teamRepository;
    private PlayerRepository playerRepository;

    // Constructor injection
    public CricinfoService(MatchRepository matchRepository, TeamRepository teamRepository,
                           PlayerRepository playerRepository) {
        this.matchRepository = matchRepository;
        this.teamRepository = teamRepository;
        this.playerRepository = playerRepository;
    }

    public List<Match> getUpcomingMatches() {
        List<Match> allMatches = matchRepository.getAllMatches();
        return allMatches.stream()
                .filter(match -> match.getStatus() == MatchStatus.UPCOMING)
                .collect(Collectors.toList());
    }

    public List<Match> getCompletedMatches() {
        List<Match> allMatches = matchRepository.getAllMatches();
        return allMatches.stream()
                .filter(match -> match.getStatus() == MatchStatus.COMPLETED)
                .collect(Collectors.toList());
    }

    public Match getMatchDetails(String matchId) {
        return matchRepository.getMatchById(matchId);
    }

    public void updateLiveScore(String matchId, Scorecard updatedScorecard) {
        Match match = matchRepository.getMatchById(matchId);
        if (match != null && match.getStatus() == MatchStatus.LIVE) {
            match.setScorecard(updatedScorecard);
            matchRepository.saveMatch(match);
            notifyObservers(match);
        }
    }

    public List<Match> searchMatches(String keyword) {
        List<Match> allMatches = matchRepository.getAllMatches();
        return allMatches.stream()
                .filter(match -> match.getTitle().contains(keyword))
                .collect(Collectors.toList());
    }

    public List<Team> searchTeams(String keyword) {
        List<Team> allTeams = teamRepository.getAllTeams();
        return allTeams.stream()
                .filter(team -> team.getName().contains(keyword))
                .collect(Collectors.toList());
    }

    public List<Player> searchPlayers(String keyword) {
        List<Player> allPlayers = playerRepository.getAllPlayers();
        return allPlayers.stream()
                .filter(player -> player.getName().contains(keyword))
                .collect(Collectors.toList());
    }

    private List<Observer> observers = new ArrayList<>();

    public void addObserver(Observer observer) {
        observers.add(observer);
    }

    public void removeObserver(Observer observer) {
        observers.remove(observer);
    }

    private void notifyObservers(Match match) {
        for (Observer observer : observers) {
            observer.update(match);
        }
    }
}

interface Observer {
    void update(Match match);
}

class LiveScoreUpdater implements Observer {
    @Override
    public void update(Match match) {
        // Update live score display
        displayLiveScore(match);
    }

    private void displayLiveScore(Match match) {
        // Display the updated live score
    }
}
```

Explanation:
1. The `Match` class represents a cricket match, with properties such as match ID, title, start time, venue, teams, status, scorecard, commentaries, and statistics.

2. The `MatchStatus` enum defines the possible statuses of a match: upcoming, live, or completed.

3. The `Team` class represents a cricket team, with properties such as team ID, name, and a list of players.

4. The `Player` class represents a cricket player, with properties such as player ID, name, and role.

5. The `Scorecard` class represents the scorecard of a match, containing a list of innings.

6. The `Inning` class represents an innings in a match, with properties such as batting team, bowling team, and a list of overs.

7. The `Over` class represents an over in an innings, with properties such as over number and a list of balls.

8. The `Ball` class represents a ball in an over, with properties such as ball number, bowler, batsman, runs scored, and wicket information.

9. The `Commentary` class represents a commentary entry for a match, with properties such as text and timestamp.

10. The `Statistic` class represents a statistical entry for a match, with properties such as type and value.

11. The `MatchRepository`, `TeamRepository`, and `PlayerRepository` interfaces define the contracts for match, team, and player persistence, respectively. They can be implemented using various data storage technologies such as databases or in-memory storage.

12. The `CricinfoService` class encapsulates the core functionality of the Cricinfo system. It depends on the `MatchRepository`, `TeamRepository`, and `PlayerRepository` interfaces for data persistence.

13. The `getUpcomingMatches` and `getCompletedMatches` methods retrieve the list of upcoming and completed matches, respectively, by filtering the matches based on their status.

14. The `getMatchDetails` method retrieves the detailed information about a particular match, including the scorecard, commentary, and statistics.

15. The `updateLiveScore` method updates the live score of a match by updating the scorecard and notifying the observers.

16. The `searchMatches`, `searchTeams`, and `searchPlayers` methods allow users to search for matches, teams, or players based on a keyword.

17. The `Observer` interface defines the contract for objects that want to be notified when a match's live score is updated. The `LiveScoreUpdater` class is an example implementation of the `Observer` interface.

Design Patterns Used:
- Repository Pattern: The `MatchRepository`, `TeamRepository`, and `PlayerRepository` interfaces follow the Repository pattern. They provide an abstraction layer for data persistence and allow the `CricinfoService` to interact with the data storage without knowing the implementation details.

- Dependency Injection: The `CricinfoService` class uses constructor injection to receive its dependencies (`MatchRepository`, `TeamRepository`, `PlayerRepository`). This promotes loose coupling and enables easier testing and maintainability.

- Observer Pattern: The `Observer` interface and the `LiveScoreUpdater` class implement the Observer pattern. The `CricinfoService` acts as the subject, and the `LiveScoreUpdater` acts as an observer. When the live score of a match is updated, the `CricinfoService` notifies all the registered observers, allowing them to update their displays accordingly.

Scalability and Concurrency:
- To handle concurrent access to match data and ensure data consistency, proper synchronization mechanisms should be implemented in the `CricinfoService` class and the repository implementations. This can be achieved using techniques such as pessimistic locking or optimistic locking.

- To scale the system to handle a large volume of user requests, horizontal scaling techniques can be employed. This involves deploying multiple instances of the Cricinfo service and using load balancers to distribute the incoming requests evenly.

- Caching mechanisms can be implemented to improve performance and reduce the load on the underlying data storage. Frequently accessed data, such as match details and live scores, can be cached to provide faster access.

Extensibility:
- The design is extensible to accommodate new features and enhancements in the future. For example, new types of statistics or additional match information can be added by extending the existing classes or introducing new classes.

- The use of interfaces and dependency injection allows for easy substitution of different implementations, such as using different data storage technologies or integrating with external services.

This low-level design provides a basic structure for the Cricinfo system that meets the given requirements. It can be further enhanced with additional features such as user authentication, personalized experiences, push notifications, and integration with social media platforms.

The use of design patterns such as Repository, Dependency Injection, and Observer promotes modularity, testability, and maintainability of the codebase. The scalability and concurrency considerations ensure that the system can handle a large volume of user requests efficiently.

Overall, this design aims to provide a robust and scalable solution for the Cricinfo system, taking into account the requirements of match information, live scores, search functionality, real-time updates, and extensibility.