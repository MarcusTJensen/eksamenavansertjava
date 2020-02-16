import javax.sql.DataSource;
import javax.xml.crypto.Data;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TaskDaoClass implements TaskDao {
    DataSource dataSource;

    public TaskDaoClass(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public Connection dbConnect() throws SQLException {
        /*String url = "jdbc:postgresql://localhost/java_exam_db";
        String user = "java_exam_user";
        String password = "password";*/
        Connection conn = dataSource.getConnection();
        return  conn;
    }

    public void createTable() {
        String sql = "CREATE TABLE IF NOT EXISTS TASKS (\n"
                    + "     id SERIAL PRIMARY KEY, \n"
                    + "     name text, \n"
                    + "     status text, \n"
                    + "     persons text\n"
                    + ");";
        try(Connection conn = dbConnect()) {
            Statement stmnt = conn.createStatement();
            stmnt.execute(sql);
        }catch (SQLException e) {
            System.out.println(e.getMessage());
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
                int id = rs.getInt("id");
                String name = rs.getString("name");
                String status = rs.getString("status");
                String persons = rs.getString("persons");
                Task task = new Task(id, name, status, persons);
                tasks.add(task);
            }
        }catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return tasks;
    }

    @Override
    public Task getTask(int id) {
        String sql = "SELECT * FROM tasks \n"
                    + "     WHERE id='" + id +"'";
        Task task = null;
        try(Connection conn = dbConnect()) {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                int taskId = rs.getInt("id");
                String tName = rs.getString("name");
                String status = rs.getString("status");
                String persons = rs.getString("persons");
                task = new Task(taskId, tName, status, persons);
            }
        }catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return task;
    }

    @Override
    public void updateTask(Task task) {
        String sql = "UPDATE tasks \n"
                    + "     SET name = '" +  task.getName() + "'" + ", "
                    + "         status = '" + task.getStatus() + "'" + ", "
                    + "         persons = '" + task.getpersons() + "'" + " \n"
                    + "     WHERE id = '" + task.getId() + "'";
        try (Connection conn = dbConnect()) {
            Statement stmt = conn.createStatement();
            stmt.execute(sql);
            System.out.println("Successful update");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
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
        }catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}
