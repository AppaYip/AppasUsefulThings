package org.appa.appasUsefulThings.guiManager;

import org.bukkit.inventory.ItemStack;
import org.jspecify.annotations.NullMarked;

@NullMarked
@SuppressWarnings("unused")
public interface PagedGui extends Gui {
    int getCurrentPage();

    void setCurrentPage(int page);

    int[] getPageSlots();
    ItemStack[] getItems();

    default int getPageCount() {
        int[] slots = getPageSlots();
        ItemStack[] items = getItems();

        if (slots.length == 0 || items.length == 0) {
            return 0;
        }

        return (items.length + slots.length - 1) / slots.length;
    }


    default void renderPage(int page, boolean clearSlots) {
        int[] slots = getPageSlots();
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

    default void renderPage(int page) {
        renderPage(page, true);
    }


    default boolean hasNextPage() {
        return getCurrentPage() + 1 <  getPageCount();
    }

    default boolean hasPreviousPage() {
        return getCurrentPage() > 0;
    }

    default void nextPage() {
        nextPage(true);
    }

    default void nextPage(boolean renderPage) {
        if (hasNextPage()) {
            setPage(getCurrentPage() + 1, renderPage);
        }
    }

    default void previousPage() {
        previousPage(true);
    }

    default void previousPage(boolean renderPage) {
        if (hasPreviousPage()) {
            setPage(getCurrentPage() - 1, renderPage);
        }
    }

    default void setPage(int page) {
        setPage(page, true);
    }

    default void setPage(int page, boolean renderPage) {
        int pageCount = getPageCount();

        if (page < 0 || page >= pageCount) {
            return;
        }

        setCurrentPage(page);
        renderPage(page);
    }
}
