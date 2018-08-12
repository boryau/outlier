package com.outlier.consumer.dao;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "median")
public class MedianPublisher {

    public MedianPublisher() {
    }

    public MedianPublisher(String publisherId, Long timestamp, Double median) {
        this.publisherId = publisherId;
        this.timestamp = timestamp;
        this.median = median;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String publisherId;

    private Long timestamp;

    private Double median;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPublisherId() {
        return publisherId;
    }

    public void setPublisherId(String publisherId) {
        this.publisherId = publisherId;
    }

    public Long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }

    public Double getMedian() {
        return median;
    }

    public void setMedian(Double median) {
        this.median = median;
    }
}
