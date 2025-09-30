package dev.danvega.dev2next;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class SessionToolsTest {

    @Autowired
    private SessionTools sessionTools;

    @Test
    void shouldLoadSessionsFromJson() {
        // Given - SessionTools should be loaded with data from sessions.json

        // When - we get all sessions
        List<Session> sessions = sessionTools.getSessions();

        // Then - we should have the expected number of sessions
        assertThat(sessions).hasSize(99);

        // And - sessions should have the correct structure
        Session firstSession = sessions.get(0);
        assertThat(firstSession.title()).isNotEmpty();
        assertThat(firstSession.day()).matches("\\d{4}-\\d{2}-\\d{2}"); // ISO date format
        assertThat(firstSession.time()).matches("\\d{2}:\\d{2}"); // 24-hour time format
        assertThat(firstSession.duration()).isNotEmpty();
        assertThat(firstSession.type()).isIn(SessionType.values());
        assertThat(firstSession.speakers()).isNotEmpty();
        assertThat(firstSession.room()).isNotEmpty();
        assertThat(firstSession.track()).isNotEmpty();
    }

    @Test
    void shouldCountSessionsByTrack() {
        // Given - we have sessions with different tracks

        // When - we count sessions for Java track
        var result = sessionTools.countSessionsByTrack("Java");

        // Then - we should get a count result
        assertThat(result).containsKey("track");
        assertThat(result).containsKey("count");
        assertThat(result.get("track")).isEqualTo("Java");
        assertThat((Long) result.get("count")).isGreaterThan(0);
    }

    @Test
    void shouldCountSessionsByDate() {
        // Given - we have sessions across multiple dates

        // When - we count sessions by date
        var sessionCountsByDate = sessionTools.countSessionsByDate();

        // Then - we should have counts for each date
        assertThat(sessionCountsByDate).isNotEmpty();
        assertThat(sessionCountsByDate).containsKey("2025-09-29");
        assertThat(sessionCountsByDate.values()).allSatisfy(count ->
            assertThat(count).isGreaterThan(0));

        // And - total sessions should equal our expected count
        long totalSessions = sessionCountsByDate.values().stream()
                .mapToLong(Long::longValue)
                .sum();
        assertThat(totalSessions).isEqualTo(99);
    }

    @Test
    void shouldHaveValidSessionTypes() {
        // Given - we have loaded sessions
        List<Session> sessions = sessionTools.getSessions();

        // When - we check all session types
        // Then - all session types should be valid enum values
        assertThat(sessions).allSatisfy(session ->
            assertThat(session.type()).isIn(SessionType.values()));

        // And - we should have all expected session types represented
        List<SessionType> sessionTypes = sessions.stream()
                .map(Session::type)
                .distinct()
                .toList();

        assertThat(sessionTypes).contains(SessionType.WORKSHOP, SessionType.KEYNOTE, SessionType.TALK);
    }

    @Test
    void shouldHaveValidConferenceMetadata() {
        // Given - SessionTools loads conference data

        // When - we get conference info
        Conference conference = sessionTools.getConferenceData();

        // Then - conference should have correct metadata
        assertThat(conference.name()).isEqualTo("dev2next");
        assertThat(conference.year()).isEqualTo(2025);
        assertThat(conference.location()).contains("Colorado Springs");
        assertThat(conference.dates()).hasSize(4);
        assertThat(conference.tracks()).contains("Java", "Architecture", "AI", "Leadership", "Cloud");
        assertThat(conference.rooms()).contains("Salon DE", "Salon ABC", "Salon FGH");
        assertThat(conference.sessions()).hasSize(99);
    }

    @Test
    void shouldCountSessionsByType() {
        // Given - we have sessions of different types

        // When - we count sessions by type
        var sessionCountsByType = sessionTools.countSessionsByType();

        // Then - we should have counts for each session type
        assertThat(sessionCountsByType).isNotEmpty();
        assertThat(sessionCountsByType).containsKey("Workshop");
        assertThat(sessionCountsByType).containsKey("Keynote");
        assertThat(sessionCountsByType).containsKey("Talk");

        // And - all counts should be positive
        assertThat(sessionCountsByType.values()).allSatisfy(count ->
            assertThat(count).isGreaterThan(0));

        // And - total sessions should equal our expected count
        long totalSessions = sessionCountsByType.values().stream()
                .mapToLong(Long::longValue)
                .sum();
        assertThat(totalSessions).isEqualTo(99);

        // And - manually verify counts match actual data
        List<Session> allSessions = sessionTools.getSessions();
        long workshopCount = allSessions.stream()
                .filter(s -> s.type() == SessionType.WORKSHOP)
                .count();
        assertThat(sessionCountsByType.get("Workshop")).isEqualTo(workshopCount);
    }

    @Test
    void shouldFindJavaSessions() {
        // Given - we have sessions with Java track
        List<Session> allSessions = sessionTools.getSessions();

        // When - we filter for Java sessions manually (since no direct method exists)
        List<Session> javaSessions = allSessions.stream()
                .filter(session -> session.track() != null && session.track().contains("Java"))
                .toList();

        // Then - we should find Java sessions
        assertThat(javaSessions).isNotEmpty();

        // And - all sessions should contain Java in their tracks
        assertThat(javaSessions).allSatisfy(session ->
            assertThat(session.track()).contains("Java"));

        // And - count should match our tool method
        var javaCount = sessionTools.countSessionsByTrack("Java");
        assertThat((Long) javaCount.get("count")).isEqualTo(javaSessions.size());
    }

    @Test
    void shouldGetWorkshopSessions() {
        // Given - we have workshop sessions in the data

        // When - we get all workshops
        List<Session> workshops = sessionTools.getWorkshops();

        // Then - we should have exactly 4 workshops
        assertThat(workshops).hasSize(4);

        // And - all sessions should be of type Workshop
        assertThat(workshops).allSatisfy(session ->
            assertThat(session.type()).isEqualTo(SessionType.WORKSHOP));

        // And - workshops should have valid structure
        assertThat(workshops).allSatisfy(session -> {
            assertThat(session.title()).isNotEmpty();
            assertThat(session.duration()).isEqualTo("8 hr");
            assertThat(session.speakers()).isNotEmpty();
        });
    }
}