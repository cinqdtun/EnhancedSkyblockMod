package com.cinqdt1.Mod.mixins;

import com.cinqdt1.Mod.events.ItemEvent;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(RenderItem.class)
public class MixinRenderItem {
    @Inject(
            at = @At("RETURN"),
            method = "renderItemOverlayIntoGUI"
    )
    public void renderItemOverlayIntoGUI(FontRenderer fr, ItemStack stack, int xPosition, int yPosition, String text, CallbackInfo ci)
    {
        MinecraftForge.EVENT_BUS.post(new ItemEvent.RenderItemOverlay(stack, fr, xPosition, yPosition));
    }
}
