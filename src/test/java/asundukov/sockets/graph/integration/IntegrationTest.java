package asundukov.sockets.graph.integration;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class IntegrationTest {
    private static final String DONT_KNOW_RESPONSE = "SORRY, I DID NOT UNDERSTAND THAT";
    private static int port = 50000;

    @BeforeAll
    static void beforeAll() {
        port = ServerStarter.startServer(300);
    }

    @AfterAll
    static void afterAll() throws Exception {
        ServerStarter.stopServer();
    }

    @Test
    void testServer() {
        TestClient client = new TestClient();
        client.startConnection("127.0.0.1", port);
        String greeting = client.getMessage();
        assertEquals("HI, I AM ", greeting.substring(0, 9));

        client.stopConnection();
    }

    @Test
    void testGreetingFail() {
        TestClient client = new TestClient();
        client.startConnection("127.0.0.1", port);
        client.getMessage();
        client.sendMessage("HELLO BUDDY");
        String actual = client.getMessage();
        assertEquals(DONT_KNOW_RESPONSE,  actual);

        client.stopConnection();
    }

    @Test
    void testGreetingSuccess() {
        TestClient client = new TestClient();
        client.startConnection("127.0.0.1", port);
        client.getMessage();
        client.sendMessage("HI, I AM test-client");
        String actual = client.getMessage();
        assertEquals("HI test-client",  actual);

        client.stopConnection();
    }

    @Test
    void testTimeout() {
        TestClient client = getGreetedClient();
        String msg = client.getMessage();
        assertEquals("BYE test-client, WE SPOKE FOR ", msg.substring(0, 30));

        client.stopConnection();
    }


    @Test
    void testAddNodeDeleteNode() {
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
    void testAddDeleteErrors() {
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

    private TestClient getGreetedClient() {
        TestClient client = new TestClient();
        client.startConnection("127.0.0.1", port);
        client.getMessage();
        client.sendMessage("HI, I AM test-client");
        client.getMessage();
        return client;
    }

}
