package com.outlier;

import com.outlier.consumer.dao.MedianPublisher;
import com.outlier.consumer.dao.MedianPublisherRepository;
import com.outlier.consumer.data.SensorData;
import com.outlier.consumer.listener.TopicListener;
import com.outlier.utils.DateUtil;
import com.outlier.utils.MedianUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest
public class OutlierApplicationTests {

	@Autowired
	TopicListener listener;

	@Autowired
	MedianPublisherRepository repository;


	@Test
	public void testListenerMedianCalc() {

		SensorData sensorData = new SensorData();
		sensorData.setPublisher("TestListenerMedianCalc");
		List<Integer> readings = new ArrayList<>();
		readings.add(1);
		readings.add(13);
		readings.add(192);
		readings.add(7);
		readings.add(13);
		readings.add(99);
		readings.add(1014);
		readings.add(4);
		sensorData.setReadings(readings);
		Double median = MedianUtil.findMedian(sensorData);
		assertTrue(median.equals(13.0));
		readings = new ArrayList<>();
		readings.add(2);
		readings.add(4);
		readings.add(8);
		readings.add(10);
		sensorData.setReadings(readings);
		median = MedianUtil.findMedian(sensorData);
		assertTrue(median.equals(6.0));

	}


	@Test
	public void testListener(){
		SensorData sensorData = new SensorData();
		sensorData.setPublisher("TestListener");
		sensorData.setTime(DateUtil.getCurrentDateString());
		List<Integer> readings = new ArrayList<>();
		readings.add(1);
		readings.add(13);
		readings.add(192);
		readings.add(7);
		readings.add(13);
		readings.add(99);
		readings.add(1014);
		readings.add(4);
		sensorData.setReadings(readings);
		listener.listen(sensorData);
		List<MedianPublisher> query = repository.findByPublisherIdOrderByTimestampDesc("TestListener", PageRequest.of(0, 1));
		assertTrue(query.get(0).getMedian().equals(13.0));

	}

}
