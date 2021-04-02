package asundukov.sockets.graph.engine.impl;

import asundukov.sockets.graph.engine.Engine;
import asundukov.sockets.graph.engine.IdGenerator;
import asundukov.sockets.graph.engine.MessageSender;
import asundukov.sockets.graph.engine.TimeoutDetector;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.only;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class EngineDefaultTest {

    private static final String ID = "generated_mock_id";

    private Engine engine;
    private final MessageSender messageSender = mock(MessageSender.class);
    private final IdGenerator idGenerator = mock(IdGenerator.class);
    private final TimeoutDetector timeoutDetector = mock(TimeoutDetector.class);

    public EngineDefaultTest() {
        when(idGenerator.getNewId()).thenReturn(ID);
    }

    @BeforeEach
    void beforeEach() {
        engine = new EngineDefault(idGenerator, timeoutDetector);
        reset(messageSender);
    }

    @Test
    void testConnectionGreeting() {
        engine.createSession(messageSender).start();

        verify(messageSender, only()).send("HI, I AM " + ID);
    }
}