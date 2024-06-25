package com.cinqdt1.Mod.mixins;

import com.cinqdt1.Mod.events.GuiContainerClickEvent;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraftforge.common.MinecraftForge;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(GuiContainer.class)
public abstract class MixinGuiContainer extends GuiScreen {
    @Shadow
    public Container inventorySlots;
    @Inject(
            at = @At("HEAD"),
            method = "handleMouseClick",
            cancellable = true
    )
    public void handleMouseClick(Slot slotIn, int slotId, int clickedButton, int clickType, CallbackInfo ci) {
        if (slotIn != null && slotIn.getStack() != null) {
            if(MinecraftForge.EVENT_BUS.post(new GuiContainerClickEvent.Pre(slotIn, this.inventorySlots.windowId, slotId, clickedButton, clickType))) ci.cancel();
        }
    }
}