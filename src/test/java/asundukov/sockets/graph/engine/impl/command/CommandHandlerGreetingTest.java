package asundukov.sockets.graph.engine.impl.command;

import asundukov.sockets.graph.engine.CommandHandler;
import asundukov.sockets.graph.engine.SessionHandler;
import asundukov.sockets.graph.engine.impl.command.CommandHandlerGraph;
import asundukov.sockets.graph.engine.impl.command.CommandHandlerGraphFactory;
import asundukov.sockets.graph.engine.impl.command.CommandHandlerGreeting;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class CommandHandlerGreetingTest {
    private static final String ID = "some-session-id";
    private static final String DONT_KNOW_RESPONSE = "SORRY, I DID NOT UNDERSTAND THAT";

    private final SessionHandler sessionHandler = mock(SessionHandler.class);
    private final CommandHandlerGraphFactory commandHandlerGraphFactory = new CommandHandlerGraphFactory();

    @BeforeEach
    void beforeEach() {
        reset(sessionHandler);
        when(sessionHandler.getSessionId()).thenReturn(ID);
    }

    @Test
    void initialGreetingFromCommand() {
        new CommandHandlerGreeting(sessionHandler, commandHandlerGraphFactory);
        verify(sessionHandler, times(1)).toClient("HI, I AM " + ID);
    }

    @Test
    void correctGreetingBehavior() {
        CommandHandler handler = new CommandHandlerGreeting(sessionHandler, commandHandlerGraphFactory);

        CommandHandler newHandler = handler.handle("HI, I AM unit-test");

        verify(sessionHandler, times(1)).setClientName("unit-test");
        assertTrue(newHandler instanceof CommandHandlerGraph);
    }

    @Test
    void correctGreetingResponse() {
        when(sessionHandler.getClientName()).thenReturn("unit-test");
        CommandHandler handler = new CommandHandlerGreeting(sessionHandler, commandHandlerGraphFactory);

        handler.handle("HI, I AM unit-test");

        verify(sessionHandler, times(1)).toClient("HI unit-test");
    }

    @Test
    void incorrectRequest() {
        CommandHandler handler = new CommandHandlerGreeting(sessionHandler, commandHandlerGraphFactory);

        CommandHandler newActualHandler = handler.handle("HELLO, I AM someone");

        verify(sessionHandler, times(1)).toClient(DONT_KNOW_RESPONSE);
        assertEquals(handler, newActualHandler);
    }

    @Test
    void incorrectClientName() {
        CommandHandler handler = new CommandHandlerGreeting(sessionHandler, commandHandlerGraphFactory);

        handler.handle("HELLO, I AM C3PO_");

        verify(sessionHandler, times(1)).toClient(DONT_KNOW_RESPONSE);
    }

}