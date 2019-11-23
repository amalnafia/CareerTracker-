package com.example.ibrahimshaltout.test.ToDoList;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ListView;

import com.example.ibrahimshaltout.test.R;
import com.example.ibrahimshaltout.test.dataclass.IndividualDataClass;
import com.example.ibrahimshaltout.test.dataclass.TrackDataClass;
import com.example.ibrahimshaltout.test.newsfeed.post.PostAdapter;
import com.example.ibrahimshaltout.test.newsfeed.post.PostDataClass;
import com.example.ibrahimshaltout.test.notification.Notification;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ToDoListActivity extends AppCompatActivity {
    RecyclerView todo_recyclerView;
    ToDoAdapter toDoAdapter;
    ArrayList<IndividualDataClass> individualTodo = new ArrayList<>();

    DatabaseReference db;
    FirebaseAuth auth;
    String currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_to_do_list);

        FloatingActionButton floatingActionButton = findViewById(R.id.add_task);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ToDoListActivity.this, AddTaskActivity.class);
                startActivity(intent);
            }
        });
        db = FirebaseDatabase.getInstance().getReference();
        currentUser = auth.getCurrentUser().getUid();
        initViewsPosts();
        getPostsData();
    }

    private void initViewsPosts() {
        todo_recyclerView = findViewById(R.id.todo_recyclerView);
        toDoAdapter = new ToDoAdapter(this, individualTodo);
        RecyclerView.LayoutManager trackLayoutManager = new LinearLayoutManager(this);
        todo_recyclerView.setLayoutManager(trackLayoutManager);
        todo_recyclerView.setNestedScrollingEnabled(true);
        todo_recyclerView.setHasFixedSize(true);
        todo_recyclerView.requestFocus();
        todo_recyclerView.setAdapter(toDoAdapter);
    }

    private void getPostsData() {

        db.child("Users").child(currentUser).child("userTasks").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                parseTasksData(dataSnapshot);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void parseTasksData(DataSnapshot dataSnapshot) {
        ArrayList<IndividualDataClass> listOfTasks = new ArrayList<>();
        Iterable<DataSnapshot> list = dataSnapshot.getChildren();
        for (DataSnapshot x : list) {
            listOfTasks.add(x.getValue(IndividualDataClass.class));
        }
        setTasksData(listOfTasks);
    }

    private void setTasksData(ArrayList<IndividualDataClass> listOfTasks) {
        individualTodo.addAll(listOfTasks);
        toDoAdapter.notifyDataSetChanged();
    }
}
