package org.appa.appasUsefulThings.cooldownManager;

import lombok.NonNull;
import org.bukkit.entity.Entity;
import org.jetbrains.annotations.NotNull;
import org.jspecify.annotations.NullMarked;

import java.util.HashMap;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@SuppressWarnings("unused")
public class CooldownManager {
    private final HashMap<UUID, Long> cooldowns = new HashMap<>();

    /**
     * Sets a player cooldown
     * @param entity The entity
     * @param duration The duration in milliseconds.
     * Use {@link TimeUnit} for easy conversion
     */
    public void setCooldown(@NonNull Entity entity, long duration) {
        long expiresAt = System.currentTimeMillis() + duration;
        cooldowns.put(entity.getUniqueId(), expiresAt);
    }

    /**
     * Clears a cooldown
     * @param entity The entity
     */
    public void clearCooldown(@NonNull Entity entity) {
        cooldowns.remove(entity.getUniqueId());
    }

    /**
     * Returns a boolean based on the state of the cooldown
     * @param entity The entity
     * @return True if the cooldown is over
     */
    public boolean isOver(@NonNull Entity entity) {
        Long expiresAt = cooldowns.get(entity.getUniqueId());
        if (expiresAt == null) return true;

        if (System.currentTimeMillis() >= expiresAt) {
            cooldowns.remove(entity.getUniqueId());
            return true;
        }
        return System.currentTimeMillis() >= expiresAt;
    }

    /**
     * Gets the remaining time left on the cooldown
     * @param entity The entity
     * @return The duration left in milliseconds
     */
    public long getRemainingMillis(@NonNull Entity entity) {
        Long expiresAt = cooldowns.get(entity.getUniqueId());
        if (expiresAt == null) return 0;
        return Math.max(0, expiresAt - System.currentTimeMillis());
    }

    /**
     * Converts ticks to milliseconds.
     * @param ticks The number of ticks.
     * @return The number of milliseconds in ticks.
     */
    public long ticksToMillis(long ticks) {
        return ticks*50L;
    }
}
