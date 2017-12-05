package com.dbmsjack.dbms.objects;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.dbmsjack.dbms.MainActivity;
import com.dbmsjack.dbms.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by root on 2/11/17.
 */

public class QueryAdapter extends RecyclerView.Adapter<QueryAdapter.CustomView>{

    public Context context;
    public QueryObject queryObject;

    public QueryAdapter(Context context,QueryObject queryObject){

        this.context = context;
        this.queryObject = queryObject;

    }

    @Override
    public CustomView onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        int layout_id = R.layout.query_list_item;
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(layout_id,parent,false);
        return new CustomView(view);
    }

    @Override
    public void onBindViewHolder(CustomView holder, int position) {
        holder.bind(queryObject,position,context);
    }

    @Override
    public int getItemCount() {
        return queryObject.getTotal();
    }

    public class CustomView extends RecyclerView.ViewHolder {

        RecyclerView listView;

        public CustomView(View view){
            super(view);
            listView = (RecyclerView) view.findViewById(R.id.key_value_list);
        }

        public void bind(QueryObject object,int pos,Context context){

            try {
                ArrayList<String> fields = object.getFields();
                JSONObject jsonObject = object.getRows().get(pos);
                ArrayList<InnerListObject> innerListObjects = new ArrayList<>();

                for (int i = 0; i < fields.size(); i++)
                    innerListObjects.add(new InnerListObject(fields.get(i), jsonObject.getString(fields.get(i))));

                InnerListAdapter listAdapter = new InnerListAdapter(innerListObjects);
                LinearLayoutManager layoutManager = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);
                listView.setAdapter(listAdapter);
                listView.setLayoutManager(layoutManager);
            }
            catch(JSONException e){
                e.printStackTrace();
            }
        }

    }

}
