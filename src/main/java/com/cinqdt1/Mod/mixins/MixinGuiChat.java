package com.cinqdt1.Mod.mixins;


import net.minecraft.client.gui.GuiChat;
import net.minecraft.client.gui.GuiScreen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.io.IOException;

@Mixin(GuiChat.class)
public class MixinGuiChat extends GuiScreen {
    @Inject(
            at = @At("HEAD"),
            method = "keyTyped",
            cancellable = true
    )
    protected void keyTyped(char typedChar, int keyCode, CallbackInfo ci) throws IOException
    {
        /*
        if (MinecraftForge.EVENT_BUS.post(new ChatKeyEvent.Press(typedChar, keyCode)))
        {
            ci.cancel();
        };*/
    }
}

