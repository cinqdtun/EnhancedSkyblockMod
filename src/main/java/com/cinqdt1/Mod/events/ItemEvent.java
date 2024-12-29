package com.cinqdt1.Mod.events;

import net.minecraft.client.gui.FontRenderer;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.eventhandler.Event;

public class ItemEvent extends Event {
    public final ItemStack itemStack;
    public ItemEvent(ItemStack itemStack){
        this.itemStack = itemStack;
    }
    public static class RenderItemOverlay extends ItemEvent{
        public final FontRenderer fontRenderer;
        public final int xPos;
        public final int yPos;

        public RenderItemOverlay(ItemStack itemStack, FontRenderer fontRenderer, int xPos, int yPos){
            super(itemStack);
            this.fontRenderer = fontRenderer;
            this.xPos = xPos;
            this.yPos = yPos;
        }
    }
}
