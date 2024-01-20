package com.hwiseo.kafka.controller;

import com.hwiseo.kafka.entity.UserEventVo;
import com.hwiseo.kafka.service.ProduceService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*") // 다른 도메인에서도 호출할 수 있도록
@Slf4j
@RequiredArgsConstructor
public class ProduceController {

    private final ProduceService produceService;

    @GetMapping("/api/select")
    public void selectColor(@RequestHeader("user-agent") String userAgentName,
                            @RequestParam(value = "color") String colorName,
                            @RequestParam(value = "user") String username) {

        // 시간 적재를 위해 선언
        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));

        UserEventVo eventVo = new UserEventVo(timestamp, userAgentName, colorName, username);
        produceService.selectColor(eventVo);
    }
}
