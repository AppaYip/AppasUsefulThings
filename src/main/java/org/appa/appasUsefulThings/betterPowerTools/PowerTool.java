package org.appa.appasUsefulThings.betterPowerTools;

@FunctionalInterface
public interface PowerTool {
    /**
     * Executes the power tool's callback
     *
     * @return true if the underlying Bukkit event should be canceled
     */
    boolean execute(PowerToolContext<?> context);
}
