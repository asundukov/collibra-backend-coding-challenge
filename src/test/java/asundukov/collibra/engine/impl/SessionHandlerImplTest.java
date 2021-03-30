package asundukov.collibra.engine.impl;

import asundukov.collibra.engine.CommandHandler;
import asundukov.collibra.engine.MessageSender;
import asundukov.collibra.engine.SessionHandler;
import asundukov.collibra.engine.TimeoutDetector;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

class SessionHandlerImplTest {

    private final MessageSender messageSender = mock(MessageSender.class);
    private final TimeoutDetector timeoutDetector = mock(TimeoutDetector.class);
    private final CommandHandler handler = mock(CommandHandler.class);
    private SessionHandler sessionHandler;

    @BeforeEach
    public void beforeEach() {
        reset(messageSender);
        reset(handler);
        sessionHandler = new SessionHandlerImpl("default-session-id", messageSender, timeoutDetector);
        sessionHandler.setCommandHandler(handler);
    }

    @Test
    void testToClient() {
        sessionHandler.toClient("HELLO BUDDY");
        verify(messageSender, times(1)).send("HELLO BUDDY");
        verify(handler, never()).handle(any());
    }

    @Test
    void testFromClient() {
        sessionHandler.fromClient("HI, I AM FRIEND");

        verify(handler, times(1)).handle("HI, I AM FRIEND");
        verify(messageSender, never()).send(any());
    }

}
