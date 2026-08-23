package org.appa.appasUsefulThings.guiManager;

import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.jspecify.annotations.NullMarked;


/**
 * Defines a basic gui.
 *<p>
 * This **does not** have automatic event routing, you have to implement your own event handler.
 * If you want event routing, use {@link InteractiveGui}
 */
@NullMarked
public interface Gui {
    /**
     * Your Gui's id. There can only be one registered per instance.
     * @return The id of the gui.
     */
    String getId();

    /**
     * The inventory for you gui.
     * To open this use {@link GuiManager#open(Player, String)}
     *
     * @return The inventory of the gui.
     */
    Inventory getInventory();
}
