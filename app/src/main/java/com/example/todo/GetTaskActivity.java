package com.example.todo;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.todo.db.Tasks;
import com.example.todo.db.TodoDatabase;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.TimeZone;

public class GetTaskActivity extends AppCompatActivity {
    EditText getTitle,getDiscription,getDate,getTime,taskEvent;

    String title,discription,mDayiw,mDayic,mYear,mMonth,time;
    Button button;
    DatePickerDialog.OnDateSetListener setListener;
    int position;
    int mHour,mMinute;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_task);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
        //it will check if the user is adding new task or editing existing task
        Intent i = getIntent();
        boolean isEdit = i.getBooleanExtra("isEdit",false);

        getTitle=findViewById(R.id.gettitle);
        getDiscription=findViewById(R.id.getdiscription);
        getDate=findViewById(R.id.getdate);

        getTime=findViewById(R.id.gettime);

        button = findViewById(R.id.addTask);
        if(isEdit){
            Intent i2=getIntent();
            position = i2.getIntExtra("id",-1);
            getTitle.setText(i2.getStringExtra("title"));
            getDiscription.setText(i2.getStringExtra("about"));
            mDayiw=i2.getStringExtra("dayiw");
            mDayic=i2.getStringExtra("dayic");
            mMonth=i2.getStringExtra("month");
            mYear=i2.getStringExtra("year");
            time=i2.getStringExtra("time");
            getDate.setText(i2.getStringExtra("dayiw")+" | "+i2.getStringExtra("dayic")+"-"+
                    i2.getStringExtra("month")+"-"+i2.getStringExtra("year"));
            getTime.setText(i2.getStringExtra("time"));
            button.setText("Update");
        }
        //initializing Calendar
        Calendar calendar = Calendar.getInstance();
        final int year = calendar.get(Calendar.YEAR);
        final int month = calendar.get(Calendar.MONTH);
        final int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);



        getDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        GetTaskActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int dayOfMonth) {
                        month=month+1;

                        mDayiw=dayOfWeek(dayOfMonth,month,year);
                        mDayic=""+dayOfMonth;
                        mYear=""+year;
                        mMonth=""+month;
                        String datee = mDayiw+"|"+mDayic+"-"+mMonth+"-"+mYear;
                        getDate.setText(datee);
                    }
                },year,month,dayOfMonth);
                datePickerDialog.show();
            }
        });//end of calendar ontough listener

        getTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar cTime = Calendar.getInstance();
                int hour = cTime.get(Calendar.HOUR_OF_DAY);
                int mins = cTime.get(Calendar.MINUTE);
                TimePickerDialog timePickerDialog = new TimePickerDialog(GetTaskActivity.this,
                        new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker timePicker, int hourOfDay, int minute) {
                                Calendar c = Calendar.getInstance();
                                mHour = hourOfDay;
                                mMinute = minute;
                                c.set(Calendar.HOUR_OF_DAY,hourOfDay);
                                c.set(Calendar.MINUTE,minute);
                                c.setTimeZone(TimeZone.getDefault());
                                SimpleDateFormat format = new SimpleDateFormat("k:mm a");
                                time = format.format(c.getTime());
                                getTime.setText(time);
                            }
                        },hour,mins,false);
                timePickerDialog.show();
            }
        });//end of time get method
        //button click to add tasks
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(anyFieldIsEmpty())checkForEmptyField();
                else{
                    if(!isEdit){//check if user is updating existing task or adding new task
                        //this will add new task
                        //initializeCalendar();
                        Tasks task = new Tasks(getTitle.getText().toString(),getDiscription.getText().toString(),
                                mDayiw,mDayic,mMonth,mYear,time,"soon");
                        TodoDatabase.getDbInstance(getApplicationContext()).tasksDao().insertTask(task);
                        startActivity(new Intent(getApplicationContext(),MainActivity.class));
                        finish();

                    }
                    else if(isEdit){


                        //initializeCalendar();
                        Tasks task = new Tasks(getTitle.getText().toString(),getDiscription.getText().toString(),
                                mDayiw,mDayic,mMonth,mYear,time,"soon");
                        TodoDatabase.getDbInstance(getApplicationContext()).tasksDao().updateExistingRow(
                                position,getTitle.getText().toString(),getDiscription.getText().toString(),
                                mDayiw,mDayic,mMonth,mYear,time,"soon"
                        );
                        startActivity(new Intent(getApplicationContext(),MainActivity.class));
                        finish();
                    }
                }//end of else
            }
        });//end of button click listener

    }
    //this method will check for any field is empty
    public void checkForEmptyField(){
        if(TextUtils.isEmpty(getTitle.getText().toString())){
            getTitle.setError("please insert the title");
        }
        if(TextUtils.isEmpty(getDiscription.getText().toString())){
            getDiscription.setError("please insert the discription");
        }
        if(TextUtils.isEmpty(getDate.getText().toString())){
            getDate.setError("please insert the date");
        }
        if(TextUtils.isEmpty(getTime.getText().toString())){
            getTime.setError("please insert the time");
        }

    }//end of empty field checking

    public boolean anyFieldIsEmpty(){
        if(TextUtils.isEmpty(getTitle.getText().toString())||
                TextUtils.isEmpty(getDiscription.getText().toString())||
                TextUtils.isEmpty(getDate.getText().toString())||
                TextUtils.isEmpty(getTime.getText().toString())) return true;
        return false;
    }
    public static String dayOfWeek(int d,int m,int y){
        return CheckDay.dayOfWeek(d,m,y);

    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(getApplicationContext(),MainActivity.class));
        finish();
        super.onBackPressed();
    }
    public void initializeCalendar(){
        Calendar calendar= Calendar.getInstance();
        if(Build.VERSION.SDK_INT>=23){
            calendar.set(
                    Integer.parseInt(mYear),
                    Integer.parseInt(mMonth),
                    Integer.parseInt(mDayic),
                    mHour,
                    mMinute,
                    0
            );
        }
        else{
            calendar.set(
                    Integer.parseInt(mYear),
                    Integer.parseInt(mMonth),
                    Integer.parseInt(mDayic),
                    mHour,
                    mMinute,
                    0
            );
        }//end of else
        setAlarm(calendar.getTimeInMillis());
    }//end of initilize calendar
    public void setAlarm(long timeInMilis){
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);

        Intent intent = new Intent(this,MyAlrmBroadcast.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this,0,intent,0);
        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP,timeInMilis,
                AlarmManager.INTERVAL_FIFTEEN_MINUTES,pendingIntent);

        showToast("alarm is set");
    }
    public  void showToast(String s){
        Toast.makeText(this, s, Toast.LENGTH_SHORT).show();
    }
}