package com.vulp.druidcraft.capabilities;

import java.util.concurrent.Callable;

public class TempSpawnFactory implements Callable<ITempSpawnCapability> {

    @Override
    public ITempSpawnCapability call() throws Exception {
        return new TempSpawnCapability();
    }
}
