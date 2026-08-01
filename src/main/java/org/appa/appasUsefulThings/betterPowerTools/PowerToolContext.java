package org.appa.appasUsefulThings.betterPowerTools;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nullable;

public record PowerToolContext<T> (
        Player player,
        ItemStack item,
        PowerToolEventType type,
        @Nullable T eventData
) {}
