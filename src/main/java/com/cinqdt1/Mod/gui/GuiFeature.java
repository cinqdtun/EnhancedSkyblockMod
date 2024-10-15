package com.cinqdt1.Mod.gui;

import net.minecraft.item.ItemStack;

import java.util.List;

public class GuiFeature {
    private int id;
    private int xTextOffset;
    private int yTextOffset;
    private int linesOffset;
    private String coordinatesCategory;
    private List<String> text;
    private ItemStack displayItem;

    public GuiFeature(int id, int xTextOffset, int yTextOffset, int linesOffset, String coordinatesCategory, List<String> text, ItemStack displayItem) {
        this.id = id;
        this.xTextOffset = xTextOffset;
        this.yTextOffset = yTextOffset;
        this.linesOffset = linesOffset;
        this.coordinatesCategory = coordinatesCategory;
        this.text = text;
        this.displayItem = displayItem;
    }

    public int getId() {
        return id;
    }

    public int getxTextOffset() {
        return xTextOffset;
    }

    public int getyTextOffset() {
        return yTextOffset;
    }

    public int getLinesOffset() {
        return linesOffset;
    }
    public String getCoordinatesCategory() {
        return coordinatesCategory;
    }

    public List<String> getText() {
        return text;
    }

    public ItemStack getDisplayItem() {
        return displayItem;
    }

}
