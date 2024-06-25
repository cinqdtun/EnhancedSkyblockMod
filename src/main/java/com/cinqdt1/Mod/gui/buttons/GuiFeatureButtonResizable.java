package com.cinqdt1.Mod.gui.buttons;

import com.cinqdt1.Mod.gui.EditLocations;
import com.cinqdt1.Mod.utils.Utils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.SoundHandler;
import net.minecraft.client.gui.GuiButton;

public class GuiFeatureButtonResizable extends GuiButton {
    public final EditLocations.FeatureButton feature;
    private final int defaultButtonColor = Utils.getIntColorFromRGBAColor(255,255,255,150);


    public GuiFeatureButtonResizable(GuiFeatureButton button, EditLocations.FeatureButton feature) {
        super(-1, button.xPosition + button.width - 3, button.yPosition + button.height - 3, null);
        this.height = 6;
        this.width = 6;
        this.xPosition = button.xPosition + button.width - 3;
        this.yPosition = button.yPosition + button.height - 3;
        this.feature = feature;


    }

    @Override
    public void drawButton(Minecraft mc, int mouseX, int mouseY) {
        drawRect(xPosition, yPosition, xPosition + width, yPosition + height, defaultButtonColor);
    }

    @Override
    public void playPressSound(SoundHandler soundHandler) {}
}
