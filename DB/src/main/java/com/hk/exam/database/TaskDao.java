package com.hk.exam.database;

import java.util.ArrayList;

public interface TaskDao {
    public ArrayList<Task> getAllTasks();
    public Task getTask(int id);
    public void updateTask(Task task);
    public void setTask(Task task);
}
