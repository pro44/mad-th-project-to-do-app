package com.example.todo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import com.example.todo.db.Tasks;
import com.example.todo.db.TodoDatabase;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    List<Tasks> list;
    RecyclerView recyclerView;
    TextView addTask;
    TaskAdapter adapter;
    TextView status;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
        //initializing the components
        recyclerView=findViewById(R.id.recyclerView);
        addTask=findViewById(R.id.addTask);
        status=findViewById(R.id.isEmpty);
        list = new ArrayList<>();
        list = TodoDatabase.getDbInstance(getApplicationContext()).tasksDao().getAllTasks();
        if(list.size()<=0)TodoDatabase.getDbInstance(getApplicationContext()).tasksDao().truncateTheTable();

        statusUpdate();
        addTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(getApplicationContext(),GetTaskActivity.class);
                intent.putExtra("isEdit",false);
                startActivity(intent);
                finish();
                overridePendingTransition(android.R.anim.slide_in_left,android.R.anim.slide_out_right);

            }
        });

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new TaskAdapter(list,this);
        recyclerView.setAdapter(adapter);


    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()){
            case 121:
                Tasks foreDelete = list.get(item.getGroupId());
                int databasePosition = foreDelete.getTaskId();
                adapter.removeItem(databasePosition,item.getGroupId(), list);
                statusUpdate();
                return true;



            case 122:
                Intent intent = new Intent(getApplicationContext(),GetTaskActivity.class);
                intent.putExtra("isEdit",true);
                int position = item.getGroupId();
                Tasks t = list.get(position);
                intent.putExtra("id",t.getTaskId());
                intent.putExtra("title",t.getTitle());
                intent.putExtra("about",t.getAboutActivity());
                intent.putExtra("dayiw",t.getDayiw());
                intent.putExtra("dayic",t.getDayic());
                intent.putExtra("month",t.getMonth());
                intent.putExtra("year",t.getYear());
                intent.putExtra("time",t.getTime());
                intent.putExtra("status",t.getStatus());
                startActivity(intent);
                finish();
                overridePendingTransition(android.R.anim.slide_in_left,android.R.anim.slide_out_right);

                //showToast("updated");
                return true;
            case 123:
                Tasks foreComplete = list.get(item.getGroupId());
                int dbPosition = foreComplete.getTaskId();
                int gId= item.getGroupId();
                showTaskCompletionDialogue(dbPosition,gId,list);
                return true;
        }
        return super.onContextItemSelected(item);
    }

    private void showTaskCompletionDialogue(int dattabasePosition,int position,List<Tasks> l) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Are you sure? Have you completed the task?");

        builder.setNegativeButton("yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                adapter.removeItem(dattabasePosition,position, list);
                statusUpdate();

            }
        });
        builder.setPositiveButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                showToast("Ok then complete task and come back");
            }
        });
        builder.create();
        builder.show();
    }

    public void showToast(String s){
        Toast.makeText(getApplicationContext(), s, Toast.LENGTH_SHORT).show();
    }
    public void statusUpdate(){
        if(list.size()==0){status.setText("No pending task");
        status.setVisibility(View.VISIBLE);
        }
    }
}