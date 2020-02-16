import org.h2.jdbcx.JdbcDataSource;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import javax.sql.DataSource;
import javax.xml.crypto.Data;

import static org.junit.Assert.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.sql.SQLException;

public class RequestTest {

    @Before
    public void start() throws SQLException {
        TaskDaoClass taskDaoClass = new TaskDaoClass(testDataSource());
        TaskDao taskDao = new TaskDaoClass(testDataSource());
        taskDaoClass.dbConnect();
        taskDaoClass.createTable();
        taskDao.setTask(testData());
        HttpServer server = new HttpServer(8380, testDataSource());
        server.startServer();
    }

    @Test
    public void checkGetAllRequest() throws IOException {
        HttpRequest request = new HttpRequest("/tasks", "localhost", "GET", "");
        Socket socket = new Socket("localhost", 8380);
        request.sendRequest(socket);
        BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        assertTrue(!(input.readLine().isEmpty()));
    }
    @Test
    public void getOneRequestTest() throws IOException {
        HttpRequest request = new HttpRequest("/tasks/1", "localhost", "GET", "");
        Socket socket = new Socket("localhost", 8380);
        request.sendRequest(socket);
        BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        assertTrue(input.readLine().equals("HTTP/1/1 200 OK"));
    }
    @Test
    public void postNewRequestTest() throws IOException {
        Task task = new Task("Bug fixing", "Doing", "Atle, Terje, Harald, Steinar");
        HttpRequest req = new HttpRequest("/tasks", "localhost", "POST", "name=" + task.getName() + "&" + "status=" + task.getStatus() + "&" + "persons=" + task.getpersons());
        Socket socket = new Socket("localhost", 8380);
        req.sendRequest(socket);
        BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        assertTrue(input.readLine().equals("HTTP/1/1 200 OK"));
    }
    @Test
    public void postUpdateRequestTest() throws IOException {
        TaskDao taskDao = new TaskDaoClass(testDataSource());
        Task task = taskDao.getTask(1);
        task.setName("Create Structure");
        HttpRequest req = new HttpRequest("/tasks/" + task.getId(), "localhost", "POST", "name=" + task.getName() + "&" + "status=" + task.getStatus() + "&" + "persons=" + task.getpersons());
        Socket socket = new Socket("localhost", 8380);
        req.sendRequest(socket);
        BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        assertTrue(input.readLine().equals("HTTP/1/1 200 OK"));
    }

    private DataSource testDataSource() {
        JdbcDataSource ds = new JdbcDataSource();
        ds.setURL("jdbc:h2:mem:test");
        ds.setUser("tu");

        return ds;
    }

    private Task testData() {
        Task testTask = new Task("Clean bathroom", "Starting", "Kåre, Britt, Helge");
        return testTask;
    }
}
