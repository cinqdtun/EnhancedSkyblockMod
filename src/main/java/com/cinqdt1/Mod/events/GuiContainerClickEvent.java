package com.cinqdt1.Mod.events;

import net.minecraft.inventory.Slot;
import net.minecraftforge.fml.common.eventhandler.Cancelable;
import net.minecraftforge.fml.common.eventhandler.Event;

public class GuiContainerClickEvent extends Event {

    public final Slot slotIn;
    public final int windowId;
    public final int slotId;
    public final int mouseButtonClicked;
    public final int mode;

    public GuiContainerClickEvent(Slot slotIn, int windowId, int slotId, int mouseButtonClicked, int mode)
    {
        this.slotIn = slotIn;
        this.windowId = windowId;
        this.slotId = slotId;
        this.mouseButtonClicked = mouseButtonClicked;
        this.mode = mode;
    }
    @Cancelable
    public static class Pre extends GuiContainerClickEvent
    {
        public Pre(Slot slotIn, int windowId, int slotId, int mouseButtonClicked, int mode)
        {
            super(slotIn, windowId, slotId, mouseButtonClicked, mode);
        }
    }
}