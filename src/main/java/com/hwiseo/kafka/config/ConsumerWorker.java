package com.hwiseo.kafka.config;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.TopicPartition;
import org.apache.kafka.common.errors.WakeupException;
import org.springframework.stereotype.Component;

import java.lang.module.Configuration;
import java.time.Duration;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@RequiredArgsConstructor
@Component
public class ConsumerWorker implements Runnable {

    private static Map<Integer, List<String>> bufferString = new ConcurrentHashMap<>();
    private static Map<Integer, Long> currentFileOffset = new ConcurrentHashMap<>();

    private final static int FLUSH_RECORD_COUNT = 10;
    private Properties prop;
    private String topic;
    private String threadName;
    private KafkaConsumer<String, String> consumer;

    public ConsumerWorker(Properties prop, String topic, int number) {
        this.prop = prop;
        this.topic = topic;
        this.threadName = "consumer-thread-" + number;
    }

    @Override
    public void run() {
        Thread.currentThread().setName(threadName);
        consumer = new KafkaConsumer<>(prop);
        consumer.subscribe(Arrays.asList(topic));

        try {
            while (true) {
                ConsumerRecords<String, String> records = consumer.poll(Duration.ofSeconds(1));

                for (ConsumerRecord<String, String> record : records) {
                    addMysqlFileBuffer(record);
                }

                saveBufferToMysqlFile(consumer.assignment());
            }
        } catch (WakeupException e) {
            log.warn("Wakeup consumer");
        } catch (Exception e) {
            log.error(e.getMessage());
        } finally {
            consumer.close();
        }
    }

    private void addMysqlFileBuffer(ConsumerRecord<String, String> record) {
        List<String> buffer = bufferString.getOrDefault(record.partition(), new ArrayList<>());
        buffer.add(record.value());
        bufferString.put(record.partition(), buffer);

        if (buffer.size() == 1) {
            currentFileOffset.put(record.partition(), record.offset());
        }
    }

    private void saveBufferToMysqlFile(Set<TopicPartition> partitions) {
        partitions.forEach(p -> checkFlushCount(p.partition()));
    }

    private void checkFlushCount(int partitionNo) {
        if(bufferString.get(partitionNo) != null) {
            if(bufferString.get(partitionNo).size() > FLUSH_RECORD_COUNT - 1) {
                save(partitionNo);
            }
        }
    }

    private void save(int partitionNo) {
        if (bufferString.get(partitionNo).size() > 0) {
            try {
                String filename = "/data/color-" + partitionNo + "-" + currentFileOffset.get(partitionNo) + ".log";
            } catch (Exception e) {
                log.error(e.getMessage());
            }
        }
    }


}
