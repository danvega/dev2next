package dev.danvega.dev2next;

import io.modelcontextprotocol.spec.McpSchema.GetPromptResult;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class ConferencePromptsTest {

    @Autowired
    private ConferencePrompts conferencePrompts;

    @Test
    void shouldGenerateDev2NextSessionsPerDayPrompt() {
        // When - we get the sessions per day prompt
        GetPromptResult result = conferencePrompts.sessionsPerDayPrompt();

        // Then - it should be properly configured for dev2next
        assertThat(result.description()).isEqualTo("dev2next 2025 sessions analysis by day");
        assertThat(result.messages()).hasSize(1);

        String messageContent = ((io.modelcontextprotocol.spec.McpSchema.TextContent) result.messages().get(0).content()).text();
        assertThat(messageContent).contains("dev2next 2025 conference");
        assertThat(messageContent).contains("dev2Next-sessions-by-date tool");
        assertThat(messageContent).contains("How many sessions are scheduled for each day");
        assertThat(messageContent).contains("What is the total number of sessions");

        // And - it should not contain references to the old conference
        assertThat(messageContent).doesNotContain("Commit your Code");
        assertThat(messageContent).doesNotContain("CYC");
        assertThat(messageContent).doesNotContain("cyc-sessions-by-date");
    }

    @Test
    void shouldBeSpringComponent() {
        // Given - ConferencePrompts should be auto-wired as Spring component
        // Then - it should not be null
        assertThat(conferencePrompts).isNotNull();
    }
}