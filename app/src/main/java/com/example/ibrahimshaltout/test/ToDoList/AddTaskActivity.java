
package com.example.ibrahimshaltout.test.ToDoList;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.ibrahimshaltout.test.R;
import com.example.ibrahimshaltout.test.dataclass.IndividualDataClass;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AddTaskActivity extends AppCompatActivity {
    EditText task_name, task_desc;
    Button add_task_btn;


    private FirebaseAuth auth;
    DatabaseReference db;
    DatabaseReference databaseReference;

    IndividualDataClass individualDataClass = new IndividualDataClass();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_task);

        final String currentUser = auth.getCurrentUser().getUid();

        task_name = findViewById(R.id.task_name);
        task_desc = findViewById(R.id.task_desc);

        add_task_btn = findViewById(R.id.add_task_btn);
        add_task_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                databaseReference = FirebaseDatabase.getInstance().getReference("Users").child(currentUser).child("userTasks");

                final String taskName = task_name.getText().toString().trim();
                individualDataClass.setTaskName(taskName);
                databaseReference.child("taskName").setValue(individualDataClass.getTaskName());

                final String taskDesc = task_name.getText().toString().trim();
                individualDataClass.setTaskDesc(taskDesc);
                databaseReference.child("taskDesc").setValue(individualDataClass.getTaskDesc());

                Intent intent = new Intent(AddTaskActivity.this, ToDoListActivity.class);
                startActivity(intent);
            }
        });
    }
}
