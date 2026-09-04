package org.appa.appasUsefulThings.cooldownManager;

import lombok.NonNull;
import org.jetbrains.annotations.NotNull;
import org.jspecify.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * This class is a helper that provides utility for storing and tracking cooldowns for entities.
 * Cooldowns are per {@link CooldownManager} instance based.
 * Methods do use {@link System#currentTimeMillis()} for tracking time.
 * This means these methods **do not** take into account lag/server tps drops.
 */
@SuppressWarnings("unused")
public class CooldownManager {
    private final Map<CooldownKey, Long> cooldowns = new HashMap<>();
    private final Map<String, Cooldown> handles = new HashMap<>();


    /**
     * Gets a named cooldown.
     *
     * <p>Calls with the same ID will return the same cooldown handle.</p>
     *
     * @param id The unique cooldown ID.
     * @return The cooldown handle.
     */
    public @NotNull Cooldown cooldown(@NonNull String id ) {
        if (id.isBlank())
            throw new IllegalArgumentException("Cooldown ID cannot be blank");

        return handles.computeIfAbsent(id, key -> new Cooldown(this, key));
    }

    /**
     * Sets the expiration time for an entity's cooldown.
     *
     * @param id The cooldown ID.
     * @param uuid The entity UUID.
     * @param expiresAt The expiration time in ms.
     */
    void set(@NonNull String id, @NonNull UUID uuid, long expiresAt) {
        cooldowns.put(new CooldownKey(id, uuid), expiresAt);
    }

    /**
     * Gets the expiration time for an entity's cooldown.
     *
     * @param id The cooldown ID.
     * @param uuid The entity UUID.
     * @return The expiration time, or {@code null} if no cooldown exists.
     */
    @Nullable Long get(@NonNull String id, @NonNull UUID uuid) {
       return cooldowns.get(new CooldownKey(id, uuid));
    }

    /**
     * Removes an entity's cooldown
     *
     * @param id The down ID.
     * @param uuid The entity UUID.
     */
    void remove(@NonNull String id,  @NonNull UUID uuid) {
        cooldowns.remove(new CooldownKey(id, uuid));
    }

    private record CooldownKey (
        @NonNull String id,
        @NonNull UUID uuid
    ) {}
}
