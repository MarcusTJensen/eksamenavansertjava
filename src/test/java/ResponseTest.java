import org.h2.jdbcx.JdbcDataSource;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

import javax.sql.DataSource;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.sql.SQLException;

public class ResponseTest {

    @Before
    public void start() throws SQLException {
        TaskDaoClass taskDaoClass = new TaskDaoClass(testDataSource());
        TaskDao taskDao = new TaskDaoClass(testDataSource());
        taskDaoClass.dbConnect();
        taskDaoClass.createTable();
        taskDao.setTask(testData());
    }

    @Test
    public void testGetResponse() throws IOException {
        HttpServer server = new HttpServer(8380, testDataSource());
        server.startServer();
        Socket socket = new Socket("localhost", 8380);
        PrintWriter pw = new PrintWriter(socket.getOutputStream());
        pw.println("GET /tasks HTTP/1/1" + "\r\n");
        pw.println("Host: localhost\r\n");
        pw.println("Connection: Keep-Alive\r\n");
        pw.println("Content-type: application/x-www-form-urlencoded\r\n");
        pw.flush();
        BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));

        assertEquals(br.readLine(), "HTTP/1/1 200 OK");
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
