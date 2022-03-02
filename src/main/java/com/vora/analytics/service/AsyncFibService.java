package com.vora.analytics.service;

import com.vora.analytics.model.MemoItem;
import com.vora.analytics.repository.MemoItemRepository;
import org.springframework.data.domain.Sort;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

@Service
public class AsyncFibService {

    @Async
    public void saveToDB(HashMap<Long,Long> fibMap, MemoItemRepository memoItemRepo){
        System.out.println("Async - Currently Executing in: " + Thread.currentThread().getName());
        try {
            List<MemoItem> items = memoItemRepo.findAll(
                    Sort.by(Sort.Direction.ASC,"id"));
            HashMap<Long,Long> dbMap = new HashMap<Long,Long>();
            items.forEach((m)->{
                dbMap.put(m.getId(),m.getValue());
            });
            fibMap.forEach((k, v) -> {
                if(!dbMap.containsKey(k)) {
                    memoItemRepo.save(new MemoItem(k, v));
                }
            });

        } catch (Exception e) {
            System.out.println("Failed to save records!");
        }
    }

}
