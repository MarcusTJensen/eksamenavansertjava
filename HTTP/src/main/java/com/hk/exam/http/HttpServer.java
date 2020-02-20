package com.hk.exam.http;

import com.hk.exam.database.PostgrresqlDataSource;
import com.hk.exam.database.Task;
import com.hk.exam.database.TaskDao;
import com.hk.exam.database.TaskDaoClass;

import javax.sql.DataSource;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.SQLException;
import java.util.ArrayList;

public class HttpServer {
    DataSource ds;
    int port;
    public HttpServer(int port, DataSource ds) {
        this.port = port;
        this.ds = ds;
    }
    public void startServer() {
        new Thread(() -> {
            try {
                ServerSocket server = new ServerSocket(port);

                while (true) {
                    Socket socket = server.accept();
                    BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                    String headers = br.readLine();
                    String path = headers.split(" ")[1];
                    String requestType = headers.split(" ")[0];
                    String response = readRequest(requestType, path, br);
                    socket.getOutputStream().write(response.getBytes());
                }
            }catch (IOException e) {
                System.out.println(e.getMessage());
            }
        }).start();
    }

    public String readRequest(String requestType, String path, BufferedReader br) throws IOException {
        String response = "";
        if (requestType.equals("GET")) {
            StringBuilder sb = new StringBuilder();
            TaskDao task = new TaskDaoClass(ds);
            if (path.equals("/tasks")) {
                ArrayList tasks;
                tasks = task.getAllTasks();
                for (int i = 0; i < tasks.size(); i++) {
                    Task currentTask = (Task) tasks.get(i);
                    sb.append(currentTask.getId() + " ");
                    sb.append("Name: " + currentTask.getName() + ", ");
                    sb.append("Status: " + currentTask.getStatus() + ", ");
                    sb.append("Persons: " + currentTask.getpersons());
                }
            } else if (path.contains("/tasks/")) {
                String[] pathParts = path.split("/");
                int id = Integer.parseInt(pathParts[2]);
                Task currentTask = task.getTask(id);
                sb.append(currentTask.getId() + " ");
                sb.append("Name: " + currentTask.getName() + ", ");
                sb.append("Status: " + currentTask.getStatus() + ", ");
                sb.append("Persons: " + currentTask.getpersons());
            }
            response = "HTTP/1/1 200 OK\r\n\r\n" + sb.toString();
        } else if (requestType.equals("POST")) {
            StringBuilder payload = new StringBuilder();
            TaskDao taskDao = new TaskDaoClass(ds);
            while (br.ready()) {
                payload.append((char) br.read());
            }
            String[] splitRequest = payload.toString().split("Body:");
            String body = splitRequest[1];
            System.out.println(payload);
            String[] splitBody = body.split("&");
            String name = splitBody[0].split("=")[1];
            String status = splitBody[1].split("=")[1];
            String persons = splitBody[2].split("=")[1];
            Task task = new Task(name, status, persons);
            if (path.equals("/tasks")) {

                System.out.println(payload);
                taskDao.setTask(task);
            } else if(path.contains("/tasks/")) {
                String[] pathParts = path.split("/");
                int id = Integer.parseInt(pathParts[2]);

                task.setId(id);
                taskDao.updateTask(task);
            }
            response = "HTTP/1/1 200 OK\r\n\r\n";
        }

        return response;
    }

    public static void main(String[] args) {
            DataSource ds = new PostgrresqlDataSource().getDataSource();
            TaskDaoClass taskDaoClass = new TaskDaoClass(ds);
            try {
                taskDaoClass.dbConnect();
                taskDaoClass.createTable();
            } catch (SQLException e) {
                System.out.println(e);
            }

            HttpServer server = new HttpServer(8380, ds);
            server.startServer();
            System.out.println("Server listening");
    }
}
