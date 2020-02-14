import java.util.ArrayList;
import java.util.List;

public interface TaskDao {
    public ArrayList<Task> getAllTasks();
    public Task getTask(String name);
    public void updateTask(Task task);
    public void setTask(Task task);
}
