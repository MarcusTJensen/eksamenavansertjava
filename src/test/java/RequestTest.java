import org.junit.Assert;
import org.junit.Test;
import static org.junit.Assert.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class RequestTest {
    @Test
    public void checkGetRequest() throws IOException {
        HttpRequest request = new HttpRequest("/all", "localhost", "GET", "");
        Socket socket = new Socket("localhost", 8380);
        request.sendRequest(socket);
        BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        assertTrue(!(input.readLine().isEmpty()));
    }
}
