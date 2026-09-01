package org.appa.appasUsefulThings.guiManager;

import lombok.NonNull;
import net.kyori.adventure.text.Component;
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
     *
     * @return The id of the gui.
     */
    @NonNull
    String getId();

    /**
     * The inventory for your gui.
     * To open this use {@link GuiManager#open(Player, String)}.
     * When creating an inventory, the title **will not** be set. To change the title, use {@link Gui#getTitle()}.
     * Due to not being able to pass in {@code null}, or use a String due to deprecation by paper, I recommend using {@link Component#empty()}
     *
     * @return The inventory for the gui.
     */
     Inventory getInventory();

    /**
     * Gets the title for the inventory.
     * This **will** override the inventories existing title upon open. See {@link Gui#getInventory()}
     *
     * @return The inventories title.
     */
    default Component getTitle() {
        return Component.empty();
    }
}
