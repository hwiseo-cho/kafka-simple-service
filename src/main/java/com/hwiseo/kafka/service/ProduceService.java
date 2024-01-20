package com.hwiseo.kafka.service;

import com.google.gson.Gson;
import com.hwiseo.kafka.entity.UserEventVo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProduceService {

    private final KafkaTemplate<String, String> kafkaTemplate;

    public void selectColor(UserEventVo eventVo) {
        String jsonColorLog = new Gson().toJson(eventVo);
        kafkaTemplate.send("select-color", jsonColorLog).whenComplete((result, error) -> {
            // success
            if(error == null) {
                log.info("ProduceService success {}", result);
            }
            // fail
            else {
                log.error("ProduceService fail {}", error);
            }
        });
    }
}
