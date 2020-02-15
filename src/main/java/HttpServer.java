import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class HttpServer {
    int port;
    public HttpServer(int port) {
        this.port = port;
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
                    //System.out.println(path);
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
            TaskDao task = new TaskDaoClass();
            if (path.equals("/tasks")) {
                ArrayList tasks;
                tasks = task.getAllTasks();
                for (int i = 0; i < tasks.size(); i++) {
                    Task currentTask = (Task) tasks.get(i);
                    sb.append(currentTask.getId() + " ");
                    sb.append(currentTask.getName() + " ");
                    sb.append(currentTask.getStatus() + " ");
                    sb.append(currentTask.getpersons() + "| ");
                }
            } else if (path.contains("/tasks/")) {
                String[] pathParts = path.split("/");
                int id = Integer.parseInt(pathParts[2]);
                Task currentTask = task.getTask(id);
                sb.append(currentTask.getId());
                sb.append(currentTask.getName());
                sb.append(currentTask.getStatus());
                sb.append(currentTask.getpersons());
            }
            response = "HTTP/1/1 200 OK\r\n\r\n" + sb.toString();
        } else if (requestType.equals("POST")) {
            StringBuilder payload = new StringBuilder();
            TaskDao taskDao = new TaskDaoClass();
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
            HttpServer server = new HttpServer(8380);
            server.startServer();
            System.out.println("Server listening");
    }
}
