package dev.danvega.dev2next;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class TrackResourceTest {

    @Autowired
    private TrackResource trackResource;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void shouldReturnDev2NextTracks() throws Exception {
        // When - we get all tracks
        String tracksJson = trackResource.getAllTracks();

        // Then - we should get JSON containing dev2next tracks
        assertThat(tracksJson).isNotEmpty();

        // And - we can parse it as a list
        List<String> tracks = objectMapper.readValue(tracksJson, new TypeReference<List<String>>() {});

        // And - it should contain the expected dev2next tracks
        assertThat(tracks).contains("Architecture", "Java", "AI", "Leadership", "Cloud", "General");
        assertThat(tracks).hasSize(6);
    }

    @Test
    void shouldLoadTrackResourceSuccessfully() {
        // Given - TrackResource should be loaded and initialized

        // When - we call the resource method
        // Then - it should not throw an exception
        assertThat(trackResource).isNotNull();
    }
}