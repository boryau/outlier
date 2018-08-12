package com.outlier.server.controllers;


import com.outlier.consumer.dao.MedianPublisher;
import com.outlier.consumer.dao.MedianPublisherRepository;
import com.outlier.consumer.data.SensorData;
import com.outlier.server.data.EndToEndResponse;
import com.outlier.server.producer.KafkaHandler;
import com.outlier.utils.DateUtil;
import com.outlier.utils.MedianUtil;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = "endtoend")
public class EndToEndController {

    private Logger logger = Logger.getLogger(EndToEndController.class);

    @Autowired
    KafkaHandler kafkaHandler;

    @Autowired
    MedianPublisherRepository repository;

    private String publisherId = "EndToEndFirst";

    @RequestMapping(value = "", method = RequestMethod.GET)
    public ResponseEntity<EndToEndResponse> endToEnd(){
        logger.info("EndToEndController endToEnd start");
        if(repository.findByPublisherIdOrderByTimestampDesc(publisherId, PageRequest.of(0,1)).size() == 1){
            clearDB();
        }
        SensorData sensorData = new SensorData();
        List<Integer> readings = new ArrayList<>();
        readings.add(1);
        readings.add(25);
        readings.add(25);
        readings.add(30);
        sensorData.setReadings(readings);
        sensorData.setPublisher(publisherId);
        sensorData.setTime(DateUtil.getCurrentDateString());
        kafkaHandler.sendMessage(sensorData);

        readings = new ArrayList<>();
        readings.add(2);
        readings.add(29);
        readings.add(29);
        readings.add(40);
        sensorData.setReadings(readings);
        sensorData.setTime(DateUtil.getCurrentDateString());
        kafkaHandler.sendMessage(sensorData);
        EndToEndResponse response = new EndToEndResponse();
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        List<MedianPublisher> medianPublisherList = repository.findByPublisherIdOrderByTimestampDesc("EndToEndFirst", PageRequest.of(0,2));
        if(medianPublisherList.size() < 2){
            response.setMessage("EndToEnd falied. Num of data in DB does not match input");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
        List<Double> outlierList = MedianUtil.findAllOutliers(medianPublisherList);
        if(outlierList.size() >= 1 ){
            response.setMessage("EndToEnd falied. Outlier was found  when it sould be null");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }

        readings = new ArrayList<>();
        readings.add(2);
        readings.add(3);
        readings.add(3);
        readings.add(8);
        sensorData.setReadings(readings);
        sensorData.setTime(DateUtil.getCurrentDateString());
        kafkaHandler.sendMessage(sensorData);

        readings = new ArrayList<>();
        readings.add(2);
        readings.add(32);
        readings.add(32);
        readings.add(80);
        sensorData.setReadings(readings);
        sensorData.setTime(DateUtil.getCurrentDateString());
        kafkaHandler.sendMessage(sensorData);

        readings = new ArrayList<>();
        readings.add(2);
        readings.add(85);
        readings.add(85);
        readings.add(90);
        sensorData.setReadings(readings);
        sensorData.setTime(DateUtil.getCurrentDateString());
        kafkaHandler.sendMessage(sensorData);

        readings = new ArrayList<>();
        readings.add(2);
        readings.add(33);
        readings.add(33);
        readings.add(84);
        sensorData.setReadings(readings);
        sensorData.setTime(DateUtil.getCurrentDateString());
        kafkaHandler.sendMessage(sensorData);

        readings = new ArrayList<>();
        readings.add(2);
        readings.add(27);
        readings.add(27);
        readings.add(84);
        sensorData.setReadings(readings);
        sensorData.setTime(DateUtil.getCurrentDateString());
        kafkaHandler.sendMessage(sensorData);

        readings = new ArrayList<>();
        readings.add(2);
        readings.add(28);
        readings.add(28);
        readings.add(84);
        sensorData.setReadings(readings);
        sensorData.setTime(DateUtil.getCurrentDateString());
        kafkaHandler.sendMessage(sensorData);

        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        medianPublisherList = repository.findByPublisherIdOrderByTimestampDesc("EndToEndFirst", PageRequest.of(0,8));

        outlierList = MedianUtil.findAllOutliers(medianPublisherList);
        if(!outlierList.contains(3.0)|| !outlierList.contains(85.0)) {
            response.setMessage("EndToEnd falied.Outliers were not found correctly");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
        response.setOutliers(outlierList);
        response.setMessage("EndToEnd finished succesfully. Outliers were found");
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    public void clearDB(){
        repository.deleteByPublisherId(publisherId);
    }
}
