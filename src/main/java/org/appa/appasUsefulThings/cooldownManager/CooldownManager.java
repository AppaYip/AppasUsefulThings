package org.appa.appasUsefulThings.cooldownManager;

import lombok.NonNull;
import org.bukkit.entity.Entity;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * This class is a helper that provides utility for storing and tracking cooldowns for entities.
 * Cooldowns are per {@link CooldownManager} instance based.
 * Methods do use {@link System#currentTimeMillis()} for tracking time.
 * This means these methods **do not** take into account lag/server tps drops.
 */
@SuppressWarnings("unused")
public class CooldownManager {
    private final Map<UUID, Long> cooldowns = new HashMap<>();

    /**
     * Sets the cooldown of an entity.
     * @param entity The entity.
     * @param duration The duration of the cooldown.
     * @param unit The {@link TimeUnit} for the cooldown.
     */
    public void setCooldown(@NonNull Entity entity, long duration, @NonNull TimeUnit unit) {
        setCooldown(entity, unit.toMillis(duration));
    }

    /**
     * Sets the cooldown of an entity.
     * @param entity The entity.
     * @param duration The duration in milliseconds.
     * @throws IllegalArgumentException if the duration is negative.
     * Use {@link TimeUnit} for easy conversion.
     */
    public void setCooldown(@NonNull Entity entity, long duration) {
        if (duration < 0) throw new IllegalArgumentException("Cooldown duration cannot be negative");
        if (duration == 0) return; // We don't need to care about 0 duration cooldowns.

        cooldowns.put(entity.getUniqueId(), System.currentTimeMillis() + duration);
    }

    /**
     * Clears an entity's cooldown.
     *
     * @param entity The entity.
     */
    public void clearCooldown(@NonNull Entity entity) {
        cooldowns.remove(entity.getUniqueId());
    }

    /**
     * Checks if an entity's cooldown is over.
     * If no cooldown is present, this will return true.
     *
     * @param entity The entity.
     * @return true if the entity currently has an active cooldown.
     */
    public boolean isOnCooldown(@NonNull Entity entity) {
        UUID uuid = entity.getUniqueId();
        Long expiresAt = cooldowns.get(uuid);

       if (expiresAt == null) return false;

       if (System.currentTimeMillis() >= expiresAt) {
            cooldowns.remove(uuid);
            return false;
        }

        return true;
    }

    /**
     * Gets the remaining time left on the cooldown.
     *
     * @param entity The entity
     * @return Remaining time in milliseconds, or 0 if no cooldown is active.
     */
    public long getRemainingMillis(@NonNull Entity entity) {
        UUID uuid = entity.getUniqueId();
        Long expiresAt = cooldowns.get(uuid);
        if (expiresAt == null) return 0;

        long remaining = expiresAt - System.currentTimeMillis();

        if (remaining <= 0) {
            cooldowns.remove(uuid);
            return 0;
        }

        return remaining;
    }

    /**
     * Converts Minecraft ticks to milliseconds.
     *
     * @param ticks The number of ticks.
     * @return The milliseconds in ticks.
     */
    public static long ticksToMillis(long ticks) {
        return ticks * 50L;
    }
}
