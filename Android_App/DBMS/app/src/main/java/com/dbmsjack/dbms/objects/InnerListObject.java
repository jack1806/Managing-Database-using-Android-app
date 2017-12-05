package com.dbmsjack.dbms.objects;

/**
 * Created by root on 2/11/17.
 */

public class InnerListObject {

    String key;
    String value;

    public InnerListObject(String key,String value){
        this.key = key;
        this.value = value;
    }

    public String getKey() {
        return key;
    }

    public String getValue() {
        return value;
    }
}
