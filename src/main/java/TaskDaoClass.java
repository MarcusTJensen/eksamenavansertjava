import javax.sql.DataSource;
import javax.xml.crypto.Data;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TaskDaoClass implements TaskDao {
    PostgrresqlDataSource postgresDs = new PostgrresqlDataSource();

    public Connection dbConnect() throws SQLException {
        String url = "jdbc:postgresql://localhost/java_exam_db";
        String user = "java_exam_user";
        String password = "password";
        Connection conn = DriverManager.getConnection(url, user, password);
        createTable();
        return  conn;
    }

    public void createTable() {
        String sql = "CREATE TABLE IF NOT EXISTS tasks (\n"
                    + "     name text PRIMARY KEY, \n"
                    + "     status text, \n"
                    + "     persons text\n"
                    + ");";
        try(Connection conn = dbConnect()) {
            Statement stmnt = conn.createStatement();
            stmnt.execute(sql);
        }catch (SQLException e) {
            System.out.println("added table");
        }
    }

    @Override
    public ArrayList<Task> getAllTasks() {
        String sql = "SELECT * FROM tasks";
        ArrayList<Task> tasks = new ArrayList<>();
        try(Connection conn = dbConnect()) {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                String name = rs.getString("name");
                String status = rs.getString("status");
                String persons = rs.getString("persons");
                Task task = new Task(name, status, persons);
                tasks.add(task);
            }
        }catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return tasks;
    }

    @Override
    public Task getTask(String name) {
        String sql = "SELECT * FROM tasks \n"
                    + "     WHERE name='" + name +"'";
        Task task = null;
        try(Connection conn = dbConnect()) {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                String tName = rs.getString("name");
                String status = rs.getString("status");
                String persons = rs.getString("persons");
                task = new Task(tName, status, persons);
            }
        }catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return task;
    }

    @Override
    public void updateTask(Task task) {
        Task taskToUpdate = getTask(task.getName());

    }

    @Override
    public void setTask(Task task) {
        String sql = "INSERT INTO tasks (name, status, persons) \n"
                    + "     VALUES (?, ?, ?)";
        try(Connection conn = dbConnect();
            PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, task.getName());
            stmt.setString(2, task.getStatus());
            stmt.setString(3, task.getpersons());
            stmt.execute();
            System.out.println("Successfull Insertion!");
        }catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}
