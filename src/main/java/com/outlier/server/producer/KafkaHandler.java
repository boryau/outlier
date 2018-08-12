package com.outlier.server.producer;

import com.outlier.consumer.data.SensorData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class KafkaHandler {
    @Autowired
    private KafkaTemplate<String, SensorData> kafkaTemplate;

    @Value("${kafka.topic}")
    private String topic;


    public void sendMessage(SensorData request){

        kafkaTemplate.send(topic, request);

    }
}
