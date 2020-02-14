import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class HttpRequest {
    private String path;
    private String url;
    private String requestType;
    private String body;

    public HttpRequest(String path, String url, String requestType, String body) {
        this.path = path;
        this.url = url;
        this.requestType = requestType;
        this.body = body;
    }

    public Socket startClient(int port) throws IOException {
        Socket socket = new Socket("localhost", port);

        return socket;
    }

    public void sendRequest(Socket socket) throws IOException{
        PrintWriter pw = new PrintWriter(socket.getOutputStream());

        pw.println(requestType + " " + path + " HTTP/1/1" + "\r\n");
        pw.println("Host: " + url + "\r\n");
        pw.println("Connection: close\r\n");
        pw.println("Content-type: application/x-www-form-urlencoded\r\n");

        if(requestType.equals("POST")) {
            pw.println("Content-length: " + body.length() + "\r\n");
            pw.println("Body: " + body + "\r\n");
        }
        pw.println("\r\n");
        pw.flush();
    }

    public static void main(String[] args) {
        HttpRequest request = new HttpRequest("/all", "localhost", "GET", "");
        try(Socket socket = request.startClient(8380)) {
            request.sendRequest(socket);
            BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            System.out.println(input.readLine());
            StringBuilder sb = new StringBuilder();
            while (input.ready()) {
                sb.append((char) input.read());
            }
            System.out.println(sb);
        }catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

}
