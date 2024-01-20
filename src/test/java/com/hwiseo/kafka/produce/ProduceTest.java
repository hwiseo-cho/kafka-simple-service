package com.hwiseo.kafka.produce;

import com.hwiseo.kafka.controller.ProduceController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class ProduceTest {

    @Autowired
    ProduceController produceController;

    @Test
    void 프로듀스_테스트() {

        System.out.println("gddasdasdasadsdasdas");
    }
}
