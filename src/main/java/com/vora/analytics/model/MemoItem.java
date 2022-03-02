package com.vora.analytics.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("memoitems")
@Data
public class MemoItem {

    @Id
    private long id;

    private long value;

    public MemoItem(long id, long value){
        super();
        this.id = id;
        this.value = value;
    }
}
