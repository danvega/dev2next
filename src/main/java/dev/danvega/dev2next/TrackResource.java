package dev.danvega.dev2next;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springaicommunity.mcp.annotation.McpResource;
import org.springframework.stereotype.Component;

@Component
public class TrackResource {

    private final ConferenceRepository conferenceRepository;
    private final ObjectMapper objectMapper;

    public TrackResource(ConferenceRepository conferenceRepository, ObjectMapper objectMapper) {
        this.conferenceRepository = conferenceRepository;
        this.objectMapper = objectMapper;
    }

    @McpResource(
            uri = "tracks://all",
            name = "Conference Tracks",
            description = "Provides a list of all tracks available at the dev2next Conference"
    )
    public String getAllTracks() throws JsonProcessingException {
        return objectMapper.writeValueAsString(conferenceRepository.getConference().tracks());
    }
}