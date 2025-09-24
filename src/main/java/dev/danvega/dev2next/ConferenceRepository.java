package dev.danvega.dev2next;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.io.InputStream;

@Repository
public class ConferenceRepository {

    private static final Logger log = LoggerFactory.getLogger(ConferenceRepository.class);
    private Conference conference;
    private final ObjectMapper objectMapper;

    public ConferenceRepository(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    public Conference getConference() {
        return conference;
    }

    @PostConstruct
    public void init() {
        log.info("Loading Conference data from JSON file 'sessions.json'");
        try (InputStream inputStream = TypeReference.class.getResourceAsStream("/data/sessions.json")) {
            var jsonNode = objectMapper.readTree(inputStream);
            var conferenceNode = jsonNode.get("conference");
            this.conference = objectMapper.treeToValue(conferenceNode, Conference.class);
            log.info("Conference data loaded successfully: {} sessions, {} tracks",
                    conference.sessions() != null ? conference.sessions().size() : 0,
                    conference.tracks() != null ? conference.tracks().size() : 0);
        } catch (IOException e) {
            log.error("Failed to read JSON data", e);
            throw new RuntimeException("Failed to read JSON data", e);
        }
    }

}