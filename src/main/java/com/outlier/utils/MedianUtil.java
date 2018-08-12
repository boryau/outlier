package com.outlier.utils;


import com.outlier.consumer.dao.MedianPublisher;
import com.outlier.consumer.data.SensorData;
import org.apache.commons.math3.stat.descriptive.rank.Median;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


public class MedianUtil {

    public MedianUtil(){

    }

    public static Double findMedian(SensorData sensorData){
        double[] dataArray = sensorData.getReadings().stream().mapToDouble(Integer::doubleValue).toArray();
        return findMedian(dataArray);
    }


    public static Double findMedian(double[] dataArray){
        Median median = new Median();
        double medianValue = median.evaluate(dataArray);
        return medianValue;
    }

    public static List<Double> findAllOutliers(List<MedianPublisher> medianPublishers){
        List<Double> response = new ArrayList<>();
        List<MedianPublisher> sortedList = medianPublishers.stream().sorted(((o1, o2) ->
                o1.getMedian().compareTo(o2.getMedian()))).collect(Collectors.toList());

        int size = sortedList.size();
        List<MedianPublisher> list1, list2;
        if (sortedList.size() % 2 == 0) {
            list1 = sortedList.subList(0, size / 2);
            list2 = sortedList.subList(size / 2, sortedList.size());
        } else {
            list1 = sortedList.subList(0, size / 2);
            list2 = sortedList.subList(size / 2 + 1, size);
        }
        double q1 = findMedian(list1.stream().mapToDouble(p -> p.getMedian().doubleValue() ).toArray());
        double q3 = findMedian(list2.stream().mapToDouble(p -> p.getMedian().doubleValue()).toArray());
        double iqr = q3 - q1;
        double lowerFence = q1 - 1.5 * iqr;
        double upperFence = q3 + 1.5 * iqr;

        response = medianPublishers.stream().filter(p-> p.getMedian() < lowerFence || p.getMedian() > upperFence).map(p ->p.getMedian()).collect(Collectors.toList());
        return response;
    }
}
