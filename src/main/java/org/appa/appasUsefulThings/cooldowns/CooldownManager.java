package org.appa.appasUsefulThings.cooldowns;

import org.bukkit.entity.Entity;

import java.util.HashMap;
import java.util.UUID;

public class CooldownManager {
    private final HashMap<UUID, Long> cooldowns = new HashMap<>();

    /**
     * Sets a player cooldown
     * @param entity The entity
     * @param duration The duration in milliseconds.
     * Use TimeUnit for easy conversion
     */
    public void setCooldown(Entity entity, Long duration) {
        long expiresAt = System.currentTimeMillis() + duration;
        cooldowns.put(entity.getUniqueId(), expiresAt);
    }

    /**
     * Clears a cooldown
     * @param entity The entity
     */
    public void clearCooldown(Entity entity) {
        cooldowns.remove(entity.getUniqueId());
    }

    /**
     * Returns a boolean based on the state of the cooldown
     * @param entity The entity
     * @return True if the cooldown is over
     */
    public boolean isOver(Entity entity) {
        Long expiresAt = cooldowns.get(entity.getUniqueId());
        if (expiresAt == null) return true;
        return System.currentTimeMillis() >= expiresAt;
    }

    /**
     * Gets the remaining time left on the cooldown
     * @param entity The entity
     * @return The duration left in milliseconds
     */
    public long getRemainingMillis(Entity entity) {
        Long expiresAt = cooldowns.get(entity.getUniqueId());
        if (expiresAt == null) return 0;
        return Math.max(0, expiresAt - System.currentTimeMillis());
    }

    // 1 tick = 50ms
    public long ticksToMillis(long ticks) {
        return ticks*50L;
    }
    public long millisToSeconds(long millis) {
        return millis / 1000L;
    }

}
