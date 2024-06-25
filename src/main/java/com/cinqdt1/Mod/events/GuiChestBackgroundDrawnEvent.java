package com.cinqdt1.Mod.events;

import net.minecraft.client.gui.GuiScreen;
import net.minecraft.inventory.Slot;
import net.minecraftforge.fml.common.eventhandler.Event;

import java.util.List;

public class GuiChestBackgroundDrawnEvent extends Event {
    public final String displayName;
    public final int chestSize;
    public final List<Slot> slots;
    public final GuiScreen gui;
    public final int windowsId;

    public GuiChestBackgroundDrawnEvent(String displayName, int chestSize, List<Slot> slots, GuiScreen gui, int windowsId) {
        this.displayName = displayName;
        this.chestSize = chestSize;
        this.slots = slots;
        this.gui = gui;
        this.windowsId = windowsId;
    }

}
