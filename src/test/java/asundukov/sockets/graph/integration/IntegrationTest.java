package asundukov.sockets.graph.integration;

import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class IntegrationTest {
    private static final String DONT_KNOW_RESPONSE = "SORRY, I DID NOT UNDERSTAND THAT";
    private static String ip = "127.0.0.1";
    private static int port = 50000;

    private TestClient client;

    @BeforeAll
    static void beforeAll() {
        port = ServerStarter.startServer(300);
    }

    @AfterAll
    static void afterAll() throws Exception {
        ServerStarter.stopServer();
    }

    @BeforeEach
    void beforeEach() {
        client = new TestClient();
        client.startConnection(ip, port);
    }

    @AfterEach
    void afterEach() {
        client.stopConnection();
    }

    @Test
    void testServer() {
        String greeting = client.getMessage();

        assertEquals("HI, I AM ", greeting.substring(0, 9));
    }

    @Test
    void testGreetingFail() {
        client.getMessage();
        String actual = client.sendAndGetMessage("HELLO BUDDY");

        assertEquals(DONT_KNOW_RESPONSE,  actual);
    }

    @Test
    void testGreetingSuccess() {
        client.getMessage();
        String actual = client.sendAndGetMessage("HI, I AM test-client");

        assertEquals("HI test-client",  actual);
    }

    @Test
    void testTimeout() {
        setGeetedClient();
        String msg = client.getMessage();

        assertEquals("BYE test-client, WE SPOKE FOR ", msg.substring(0, 30));
    }


    @Test
    void testAddNodeDeleteNode() {
        setGeetedClient();
        String response = client.sendAndGetMessage("ADD NODE node-1");

        assertEquals("NODE ADDED", response);

        response = client.sendAndGetMessage("REMOVE NODE node-1");

        assertEquals("NODE REMOVED", response);
    }

    @Test
    void testAddDeleteErrors() {
        setGeetedClient();
        String response = client.sendAndGetMessage("REMOVE NODE node-1");

        assertEquals("ERROR: NODE NOT FOUND", response);

        client.sendAndGetMessage("ADD NODE node-1");
        response = client.sendAndGetMessage("ADD NODE node-1");

        assertEquals("ERROR: NODE ALREADY EXISTS", response);
    }

    @Test
    void differentClients() {
        setGeetedClient();
        client.sendAndGetMessage("ADD NODE node-111");
        client.sendAndGetMessage("ADD NODE node-112");
        client.sendAndGetMessage("ADD EDGE node-112 node-111 10");

        TestClient client2 = new TestClient();
        client2.startConnection(ip, port);

        client2.getMessage();
        client2.sendAndGetMessage("HI, I AM client-2");
        String response = client2.sendAndGetMessage("SHORTEST PATH node-112 node-111");
        client2.stopConnection();

        assertEquals("10", response);
    }


    private void setGeetedClient() {
        client.getMessage();
        client.sendAndGetMessage("HI, I AM test-client");
    }

}
