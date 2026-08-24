package org.appa.appasUsefulThings.guiManager;

import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.inventory.Inventory;
import org.jspecify.annotations.NullMarked;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * This class allows for registering a gui, opening/closing a gui, and many more things.
 * Each method has Javadocs, and it is encouraged that you read them.
 */
@NullMarked
@SuppressWarnings("unused")
public class GuiManager implements Listener {
    private final Map<String, Gui> guis = new HashMap<>();
    private final Map<UUID, Gui> openGuis = new HashMap<>();
    /**
     * Registers a gui. See {@link Gui} for more information.
     * @param gui The gui
     */
    public void registerGui(Gui gui) {
        registerGui(gui, false);
    }

    /**
     * Registers a gui. See {@link Gui} for more information.
     * @param overwrite Whether to override an already existing gui.
     */
    public void registerGui(Gui gui, boolean overwrite) {
        if (overwrite) {
            guis.put(gui.getId(), gui);
            return;
        }

        if (guis.containsKey(gui.getId())) {
            throw new IllegalStateException(
                "GUI with id '%s' is already registered in this instance of GuiManager".formatted(gui.getId())
            );
        }
        guis.put(gui.getId(), gui);
    }


    /**
     * Removes a {@link Gui} from registry.
     * @param gui The gui
     */
    public void unregisterGui(Gui gui) {
        guis.remove(gui.getId());
    }

    /**
     * Opens a {@link Gui} to a player. This is required to route events to your gui.
     * @param player The player
     * @param gui An instance of a {@link Gui}
     */
    public void open(Player player, Gui gui) {
        Inventory inventory = gui.getInventory();

        openGuis.put(player.getUniqueId(), gui);
        player.openInventory(inventory);
    }

    /**
     * Opens a {@link Gui} to a player. This is required to route events to your gui.
     * @param player The player
     * @param guiId The id of a {@link Gui}
     */
    public void open(Player player, String guiId) {
        Gui gui = guis.get(guiId);
        if (gui == null) {
            throw new IllegalStateException(
                "Player '%s' tried to open GUI with id '%s' but gui is null".formatted(
                        player.getName(), guiId
                )
            );
        }
        open(player, gui);
    }

    /**
     * Clears the players open {@link Gui}.
     * This does automatically and typically does not need to be invoked.
     * <p>
     * This **will not** trigger if the reason for closing is opening another gui.
     * This is done to allow gui editing without breaking event routing.
     * @param player The player
     */
    public void close(Player player) {
        openGuis.remove(player.getUniqueId());
    }

    /**
     * Checks if a player has a gui opened.
     * @param player The player
     * @return whether the player has a {@link Gui} opened.
     */
    public boolean isOpen(Player player) {
        return openGuis.containsKey(player.getUniqueId());
    }
}
