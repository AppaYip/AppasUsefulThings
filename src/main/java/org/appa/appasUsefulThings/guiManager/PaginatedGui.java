package org.appa.appasUsefulThings.guiManager;

import org.bukkit.inventory.ItemStack;

import java.util.List;

/**
 * Represent a GUI with content divided across pages.
 *
 * <p>Page indexes start at zero. The current page is automatically rendered when changing pages</p>
 *
 * <p>Subclasses are responsible for providing the inventory content slots and items to display.
 * The items are placed into the content slots in order.</p>
 */
@SuppressWarnings("unused")
public abstract class PaginatedGui extends Gui {
    private int currentPage;

    /**
     * @return The index of the currently selected page.
     */
    public final int getCurrentPage() {
        return currentPage;
    }

    /**
     * Gets the inventory slots used for page content.
     *
     * <p>Items are placed into these slots in the order they are returned.
     * The number of slots determines how many items can be displayed on each page.
     * </p>
     *
     * @return The slots used for page contents.
     */
    public abstract int[] getContentSlots();

    /**
     * Gets the items displayed across pages of this GUI.
     *
     * <p>Items are distributed across pages in the order they are returned.
     * The number of items that can be displayed on each page is determined by {@link #getContentSlots()}
     * </p>
     *
     * @return The items displayed by this GUI.
     */
    public abstract List<ItemStack> getItems();

    /**
     * Gets the total number of pages available.
     *
     * <p>Returns 0 when there are no content slots or no items.</p>
     *
     * @return The number of pages available.
     */
    public final int getPageCount() {
        int pageSize = getContentSlots().length;
        int itemCount = getItems().size();

        if (pageSize == 0 || itemCount == 0) {
            return 0;
        }

        return (itemCount + pageSize - 1) / pageSize;
    }

    /**
     * Checks whether the given page exists.
     *
     * @param page the page index to check. Page indexes start at 0.
     * @return Whether the page exists.
     */
    public final boolean hasPage(int page) {
        return page >= 0 && page < getPageCount();
    }

    /**
     * @return Whether another page exists after the current page.
     */
    public final boolean hasNextPage() {
        return hasPage(currentPage +1);
    }

    /**
     * @return Whether another page exists before the current page.
     */
    public final boolean hasPreviousPage() {
        return hasPage(currentPage -1);
    }

    /**
     * Proceeds to the next page and renders it.
     *
     * <p>If the current page is the last page, this method does nothing.</p>
     */
    public final void nextPage() {
        if (hasNextPage()) setPage(currentPage + 1);
    }

    /**
     * Returns to the previous page and renders it.
     *
     * <p>If the current page is the first page, this method does nothing.</p>
     */
    public final void previousPage() {
        if (hasPreviousPage()) setPage(currentPage -1);
    }

    /**
     * Sets the current page and renders it.
     *
     * @param page the page index. This starts at 0.
     * @throws IllegalArgumentException if {@code page} is outside the valid page range.
     */
    public final void setPage(int page) {
        int pageCount = getPageCount();

        if (page < 0 || page >= pageCount) {
            throw new IllegalArgumentException(
                    "Invalid page %s; page count is %s".formatted(page, pageCount)
            );
        }

        this.currentPage = page;
        renderPage();
    }

    /**
     * Renders the current page.
     * This will clear content slots.
     */
    public final void renderPage() {
        renderPage(this.currentPage, true);
    }

    /**
     * Renders the current page.
     *
     * @param clearSlots Whether to clear the current slots.
     */
    public final void renderPage(boolean clearSlots) {
        renderPage(this.currentPage, clearSlots);
    }

    /**
     * Renders a page.
     *
     * @param page The page to render.
     */
    protected final void renderPage(int page) {
        renderPage(page, true);
    }

    /**
     * Renders a page.
     *
     * @param page The page to render.
     * @param clearSlots Whether to clear the content slots before rendering.
     * @throws IllegalArgumentException If the page does not exist.
     */
    public final void renderPage(int page, boolean clearSlots) {
        if (!hasPage(page)) {
            throw new IllegalArgumentException("Invalid page %s; page count is %s".formatted(page, getPageCount()));
        }

        int[] slots = getContentSlots();
        List<ItemStack> items = getItems();

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

            if (itemIndex >= items.size()) break;

            getInventory().setItem(slots[i], items.get(itemIndex));
        }
    }
}
