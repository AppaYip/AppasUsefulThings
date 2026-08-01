package org.appa.appasUsefulThings.betterPowerTools;


import java.util.*;

@SuppressWarnings("unused")
public class PowerToolRegistry {
    private final Map<String, PowerTool> powerTools = new HashMap<>();

    /**
     * Registers a power tool under id.
     * <p>
     * Registered power tools can later be retrieved by their id and
     * executed when an item with the matching PDC tag is used.
     * <p>
     * If another power tool is registered with the same id, an exception wil be thrown.
     * <p>
     * @param id The unique power tool id.
     * @param tool The power tool implementation.
     */
    public void registerIfAbsent(String id, PowerTool tool) {
        id = id.toLowerCase();
        if (exists(id)) {
            throw new IllegalStateException(
                    "Power Tool '%s' is already registered.".formatted(id)
            );
        }
        powerTools.put(id, tool);
    }

    /**
     * Registers a power tool under id.
     * This will silently override power tools with the same id.
     * <p>
     * @param id The unique power tool id.
     * @param tool The power tool implementation.
     */
    public void register(String id, PowerTool tool) {
        id = id.toLowerCase();
        powerTools.put(id, tool);
    }


    /**
     * Unregisters the power tool id.
     * If no power tool is registered under the id, this method does nothing.
     * @param id The id.
     */
    public void unregister(String id) {
        id = id.toLowerCase();
        powerTools.remove(id);
    }

    /**
     * Returns the power tool registered under id.
     *
     * @param id The power tool id.
     * @return An {@link Optional} containing the registered power tool.
     *      or an empty optional if no power tool exists.
     */
    public Optional<PowerTool> get(String id) {
        id = id.toLowerCase();
        return Optional.ofNullable(powerTools.get(id));
    }

    /**
     * Returns whether a power tool is registered under id.
     * <p>
     * @param id The power tool id.
     * @return {@code true} if a power tool exists.
     */
    public boolean exists(String id) {
        id = id.toLowerCase();
        return powerTools.containsKey(id);
    }

    /**
     * Returns an immutable set of all registered power tool ids.
     *
     * @return Unmodifiable set of ids.
     */
    public Set<String> getIds() {
        return Collections.unmodifiableSet(powerTools.keySet());
    }
}