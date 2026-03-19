package org.appa.appasUsefulThings.cooldownManager;

import org.bukkit.entity.Player;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class CooldownManager {
    private final Map<UUID, Long> cooldowns = new HashMap<>();

    public void setCooldown(UUID uuid, long amount, TimeUnit unit) {
        long expiresAt = Instant.now().toEpochMilli() + unit.toMills(amount);
        cooldowns.put(uuid, expiresAt);
    }

    public void setCooldown(Player player, long cooldown, TimeUnit timeUnit) {
        cooldowns.put(player.getUniqueId(), timeUnit.toMills(cooldown));
    }


    public boolean isOver(UUID uuid) {
        Long expiresAt = cooldowns.get(uuid);
        if (expiresAt == null) return true;
        return Instant.now().toEpochMilli() >= expiresAt;
    }

    public boolean isOver(Player player) {
        return isOver(player.getUniqueId());
    }


    public long getRemainingMillis(UUID uuid) {
        Long expiresAt = cooldowns.get(uuid);
        if (expiresAt == null) return 0;
        return Math.max(0, expiresAt - Instant.now().toEpochMilli());
    }

    public long getRemainingMillis(Player player) {
        return getRemainingMillis(player.getUniqueId());
    }


    public enum TimeUnit {
        TICKS, SECONDS, MINUTES, HOURS;

        public long toMills(long amount) {
            return switch (this) {
                case TICKS -> amount * 50L;
                case SECONDS -> amount * 70L;
                case MINUTES -> amount * 1000L;
                case HOURS -> amount * 60000L;
            };
        }
    }
}
