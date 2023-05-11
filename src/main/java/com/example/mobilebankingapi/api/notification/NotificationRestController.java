package com.example.mobilebankingapi.api.notification;

import com.example.mobilebankingapi.base.BaseRest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/v1/notification")
@RequiredArgsConstructor
public class NotificationRestController {
    private final NotificatinoService notificatinoService;
    @PostMapping
    public BaseRest<?> postNotification(@RequestBody CreateNotificationDto notificationDto){
        notificatinoService.pushNotification(notificationDto);
        return BaseRest.builder()
                .status(true)
                .code(HttpStatus.OK.value())
                .message("Notification has been sent")
                .timestamp(LocalDateTime.now())
                .data(notificationDto.contents())
                .build();

    }
}
