package com.vora.analytics.controller;

import com.vora.analytics.model.MemoItem;
import com.vora.analytics.repository.MemoItemRepository;
import com.vora.analytics.service.AsyncFibService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class FibonacciController {

    private HashMap<Long,Long> fibMap;

    @Autowired
    private MemoItemRepository memoItemRepo;

    @Autowired
    private AsyncFibService asyncFibService;

    @PostConstruct
    public void init(){
        fibMap = new HashMap<Long,Long>();
        List<MemoItem> items = memoItemRepo.findAll(
                Sort.by(Sort.Direction.ASC,"id"));
        items.forEach((m)->{
            fibMap.put(m.getId(),m.getValue());
        });
        System.out.println("======================================================================================================================================================================================================================================");
        System.out.println("Initial HashMap of Fibonacci Numbers - Size:");
        System.out.println(fibMap.size());
        System.out.println("======================================================================================================================================================================================================================================");
    }

    @GetMapping("/fibonacci")
    public ResponseEntity<List<MemoItem>> getAllMemoItems() {
        return ResponseEntity.ok().body(
                memoItemRepo.findAll(
                        Sort.by(Sort.Direction.ASC,"id")));
    }

    @GetMapping("/fibonacci/{id}")
    public ResponseEntity<MemoItem> getMemoItemById(@PathVariable(value = "id") Long id) {
        ResponseEntity<MemoItem> ret = ResponseEntity.ok().body(fibonacciRecursive(id));
        System.out.println("API - Currently Executing in: " + Thread.currentThread().getName());
        asyncFibService.saveToDB(fibMap,memoItemRepo);
        return ret;
    }


    // If n<0, return 0
    private MemoItem fibonacciRecursive(long n){
        MemoItem ret;
        if(fibMap.containsKey(n)){
            ret = new MemoItem(n,fibMap.get(n));
            return ret;
        }
        else if(n <= 0L){
            ret = new MemoItem(n,0L);
            if(!fibMap.containsKey(n)){
                fibMap.put(n,0L);
            }
            return ret;
        }
        else if(n == 1L){
            ret = new MemoItem(n,1L);
            if(!fibMap.containsKey(n)){
                fibMap.put(n,1L);
            }
            return ret;
        }
        else{
            long value = fibonacciRecursive(n-1).getValue()+fibonacciRecursive(n-2).getValue();
            ret = new MemoItem(n,value);
            if(!fibMap.containsKey(n)){
                fibMap.put(n,value);
            }
            return ret;
        }
    }
}

