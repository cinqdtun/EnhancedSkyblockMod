package cinqdt1.Mod.gui.elements;

import cinqdt1.Mod.core.Color;
import cinqdt1.Mod.utils.RenderUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.ResourceLocation;

import javax.annotation.Nullable;

public class IconButton extends Button {
    protected ResourceLocation textureIcon;

    public final static ButtonStyle transparentBackgroundButton = new ButtonStyle(new Color((byte) 255, (byte) 255, (byte) 255, (byte) 0), new Color((byte) 255, (byte) 255, (byte) 255, (byte) 0), new Color((byte) 255, (byte) 255, (byte) 255), new Color((byte) 255, (byte) 255, (byte) 255));
    public final static ButtonStyle transparentBackgroundRedIconButton = new ButtonStyle(new Color((byte) 255, (byte) 255, (byte) 255, (byte) 0), new Color((byte) 255, (byte) 255, (byte) 255, (byte) 0), new Color((byte) 200, (byte) 0, (byte) 0), new Color((byte) 164, (byte) 0, (byte) 0));

    public IconButton(int x, int y, int horizontalMargin, int verticalMargin, ButtonStyle style, ResourceLocation textureIcon, boolean shadow, @Nullable String dataBinding) {
        super(x, y, horizontalMargin, verticalMargin, style, null, shadow, dataBinding);
        this.textureIcon = textureIcon;
        this.width = horizontalMargin * 2 + 16;
        this.height = verticalMargin * 2 + 16;
    }

    @Override
    public void drawButton(Minecraft mc, int mouseX, int mouseY, boolean shouldRenderEffects) {
        if(shouldRenderEffects)  this.hovered = mouseX >= this.xPosition && mouseY >= this.yPosition && mouseX < this.xPosition + this.width && mouseY < this.yPosition + this.height;
        if(this.shadow) drawRect(this.xPosition + 1, this.yPosition + 1, this.xPosition + this.width + 1, this.yPosition + this.height + 1, new Color((byte) 0, (byte) 0, (byte) 0).getColorInteger());
        drawRect(this.xPosition, this.yPosition, this.xPosition + this.width, this.yPosition + this.height, this.hovered ? this.buttonStyle.hoveredBackgroundColor.getColorInteger() : this.buttonStyle.backgroundColor.getColorInteger());
        mc.getTextureManager().bindTexture(textureIcon);
        GlStateManager.pushAttrib();
        RenderUtils.applyColor(this.hovered ? this.buttonStyle.hoveredContentColor : this.buttonStyle.contentColor);
        Gui.drawModalRectWithCustomSizedTexture(this.xPosition + this.horizontalMargin, this.yPosition + this.verticalMargin, 0, 0, 16,16,16,16);
        GlStateManager.popAttrib();
    }
}
