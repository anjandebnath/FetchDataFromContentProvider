package com.anjan.user06.fetchdatafromcontentprovider.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.anjan.user06.fetchdatafromcontentprovider.R;
import com.anjan.user06.fetchdatafromcontentprovider.model.Students;

import java.util.List;


/**
 * Created by Anjan Debnath on 5/25/2018.
 * Copyright (c) 2018, W3 Engineers Ltd. All rights reserved.
 */
public class StudentsAdapter extends RecyclerView.Adapter<StudentsAdapter.MyViewHolder> {

    private List<Students> studentsList;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView name, dept, regId;

        public MyViewHolder(View view) {
            super(view);
            name = (TextView) view.findViewById(R.id.textName);
            regId = (TextView) view.findViewById(R.id.textReg);
            dept = (TextView) view.findViewById(R.id.textDept);
        }
    }


    public StudentsAdapter(List<Students> students) {
        this.studentsList = students;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.students_list_row, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        Students students = studentsList.get(position);

        holder.name.setText(students.getName());
        holder.regId.setText(students.getRegId());
        holder.dept.setText(students.getDept());
    }

    @Override
    public int getItemCount() {
        return studentsList.size();
    }
}
