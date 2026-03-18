package org.appa.appasUsefulThings.cooldowns;

import java.util.HashMap;
import java.util.UUID;

public class CooldownManager {
    private HashMap<UUID, Long> cooldowns = new HashMap<>();



    // 1 tick = 50ms
    public long ticksToMillis(long ticks) {
        return ticks*50L;
    }
    public long secondsToMillis(long ticks) {
        return ticks*1000L;
    }
}
