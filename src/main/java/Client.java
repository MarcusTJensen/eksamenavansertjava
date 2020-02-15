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

        if (cmd.equals("get all")) {
            HttpRequest req = new HttpRequest("/tasks", "localhost", "GET", "");
            String respone = "";
            try (Socket s = req.startClient(8380)) {
                req.sendRequest(s);
                respone = req.getResponse(s);
                System.out.println(respone);
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
            System.out.println("What is your next move brother?");
            cmd = scanner.nextLine();
        }
        if (cmd.equals("get one")) {
            System.out.println("Write the ID of the Task you wish to retrieve");
            String id = scanner.nextLine();
            HttpRequest req = new HttpRequest("/tasks/" + id, "localhost", "GET", "");
            sendRequest(req);
        }
        if (cmd.equals("add")) {
            System.out.println("Write the name of the task:");
            String name = scanner.nextLine();
            System.out.println("Write the status of this task:");
            String status = scanner.nextLine();
            System.out.println("Write down who this task is assigned to. Split the names with a comma");
            String persons = scanner.nextLine();
            HttpRequest req = new HttpRequest("/tasks", "localhost", "POST","name=" + name + "&status=" + status + "&persons=" + persons);
            sendRequest(req);
        }
        if (cmd.equals("update")) {
            System.out.println("Write the id of the task you wish to update:");
            String id = scanner.nextLine();
            System.out.println("Write a new name of the task:");
            String name = scanner.nextLine();
            System.out.println("Write the status of this task:");
            String status = scanner.nextLine();
            System.out.println("Write down who this task is assigned to. Split the names with a comma");
            String persons = scanner.nextLine();
            HttpRequest req = new HttpRequest("/tasks/" + id, "localhost", "POST","name=" + name + "&status=" + status + "&persons=" + persons);
            sendRequest(req);
        }
        if (cmd.equals("help")) {
            listCommands();
        }

    }

    public void sendRequest(HttpRequest req) {
        try (Socket s = req.startClient(8380)) {
            req.sendRequest(s);
            String res = req.getResponse(s);
            System.out.println(res);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
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
