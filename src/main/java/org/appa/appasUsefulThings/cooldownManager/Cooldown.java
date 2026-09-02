package org.appa.appasUsefulThings.cooldownManager;

import lombok.NonNull;
import org.bukkit.entity.Entity;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * Represents a single named cooldown.
 *
 * <p>All cooldown data is managed by the associated {@link CooldownManager}.</p>
 */
public class Cooldown {
    private final CooldownManager cooldownManager;
    private final String id;

    /**
     * Creates a cooldown handle.
     *
     * @param cooldownManager The manager that owns the cooldown.
     * @param id The unique cooldown ID.
     */
    Cooldown(@NonNull CooldownManager cooldownManager, @NonNull String id) {
        this.cooldownManager = cooldownManager;
        this.id = id;
    }

    /**
     * Sets the cooldown of an entity.
     *
     * @param entity The entity.
     * @param duration The duration of the cooldown.
     * @param unit The {@link TimeUnit} for the cooldown.
     */
    public void setCooldown(@NonNull Entity entity, long duration, @NonNull TimeUnit unit) {
        cooldownManager.set(this.id, entity.getUniqueId(), unit.toMillis(duration));
    }

    /**
     * Sets the cooldown of an entity.
     *
     * <p>A duration of {@code 0} clears the cooldown.</p>
     *
     * @param entity The entity.
     * @param duration The duration in milliseconds.
     *
     * @throws IllegalArgumentException if the duration is negative.
     */
    public void setCooldown(@NonNull Entity entity, long duration) {
        if (duration < 0) throw new IllegalArgumentException("Cooldown duration cannot be negative");

        UUID uuid = entity.getUniqueId();

        if (duration == 0) cooldownManager.remove(id, uuid);

        cooldownManager.set(id, uuid, System.currentTimeMillis() + duration);
    }

    /**
     * Clears an entity's cooldown.
     *
     * @param entity The entity.
     */
    public boolean isActive(@NonNull Entity entity) {
        return (remaining(entity) > 0);
    }

    /**
     * Gets the remaining time left on the cooldown.
     *
     * @param entity The entity
     * @return Remaining time in milliseconds, or 0 if no cooldown is active.
     */
    public long remaining(@NonNull Entity entity) {
        UUID uuid = entity.getUniqueId();

        Long expiresAt = cooldownManager.get(this.id, uuid);

        if (expiresAt == null) return 0;

        long remaining = expiresAt - System.currentTimeMillis();

        if (remaining <= 0) {
            cooldownManager.remove(this.id, uuid);
            return 0;
        }

        return remaining;
    }

    /**
     * Clears an entity's cooldown.
     *
     * @param entity The entity.
     */
    public void clear(@NonNull Entity entity) {
        cooldownManager.remove(this.id, entity.getUniqueId());
    }

    /**
     * Gets the unique ID of this cooldown.
     *
     * @return The cooldown ID.
     */
    public @NotNull String getId() {
        return this.id;
    }
}
