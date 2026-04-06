package org.appa.appasUsefulThings.random;

import net.kyori.adventure.text.Component;

/**
 * This is a class filled with random utility methods
 * that don't exactly fit anywhere in the project as of writing them
 * Feel free to use them, they will be JavaDoced... probably not on GitHub though
 */

public class random {

    public static Component prepend(Component prefix, Component original) {
        return prefix.append(original);
    }

    public static Component prepend(String prefix, Component original) {
        return prepend(Component.text(prefix), original);
    }
}
