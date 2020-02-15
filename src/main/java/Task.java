import java.sql.Array;
import java.util.ArrayList;

public class Task {
    private String name;
    private String status;
    private String persons;
    private int id;

    public Task(int id, String name, String status, String persons) {
        this.id = id;
        this.name = name;
        this.status = status;
        this.persons = persons;
    }

    public Task(String name, String status, String persons) {
        this.name = name;
        this.status = status;
        this.persons = persons;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStatus() {
        return status;
    }

    public  void setStatus(String status) {
        this.status = status;
    }

    public String getpersons() {
        return persons;
    }

    public void setPersons(String persons) {
        this.persons = persons;
    }

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
}
