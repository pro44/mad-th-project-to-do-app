package com.example.todo.db;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;



import java.util.List;

@Dao
public interface TasksDao {
    @Query("SELECT * FROM Tasks")
    List<Tasks> getAllTasks();

    @Insert
    void insertTask(Tasks tasks);

    @Query("DELETE FROM Tasks")
    void truncateTheTable();

    @Query("DELETE  FROM Tasks WHERE taskId=:taskId")
    void deleteFromTaskId(int taskId);

    @Query("UPDATE Tasks SET title=:title, aboutActivity=:aboutActivity, dayiw=:dayiw, dayic=:dayic, month=:month, year=:year ,time=:time, status=:status WHERE taskId=:taskId")
    void updateExistingRow(int taskId,String title, String aboutActivity, String dayiw,String dayic,String month,String year,String time,String status);

}
