package asundukov.sockets.graph.engine.impl;

import asundukov.sockets.graph.engine.IdGenerator;

import java.util.UUID;

public class IdGeneratorUUID4 implements IdGenerator {

    @Override
    public synchronized String getNewId() {
        return UUID.randomUUID().toString();
    }
}
