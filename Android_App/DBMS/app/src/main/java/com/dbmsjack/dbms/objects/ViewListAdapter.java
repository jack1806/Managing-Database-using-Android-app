package com.dbmsjack.dbms.objects;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.dbmsjack.dbms.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by root on 2/11/17.
 */

public class ViewListAdapter extends RecyclerView.Adapter<ViewListAdapter.CustomView> {

    public ArrayList<ViewListItem> items;

    public ViewListAdapter(ArrayList<ViewListItem> items){
        this.items = items;
    }

    @Override
    public CustomView onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        int layout_id = R.layout.view_list_item;
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(layout_id,parent,false);
        return new CustomView(view);
    }

    @Override
    public void onBindViewHolder(CustomView holder, int position, List<Object> payloads) {
        super.onBindViewHolder(holder, position, payloads);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    @Override
    public void onBindViewHolder(CustomView holder, int position) {
        holder.bind(position);
    }

    public class CustomView extends RecyclerView.ViewHolder{

        TextView name;
        TextView reg;
        TextView school;
        TextView cgpa;

        public CustomView(View view){
            super(view);
            name = (TextView)view.findViewById(R.id.stud_name);
            reg = (TextView)view.findViewById(R.id.stud_reg);
            school = (TextView)view.findViewById(R.id.stud_school);
            cgpa = (TextView)view.findViewById(R.id.stud_cgpa);
        }

        public void bind(int pos){

            ViewListItem current = items.get(pos);
            name.setText(current.getName());
            reg.setText(current.getReg());
            school.setText(current.getSchool());
            cgpa.setText(current.getCgpa());

        }

    }

}
