package asundukov.sockets.graph.engine.impl;

import asundukov.sockets.graph.engine.SessionHandler;
import asundukov.sockets.graph.engine.TimeoutDetector;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

import static java.util.concurrent.TimeUnit.MILLISECONDS;

public class TimeoutDetectorImpl implements TimeoutDetector {

    private final Map<SessionHandler, TimedSession> sessionsLastRequests = new LinkedHashMap<>();
    private final long timeoutMillis;

    public TimeoutDetectorImpl(long timeoutMillis) {
        this.timeoutMillis = timeoutMillis;
        ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();
        executorService.scheduleAtFixedRate(this::checkTimeouts, 500, 500, MILLISECONDS);
    }

    private synchronized void checkTimeouts() {
        long timeoutTime = System.currentTimeMillis() - timeoutMillis;
        List<SessionHandler> toDelete = new ArrayList<>();
        for (TimedSession timedSession : sessionsLastRequests.values()) {
            if (timedSession.lastRequest >= timeoutTime) {
                break;
            }
            CommandHandlerBye.sendBye(timedSession.sessionHandler);
            timedSession.sessionHandler.closeSession();
            toDelete.add(timedSession.sessionHandler);
        }
        toDelete.forEach(sessionsLastRequests::remove);
    }

    @Override
    public synchronized void renew(SessionHandler sessionHandler) {
        sessionsLastRequests.remove(sessionHandler);
        sessionsLastRequests.put(sessionHandler, new TimedSession(sessionHandler, System.currentTimeMillis()));
    }

    @Override
    public synchronized void cancel(SessionHandler sessionHandler) {
        sessionsLastRequests.remove(sessionHandler);
    }

    private static class TimedSession {
        private final SessionHandler sessionHandler;
        private final long lastRequest;

        private TimedSession(SessionHandler sessionHandler, long lastRequest) {
            this.sessionHandler = sessionHandler;
            this.lastRequest = lastRequest;
        }
    }
}
