package com.main.hooker.hooker.utils.state;

import com.yuyh.easydao.annotation.Column;
import com.yuyh.easydao.base.BaseEntity;

public class MetaItem extends BaseEntity{

    @Column
    private String key;

    @Column
    private String value;

    public MetaItem(){
        super();
    }

    public MetaItem(String key, String value){
        this.key = key;
        this.value = value;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
