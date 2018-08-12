package com.outlier.server.controllers;


import com.outlier.consumer.dao.MedianPublisher;
import com.outlier.consumer.dao.MedianPublisherRepository;
import com.outlier.server.data.OutlierResponse;
import com.outlier.utils.DateUtil;
import com.outlier.utils.MedianUtil;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "query")
public class OutlierController
{

    @Autowired
    MedianPublisherRepository repository;

    private Logger logger = Logger.getLogger(OutlierController.class);

    @RequestMapping(value = "", method = RequestMethod.GET)
    public ResponseEntity<OutlierResponse> queryOutliers(@RequestParam("N") int num, @RequestParam("publisher") String publisherId){
        logger.info("OutlierController queryOutliers start");
        OutlierResponse response = new OutlierResponse();
        List<MedianPublisher> medianPublisherList = repository.findByPublisherIdOrderByTimestampDesc(publisherId, PageRequest.of(0,num));
        response.setNum(num);
        response.setCurrentTime(DateUtil.getCurrentDateString());
        if(medianPublisherList.isEmpty()){
            return ResponseEntity.status(HttpStatus.OK).body(response);
        }
        List<Double> outlierList = MedianUtil.findAllOutliers(medianPublisherList);

        response.setOutliers(outlierList);
        logger.info("OutlierController queryOutliers end");
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
