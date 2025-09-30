package dev.danvega.dev2next;

import org.springaicommunity.mcp.annotation.McpTool;
import org.springaicommunity.mcp.annotation.McpToolParam;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class SessionTools {

    private final ConferenceRepository repository;

    public SessionTools(ConferenceRepository repository) {
        this.repository = repository;
    }

    @McpTool(name = "dev2Next-get-conference-data", description = "Get all conference data including sessions, tracks, rooms and conference details")
    public Conference getConferenceData() {
        return repository.getConference();
    }

    @McpTool(name = "dev2Next-get-sessions", description = "Get a list of all sessions")
    public List<Session> getSessions() {
        return repository.getConference().sessions();
    }

    @McpTool(name = "dev2Next-get-workshops", description = "Get a list of all workshop sessions")
    public List<Session> getWorkshops() {
        return repository.getConference().sessions().stream()
                .filter(session -> session.type() == SessionType.WORKSHOP)
                .toList();
    }

    @McpTool(name = "dev2Next-sessions-by-date", description = "Returns the count of sessions by date")
    public Map<String,Long> countSessionsByDate() {
        return repository.getConference().sessions().stream()
                .collect(Collectors.groupingBy(
                        Session::day,
                        Collectors.counting()
                ));
    }

    @McpTool(name = "dev2Next-sessions-by-track", description = "Returns the count of sessions for a specific track")
    public Map<String,Object> countSessionsByTrack(@McpToolParam String track) {
        long sessionCount = repository.getConference().sessions().stream()
                .filter(session -> session.track() != null && session.track().contains(track))
                .count();
        return Map.of("track", track, "count", sessionCount);
    }

    @McpTool(name = "dev2Next-sessions-by-type", description = "Returns the count of sessions by session type")
    public Map<String,Long> countSessionsByType() {
        return repository.getConference().sessions().stream()
                .collect(Collectors.groupingBy(
                        session -> session.type().getDisplayName(),
                        Collectors.counting()
                ));
    }

}
