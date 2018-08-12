package com.outlier.consumer.dao;

import org.springframework.data.repository.CrudRepository;

import javax.transaction.Transactional;
import java.util.List;

public interface MedianPublisherRepository  extends CrudRepository<MedianPublisher, Long>{

    public List<MedianPublisher> findByPublisherIdOrderByTimestampDesc(String publishedId, org.springframework.data.domain.Pageable pageable);

    @Transactional
    public void deleteByPublisherId(String publisherId);
}
