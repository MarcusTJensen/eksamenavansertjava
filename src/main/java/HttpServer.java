import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class HttpServer {
    int port;
    public HttpServer(int port) throws IOException {
        this.port = port;
    }
    public void startServer() throws IOException {
        new Thread(() -> {
            try {
                ServerSocket server = new ServerSocket(port);

                while (true) {
                    Socket socket = server.accept();
                    BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                    String headers = br.readLine();
                    String path = headers.split(" ")[1];
                    String requestType = headers.split(" ")[0];
                    System.out.println(path);
                    if (requestType.equals("GET")) {
                        ArrayList tasks;
                        TaskDao task = new TaskDaoClass();
                        tasks = task.getAllTasks();
                        StringBuilder sb = new StringBuilder();
                        for (int i = 0; i < tasks.size(); i++) {
                            Task currentTask = (Task) tasks.get(i);
                            sb.append(currentTask.getName() + " ");
                            sb.append(currentTask.getStatus() + " ");
                            sb.append(currentTask.getpersons() + "| ");
                        }
                        String response = "HTTP/1/1 200 OK\r\n\r\n" + sb.toString();
                        socket.getOutputStream().write(response.getBytes());
                    }
                    StringBuilder payload = new StringBuilder();
                    while (br.ready()) {
                        payload.append((char) br.read());
                    }
                    System.out.println(payload);
                }
            }catch (IOException e) {
                System.out.println(e.getMessage());
            }
        }).start();
    }

    public static void main(String[] args) {
        try {
            HttpServer server = new HttpServer(8380);
            server.startServer();
            System.out.println("Server listening");
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}
