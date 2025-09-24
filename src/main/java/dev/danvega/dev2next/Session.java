package dev.danvega.dev2next;

import java.util.List;

public record Session(
        String day,
        String time,
        String duration,
        String title,
        SessionType type,
        String[] speakers,
        String room,
        List<String> track
) {
}
