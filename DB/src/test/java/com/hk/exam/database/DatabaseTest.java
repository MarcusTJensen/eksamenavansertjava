package com.hk.exam.database;

import static org.junit.Assert.*;

import org.h2.jdbcx.JdbcDataSource;
import org.junit.Before;
import org.junit.Test;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.ArrayList;

public class DatabaseTest {

    @Before
    public void start() throws SQLException {
        TaskDao taskDao = new TaskDaoClass(testDataSource());
        TaskDaoClass taskDaoClass = new TaskDaoClass(testDataSource());
        taskDaoClass.dbConnect();
        taskDaoClass.createTable();
        ArrayList<Task> tasks = testTasks();
        for(int i = 0; i < tasks.size(); i++) {
            taskDao.setTask(tasks.get(i));
        }
    }

    @Test
    public void insertTest() {
        TaskDao taskDao = new TaskDaoClass(testDataSource());

        Task task = testTask();
        taskDao.setTask(task);
        ArrayList<Task> tasks = taskDao.getAllTasks();
        String name = "";
        for (int i = 0; i < tasks.size(); i ++) {
            if (tasks.get(i).getName().equals(task.getName())) {
                name = tasks.get(i).getName();
            }
        }
        assertTrue(name.equals(testTask().getName()));
    }

    @Test
    public void getAllTest() {
        TaskDao taskDao = new TaskDaoClass(testDataSource());
        ArrayList<Task> tasks = taskDao.getAllTasks();
        assertTrue(tasks.size() != 0);
    }

    @Test
    public void getTest(){
        TaskDao taskDao = new TaskDaoClass(testDataSource());
        Task task = taskDao.getTask(1);
        assertTrue(task.getId() == 1);
    }

    @Test
    public void updateTest(){
        TaskDao taskDao = new TaskDaoClass(testDataSource());
        Task taskToUpdate = taskDao.getTask(1);
        taskToUpdate.setName("Guitar Lesson");
        taskDao.updateTask(taskToUpdate);
        Task updatedTask = taskDao.getTask(1);
        assertTrue(updatedTask.getName().equals("Guitar Lesson"));
    }

    private Task testTask() {
        Task task = new Task("Environment setup", "Done", "Jens, Julie");
        return task;
    }

    private DataSource testDataSource() {
        JdbcDataSource ds = new JdbcDataSource();
        ds.setURL("jdbc:h2:mem:test");
        ds.setUser("tu");

        return ds;
    }

    private ArrayList<Task> testTasks() {
        Task task = new Task("Finish navigation", "Doing", "Mark, Andy, Hugh");
        Task task1 = new Task("Testing", "TBD", "Hans, Martin, Rune");
        Task task2 = new Task("Hashing", "Done", "Knut, Jorunn, Hanne");
        ArrayList taskList = new ArrayList<Task>();
        taskList.add(task);
        taskList.add(task1);
        taskList.add(task2);
        return taskList;
    }
}
