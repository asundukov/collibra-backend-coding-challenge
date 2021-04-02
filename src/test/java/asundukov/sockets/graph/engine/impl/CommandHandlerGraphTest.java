package asundukov.sockets.graph.engine.impl;

import asundukov.sockets.graph.engine.CommandHandler;
import asundukov.sockets.graph.engine.SessionHandler;
import asundukov.sockets.graph.engine.graph.Graph;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class CommandHandlerGraphTest {
    private static final String DONT_KNOW_RESPONSE = "SORRY, I DID NOT UNDERSTAND THAT";

    private final SessionHandler sessionHandler = mock(SessionHandler.class);
    private final Graph graph = mock(Graph.class);
    private CommandHandler handler;

    @BeforeEach
    public void beforeEach() {
        reset(sessionHandler);
        when(sessionHandler.getClientName()).thenReturn("JHON");
        handler = new CommandHandlerGraph(sessionHandler, graph);
    }

    @Test
    void handleBye() {
        CommandHandler nextHandler = handler.handle("BYE MATE!");

        verify(sessionHandler, times(1)).toClient(anyString());
        assertTrue(nextHandler instanceof CommandHandlerBye);
    }

    @Test
    void handleWrong() {
        CommandHandler nextHandler = handler.handle("SOMECOMMAND");

        verify(sessionHandler, times(1)).toClient(DONT_KNOW_RESPONSE);
        assertEquals(handler, nextHandler);
    }

}