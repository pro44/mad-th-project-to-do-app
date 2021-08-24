package com.example.todo.db;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Tasks {

    @PrimaryKey(autoGenerate = true)
    public int taskId;

    @ColumnInfo(name = "title")
    String title;
    @ColumnInfo(name = "aboutActivity")
    String aboutActivity;
    @ColumnInfo(name = "dayiw")
    String dayiw;
    @ColumnInfo(name = "dayic")
    String dayic;
    @ColumnInfo(name = "month")
    String month;
    @ColumnInfo(name = "year")
    String year;
    @ColumnInfo(name = "time")
    String time;
    @ColumnInfo(name = "status")
    String status;

    public Tasks(String title, String aboutActivity, String dayiw, String dayic, String month, String year, String time, String status) {
        this.title = title;
        this.aboutActivity = aboutActivity;
        this.dayiw = dayiw;
        this.dayic = dayic;
        this.month = month;
        this.year = year;
        this.time = time;
        this.status = status;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public int getTaskId() {
        return taskId;
    }

    public void setTaskId(int taskId) {
        this.taskId = taskId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAboutActivity() {
        return aboutActivity;
    }

    public void setAboutActivity(String aboutActivity) {
        this.aboutActivity = aboutActivity;
    }

    public String getDayiw() {
        return dayiw;
    }

    public void setDayiw(String dayiw) {
        this.dayiw = dayiw;
    }

    public String getDayic() {
        return dayic;
    }

    public void setDayic(String dayic) {
        this.dayic = dayic;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
