package com.vora.analytics.repository;

import com.vora.analytics.model.MemoItem;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

public interface MemoItemRepository extends MongoRepository<MemoItem, Long> {

    @Query("{id:?}")
    MemoItem findMemoItemBy(long id);

}
