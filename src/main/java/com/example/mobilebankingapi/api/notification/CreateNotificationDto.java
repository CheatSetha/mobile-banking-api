package com.example.mobilebankingapi.api.notification;

import com.fasterxml.jackson.annotation.JsonProperty;

public record CreateNotificationDto(@JsonProperty("included_segments") String[] includedSegments,
                                    ContentDto contents) {
}
