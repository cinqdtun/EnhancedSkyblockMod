package cinqdt1.Mod.gui.elements;

import cinqdt1.Mod.core.Color;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.ResourceLocation;

public class IconButton extends GuiButton {
    protected Color backgroundColor;
    protected int horizontalMargin;
    protected int verticalMargin;
    protected boolean shadow;
    protected ResourceLocation textureIcon;

    public IconButton(int x, int y, int horizontalMargin, int verticalMargin, Color backgroundColor, ResourceLocation textureIcon, boolean shadow){
        super(0, x, y, null);
        this.backgroundColor = backgroundColor;
        this.horizontalMargin = horizontalMargin;
        this.verticalMargin = verticalMargin;
        this.shadow = shadow;
        this.textureIcon = textureIcon;
        this.width = horizontalMargin * 2 + 16;
        this.height = verticalMargin * 2 + 16;
    }

    @Override
    public void drawButton(Minecraft mc, int mouseX, int mouseY) {
        this.hovered = mouseX >= this.xPosition && mouseY >= this.yPosition && mouseX < this.xPosition + this.width && mouseY < this.yPosition + this.height;
        if(shadow) drawRect(this.xPosition + 1, this.yPosition + 1, this.xPosition + this.width + 1, this.yPosition + this.height + 1, new Color((byte) 0, (byte) 0, (byte) 0).getColorInteger());
        drawRect(this.xPosition, this.yPosition, this.xPosition + this.width, this.yPosition + this.height, this.hovered ? backgroundColor.getLightenColor((byte) 36).getColorInteger() : backgroundColor.getColorInteger());
        mc.getTextureManager().bindTexture(textureIcon);
        GlStateManager.color(1, 1, 1, 1);
        Gui.drawModalRectWithCustomSizedTexture(this.xPosition + this.horizontalMargin, this.yPosition + this.verticalMargin, 0, 0, 16,16,16,16);
    }
}
