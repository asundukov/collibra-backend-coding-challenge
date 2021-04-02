package asundukov.collibra.integration;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class IntegrationTest {
    private static final String DONT_KNOW_RESPONSE = "SORRY, I DID NOT UNDERSTAND THAT";
    private static int port = 50000;

    @BeforeAll
    public static void beforeAll() throws InterruptedException, IOException {
        port = ServerStarter.startServer(300);
    }

    @Test
    public void testServer() throws IOException {
        TestClient client = new TestClient();
        client.startConnection("127.0.0.1", port);
        String greeting = client.getMessage();
        assertEquals("HI, I AM ", greeting.substring(0, 9));

        client.stopConnection();
    }

    @Test
    public void testGreetingFail() throws IOException {
        TestClient client = new TestClient();
        client.startConnection("127.0.0.1", port);
        client.getMessage();
        client.sendMessage("HELLO BUDDY");
        String actual = client.getMessage();
        assertEquals(DONT_KNOW_RESPONSE,  actual);

        client.stopConnection();
    }

    @Test
    public void testGreetingSuccess() throws IOException, InterruptedException {
        TestClient client = new TestClient();
        client.startConnection("127.0.0.1", port);
        client.getMessage();
        client.sendMessage("HI, I AM test-client");
        String actual = client.getMessage();
        assertEquals("HI test-client",  actual);

        client.stopConnection();
    }

    @Test
    public void testTimeout() throws IOException {
        TestClient client = getGreetedClient();
        String msg = client.getMessage();
        assertEquals("BYE test-client, WE SPOKE FOR ", msg.substring(0, 30));

        client.stopConnection();
    }


    @Test
    public void testAddNodeDeleteNode() throws IOException, InterruptedException {
        TestClient client = getGreetedClient();
        client.sendMessage("ADD NODE node-1");
        String response = client.getMessage();

        assertEquals("NODE ADDED", response);

        client.sendMessage("REMOVE NODE node-1");
        response = client.getMessage();

        assertEquals("NODE REMOVED", response);

        client.stopConnection();
    }

    @Test
    public void testAddDeleteErrors() throws IOException, InterruptedException {
        TestClient client = getGreetedClient();
        client.sendMessage("REMOVE NODE node-1");
        String response = client.getMessage();

        assertEquals("ERROR: NODE NOT FOUND", response);

        client.sendMessage("ADD NODE node-1");
        client.getMessage();
        client.sendMessage("ADD NODE node-1");
        response = client.getMessage();

        assertEquals("ERROR: NODE ALREADY EXISTS", response);

        client.stopConnection();
    }

    private TestClient getGreetedClient() throws IOException {
        TestClient client = new TestClient();
        client.startConnection("127.0.0.1", port);
        client.getMessage();
        client.sendMessage("HI, I AM test-client");
        client.getMessage();
        return client;
    }

}
