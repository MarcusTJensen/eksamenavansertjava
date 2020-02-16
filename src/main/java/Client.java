import java.awt.*;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

public class Client {

    public static void main(String[] args) throws IOException {
        new Client().runClient();
    }

    public void runClient() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Welcome!");
        System.out.println("Here's a list of all the available commands :)");
        listCommands();
        String cmd = scanner.nextLine();
        String taskId;
        String taskName;
        String taskStatus;
        String taskPersons;

        while (!(cmd.equals("exit"))) {
            if (cmd.equals("get all")) {
                System.out.println("Retrieving all tasks...");
                HttpRequest req = new HttpRequest("/tasks", "localhost", "GET", "");
                String response = sendRequest(req);
                System.out.println(response);
                System.out.println("What is your next move?");
                cmd = scanner.nextLine();
            }
            if (cmd.equals("get one")) {
                System.out.println("Write the ID of the Task you wish to retrieve");
                String id = scanner.nextLine();
                HttpRequest req = new HttpRequest("/tasks/" + id, "localhost", "GET", "");
                String response = sendRequest(req);
                System.out.println(response);
                System.out.println("What is your next move?");
                cmd = scanner.nextLine();
            }
            if (cmd.equals("add")) {
                System.out.println("Write the name of the task:");
                String name = scanner.nextLine();
                System.out.println("Write the status of this task:");
                String status = scanner.nextLine();
                System.out.println("Write down who this task is assigned to. Split the names with a comma");
                String persons = scanner.nextLine();
                HttpRequest req = new HttpRequest("/tasks", "localhost", "POST", "name=" + name + "&status=" + status + "&persons=" + persons);
                String response = sendRequest(req);
                System.out.println(response);
                System.out.println("What is your next move?");
                cmd = scanner.nextLine();

            }
            if (cmd.equals("update")) {
                System.out.println("Write the id of the task you wish to update:");
                String id = scanner.nextLine();
                HttpRequest request = new HttpRequest("/tasks/" + id, "localhost", "GET", "");
                String getResponse = sendRequest(request);
                taskId = getResponse.split(" Name: ")[0];
                System.out.println(taskId);
                int nameStartIndex = getResponse.indexOf("Name: ") + 6;
                int nameEndIndex = getResponse.indexOf(" Status");
                int statusStartIndex = getResponse.indexOf("Status: ") + 8;
                int statusEndIndex = getResponse.indexOf(" Persons");
                int personsStartIndex = getResponse.indexOf("Persons: ") + 9;
                int personsEndIndex = getResponse.length();
                taskName = getResponse.substring(nameStartIndex, nameEndIndex).replace(",", "");
                taskStatus = getResponse.substring(statusStartIndex, statusEndIndex).replace(",", "");
                taskPersons = getResponse.substring(personsStartIndex, personsEndIndex).replace(",", "");
                System.out.println("Write a new name of the task:");
                String name = scanner.nextLine();
                if (name.equals("")) {
                    name = taskName;
                }
                System.out.println("Write the status of this task:");
                String status = scanner.nextLine();
                if (status.equals("")) {
                    status = taskStatus;
                }
                System.out.println("Write down who this task is assigned to. Split the names with a comma");
                String persons = scanner.nextLine();
                if (persons.equals("")) {
                    persons = taskPersons;
                }
                HttpRequest req = new HttpRequest("/tasks/" + id, "localhost", "POST", "name=" + name + "&status=" + status + "&persons=" + persons);
                String response = sendRequest(req);
                System.out.println(response);
                System.out.println("What is your next move?");
                cmd = scanner.nextLine();
            }
            if (cmd.equals("help")) {
                listCommands();
                System.out.println("What is your move?");
                cmd = scanner.nextLine();
            }
        }
        if (cmd.equals("exit")) {
            System.out.println("Bye :(");
        }

    }

    public String sendRequest(HttpRequest req) {
        String res = "";
        try (Socket s = req.startClient(8380)) {
            req.sendRequest(s);
            res = req.getResponse(s);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        return res;
    }

    public void listCommands() {
        System.out.println("- get all : returns all tasks");
        System.out.println("- add : add a new task to the database");
        System.out.println("- get one : returns the task with the ID you entered");
        System.out.println("- update : updates a specific task in the database");
        System.out.println("- help : returns an overview of all the available commands");
        System.out.println("- exit : the program exits :(");
    }

}
