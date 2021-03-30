package asundukov.collibra.engine.impl;

import asundukov.collibra.engine.IdGenerator;

import java.util.UUID;

public class IdGeneratorUUID4 implements IdGenerator {

    @Override
    public synchronized String getNewId() {
        return UUID.randomUUID().toString();
    }
}
