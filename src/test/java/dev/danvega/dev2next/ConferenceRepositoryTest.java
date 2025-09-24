package dev.danvega.dev2next;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class ConferenceRepositoryTest {

    @Autowired
    private ConferenceRepository conferenceRepository;

    @Test
    void shouldLoadConferenceDataSuccessfully() {
        // When - we get the conference data
        Conference conference = conferenceRepository.getConference();

        // Then - it should be properly loaded
        assertThat(conference).isNotNull();
        assertThat(conference.name()).isEqualTo("dev2next");
        assertThat(conference.year()).isEqualTo(2025);
        assertThat(conference.location()).contains("Colorado Springs");
        assertThat(conference.dates()).hasSize(4);
        assertThat(conference.tracks()).contains("Java", "Architecture", "AI", "Leadership", "Cloud", "General");
        assertThat(conference.rooms()).contains("Salon DE", "Salon ABC", "Salon FGH");
        assertThat(conference.sessions()).hasSize(28);
    }

    @Test
    void shouldReturnSameInstanceOnMultipleCalls() {
        // When - we call getConference multiple times
        Conference conference1 = conferenceRepository.getConference();
        Conference conference2 = conferenceRepository.getConference();

        // Then - it should return the same instance (singleton behavior)
        assertThat(conference1).isSameAs(conference2);
    }
}