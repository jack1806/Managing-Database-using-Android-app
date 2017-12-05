package com.dbmsjack.dbms.objects;

import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by root on 2/11/17.
 */

public class QueryObject {

    ArrayList<String> fields;
    ArrayList<JSONObject> rows;
    int total;

    public QueryObject(ArrayList<String> fields,ArrayList<JSONObject> rows,
                       int total){
        this.total = total;
        this.fields = fields;
        this.rows = rows;

    }

    public ArrayList<JSONObject> getRows() {
        return rows;
    }

    public ArrayList<String> getFields() {
        return fields;
    }

    public int getTotal() {
        return total;
    }

}
