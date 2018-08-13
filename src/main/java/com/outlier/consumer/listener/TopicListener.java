package com.outlier.consumer.listener;

import com.outlier.consumer.dao.MedianPublisher;
import com.outlier.consumer.dao.MedianPublisherRepository;
import com.outlier.consumer.data.SensorData;
import com.outlier.utils.MedianUtil;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

@Component
public class TopicListener {

    private Logger logger = Logger.getLogger(TopicListener.class);

    @Autowired
    MedianPublisherRepository medianPublisherRepository;

    public TopicListener(){

    }

    @KafkaListener(topics = "${kafka.topic}", groupId = "sensors")
    public void listen(SensorData sensorData){
        logger.info("TopicListener listen start for group sensonrs");
        boolean parseFail = false;
        Double median = MedianUtil.findMedian(sensorData);
        logger.info("TopicListener median is "+ median);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:dd:ss.SSS");
        Date publishDate = null;
        try {
            publishDate = sdf.parse(sensorData.getTime());
        } catch (ParseException e) {
            logger.error("TopicListener Date is not in correcrt format");
            parseFail = true;
            e.printStackTrace();
        }
        logger.info("TopicListener date is "+ publishDate);
        Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("GMT"));
        if(!parseFail) {
            cal.setTime(publishDate);
        }
        Long timestamp = cal.getTimeInMillis();
        logger.info("TopicListener timestamp is "+ timestamp);
        MedianPublisher medianPublisher = new MedianPublisher(sensorData.getPublisher(), timestamp, median);
        medianPublisherRepository.save(medianPublisher);
        logger.info("TopicListener listen end for group sensonrs");
    }
}
