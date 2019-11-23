package com.example.ibrahimshaltout.test.ToDoList;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.ibrahimshaltout.test.R;
import com.example.ibrahimshaltout.test.dataclass.IndividualDataClass;

import java.util.ArrayList;

public class ToDoAdapter extends RecyclerView.Adapter<ToDoAdapter.ToDoViewHolder> {
    private Context mContext;
    private ArrayList<IndividualDataClass> taskList;

    public ToDoAdapter(Context mContext, ArrayList<IndividualDataClass> taskList) {
        this.mContext = mContext;
        this.taskList = taskList;
    }

    @NonNull
    @Override
    public ToDoViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(mContext)
                .inflate(R.layout.task_item, viewGroup, false);

        return new ToDoAdapter.ToDoViewHolder(itemView);

    }

    @Override
    public void onBindViewHolder(@NonNull ToDoViewHolder toDoViewHolder, int i) {
        IndividualDataClass item = taskList.get(i);
        toDoViewHolder.task_item_name.setText(item.getTaskName());
        toDoViewHolder.task_itm_desc.setText(item.getTaskDesc());

    }

    @Override
    public int getItemCount() {
        return taskList == null ? 0 : taskList.size();
    }

    public class ToDoViewHolder extends RecyclerView.ViewHolder {
        TextView task_item_name, task_itm_desc;

        public ToDoViewHolder(@NonNull View itemView) {
            super(itemView);

            task_item_name = itemView.findViewById(R.id.task_item_name);
            task_itm_desc = itemView.findViewById(R.id.task_itm_desc);

        }
    }
}
