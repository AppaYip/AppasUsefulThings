package org.appa.appasUsefulThings.guiManager;

import org.bukkit.inventory.ItemStack;
import org.jspecify.annotations.NullMarked;

/**
 *
 */
@NullMarked
@SuppressWarnings("unused")
public interface PagedGui extends Gui {
    /**
     * @return The page the gui is currently on.
     */
    int getCurrentPage();

    /**
     * Sets the current page of the gui.
     * This **doesn't** call {@link renderPage(int, boolean)}. Pages will not be refreshed.
     * @param page the page.
     */
    void setCurrentPage(int page);

    /**
     * These are the slots that **will** be replaced upon page changed.
     * @return A list of int (slots) that will be changed.
     */
    int[] getContentSlots();

    /**
     * These are the items that are inserted into {@link getContentSlots}
     * @return A list of items that will be changed.
     */
    ItemStack[] getItems();

    /**
     * @return Returns the maximum amount of pages possible for the gui.
     */
    default int getPageCount() {
        int[] slots = getContentSlots();
        ItemStack[] items = getItems();

        if (slots.length == 0 || items.length == 0) {
            return 0;
        }

        return (items.length + slots.length - 1) / slots.length;
    }

    /**
     * Replaces all slots from {@link getContentSlots()} with items from {@link getItems()} based on the page number.
     * @param page The page number.
     * @param clearSlots Whether to clear the {@link getContentSlots()}
     */
    default void renderPage(int page, boolean clearSlots) {
        int[] slots = getContentSlots();
        ItemStack[] items = getItems();

        if (slots.length == 0) {
            return;
        }

        if (clearSlots) {
            for (int slot : slots) {
                getInventory().clear(slot);
            }
        }

        int start = page * slots.length;

        for (int i = 0; i < slots.length; i++) {
            int itemIndex = start + i;

            if (itemIndex >= items.length) break;

            getInventory().setItem(slots[i], items[itemIndex]);
        }
    }

    /**
     * Replaces all {@link getContentSlots()} with items from {@link getItems()}.
     * If there are not enough items, air will be used, clearing slots.
     * @param page The page number.
     */
    default void renderPage(int page) {
        renderPage(page, true);
    }

    /**
     * @return Whether there is a page after the current.
     */
    default boolean hasNextPage() {
        return getCurrentPage() + 1 <  getPageCount();
    }

    /**
     * @return Whether there is a page before the current.
     */
    default boolean hasPreviousPage() {
        return getCurrentPage() > 0;
    }

    /**
     * Proceeds to the next page and renders it.
     */
    default void nextPage() {
        nextPage(true);
    }

    /**
     * Proceeds to the next page.
     * @param renderPage Whether to render the page.
     */
    default void nextPage(boolean renderPage) {
        if (hasNextPage()) {
            setPage(getCurrentPage() + 1, renderPage);
        }
    }

    /**
     * Returns to the previous page and renders it.
     */
    default void previousPage() {
        previousPage(true);
    }

    /**
     * Returns to the previous page.
     * @param renderPage Whether to render the page.
     */
    default void previousPage(boolean renderPage) {
        if (hasPreviousPage()) {
            setPage(getCurrentPage() - 1, renderPage);
        }
    }

    /**
     * Sets the current page and renders it.
     * @param page The page to change to.
     */
    default void setPage(int page) {
        setPage(page, true);
    }

    /**
     * Sets the current page.
     * @param page The page to change to.
     * @param renderPage Whether to render it.
     */
    default void setPage(int page, boolean renderPage) {
        int pageCount = getPageCount();

        if (page < 0 || page >= pageCount) {
            return;
        }

        setCurrentPage(page);
        if (renderPage) renderPage(page);
    }
}
