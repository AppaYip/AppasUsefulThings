package org.appa.appasUsefulThings.guiManager;

import lombok.NonNull;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.jetbrains.annotations.NotNull;


/**
 * Defines a basic gui.
 *<p>
 * This **does not** have automatic event routing, you have to implement your own event handler.
 * If you want event routing, use {@link InteractiveGui}
 */
@SuppressWarnings("unused")
public abstract class Gui {
    private String id;
    private Inventory inventory;

    public final void setId(@NonNull String id) {
        this.id = id;
    }

    /**
     * Your Gui's id. There can only be one registered per instance.
     * @return The id of the gui.
     */
    public final @NotNull String getId() {
        return this.id;
    }


    /**
     * The inventory for your gui.
     * To open this use {@link GuiManager#open(Player, String)}
     */
    public final void setInventory(@NonNull Inventory inventory) {
        this.inventory = inventory;
    }


    /**
     * @return The inventory for the gui.
     */
    public final @NotNull Inventory getInventory() {
        return this.inventory;
    }
}
