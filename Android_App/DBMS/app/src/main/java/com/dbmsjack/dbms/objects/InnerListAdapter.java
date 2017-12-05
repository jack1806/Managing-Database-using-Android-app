package com.dbmsjack.dbms.objects;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.dbmsjack.dbms.R;

import java.util.ArrayList;

/**
 * Created by root on 2/11/17.
 */

public class InnerListAdapter extends RecyclerView.Adapter<InnerListAdapter.CustomView> {

    public ArrayList<InnerListObject> innerListObjects;

    public InnerListAdapter(ArrayList<InnerListObject> innerListObjects){

        this.innerListObjects = innerListObjects;
    }

    @Override
    public void onBindViewHolder(CustomView holder, int position) {
        InnerListObject innerListObject = innerListObjects.get(position);
        holder.bind(innerListObject.getKey(),innerListObject.getValue());
    }

    @Override
    public CustomView onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        int layout_id = R.layout.inner_list_item;
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(layout_id,parent,false);
        return new CustomView(view);
    }

    @Override
    public int getItemCount() {
        return innerListObjects.size();
    }

    public class CustomView extends RecyclerView.ViewHolder{

        TextView key;
        TextView value;

        public CustomView(View view){

            super(view);
            key = (TextView) view.findViewById(R.id.inner_item_key);
            value = (TextView) view.findViewById(R.id.inner_item_value);

        }

        public void bind(String key,String value){

            this.key.setText(key.toUpperCase());
            this.value.setText(value);

        }

    }

}
