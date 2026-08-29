package org.appa.appasUsefulThings.guiManager;

import lombok.NonNull;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;


/**
 * Defines a basic gui.
 *<p>
 * This **does not** have automatic event routing, you have to implement your own event handler.
 * If you want event routing, use {@link InteractiveGui}
 */
@SuppressWarnings("unused")
public interface Gui {
    /**
     * Your Gui's id. There can only be one registered per instance.
     * @return The id of the gui.
     */
    @NonNull
    String getId();

    /**
     * The inventory for your gui.
     * To open this use {@link GuiManager#open(Player, String)}
     * @return The inventory for the gui.
     */
     Inventory getInventory();
}
