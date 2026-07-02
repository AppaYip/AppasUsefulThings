package org.appa.appasUsefulThings.random;

import net.kyori.adventure.text.Component;

/**
 * This is a class filled with random utility methods
 * that don't exactly fit anywhere in the project as of writing them
 * Feel free to use them, they will be JavaDoced.
 */

@SuppressWarnings("unused")
public class Random {

    /**
     * Adds a component to the start of a component.
     * @param prefix The component to add to the start.
     * @param original The original Component.
     * @return A prefixed component
     */
    public static Component prepend(Component prefix, Component original) {
        return prefix.append(original);
    }

    /**
     * Adds a string to the start of a component.
     * @param prefix The string to add to the start.
     * @param original The original Component.
     * @return A prefixed component
     */
    public static Component prepend(String prefix, Component original) {
        return prepend(Component.text(prefix), original);
    }
}
