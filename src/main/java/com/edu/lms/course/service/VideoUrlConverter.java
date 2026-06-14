package com.edu.lms.course.service;

import org.springframework.stereotype.Component;

@Component
public class VideoUrlConverter {

    public String toEmbedUrl(String rawUrl) {
        if (rawUrl == null || rawUrl.isBlank()) return null;

        // https://www.youtube.com/watch?v=VIDEO_ID
        if (rawUrl.contains("youtube.com/watch")) {
            String videoId = extractQueryParam(rawUrl, "v");
            if (videoId != null) {
                return "https://www.youtube.com/embed/" + videoId;
            }
        }

        // https://youtu.be/VIDEO_ID
        if (rawUrl.contains("youtu.be/")) {
            String id = rawUrl.substring(rawUrl.lastIndexOf("/") + 1);
            id = stripQueryParams(id);
            return "https://www.youtube.com/embed/" + id;
        }

        // https://vimeo.com/VIDEO_ID
        if (rawUrl.contains("vimeo.com/")) {
            String id = rawUrl.substring(rawUrl.lastIndexOf("/") + 1);
            id = stripQueryParams(id);
            return "https://player.vimeo.com/video/" + id;
        }

        // unknown format — return as-is, teacher knows what they're doing
        return rawUrl;
    }

    private String extractQueryParam(String url, String param) {
        String[] parts = url.split("[?&]");
        for (String part : parts) {
            if (part.startsWith(param + "=")) {
                return part.substring(param.length() + 1);
            }
        }
        return null;
    }

    private String stripQueryParams(String value) {
        int qIndex = value.indexOf("?");
        return qIndex != -1 ? value.substring(0, qIndex) : value;
    }
}
