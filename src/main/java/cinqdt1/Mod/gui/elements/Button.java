package cinqdt1.Mod.gui.elements;

import cinqdt1.Mod.core.Color;
import cinqdt1.Mod.utils.RenderUtils;
import cinqdt1.Mod.utils.Utils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.GlStateManager;

import javax.annotation.Nullable;

public class Button extends Gui {
    public final static ButtonStyle defaultButton = new ButtonStyle(new Color((byte) 60, (byte) 200, (byte) 40), new Color((byte) 24, (byte) 164, (byte) 24), new Color((byte) 255, (byte) 255, (byte) 255), new Color((byte) 255, (byte) 255, (byte) 255));
    public final static ButtonStyle redButton = new ButtonStyle(new Color((byte) 200, (byte) 0, (byte) 0), new Color((byte) 164, (byte) 0, (byte) 0), new Color((byte) 255, (byte) 255, (byte) 255), new Color((byte) 255, (byte) 255, (byte) 255));
    public final String dataBinding;
    public boolean hovered;
    public int xPosition;
    public int yPosition;
    public int width;
    public int height;
    protected ButtonStyle buttonStyle;
    protected String displayString;
    protected int horizontalMargin;
    protected int verticalMargin;
    protected boolean shadow;

    public Button(int x, int y, int horizontalMargin, int verticalMargin, ButtonStyle style, String text, boolean shadow, @Nullable String dataBinding){
        this.xPosition = x;
        this.yPosition = y;
        this.displayString = text;
        this.buttonStyle = style;
        this.horizontalMargin = horizontalMargin;
        this.verticalMargin = verticalMargin;
        this.shadow = shadow;
        this.dataBinding = dataBinding;
        this.width = (horizontalMargin * 2)+ Utils.getStringWidth(this.displayString);
        this.height = verticalMargin * 2 + Minecraft.getMinecraft().fontRendererObj.FONT_HEIGHT;
    }

    public void drawButton(Minecraft mc, int mouseX, int mouseY, boolean shouldRenderEffects) {
        if(shouldRenderEffects) this.hovered = mouseX >= this.xPosition && mouseY >= this.yPosition && mouseX < this.xPosition + this.width && mouseY < this.yPosition + this.height;
        if(shadow) drawRect(this.xPosition + 1, this.yPosition + 1, this.xPosition + this.width + 1, this.yPosition + this.height + 1, new Color((byte) 0, (byte) 0, (byte) 0).getColorInteger());
        drawRect(this.xPosition, this.yPosition, this.xPosition + this.width, this.yPosition + this.height, this.hovered ? this.buttonStyle.hoveredBackgroundColor.getColorInteger() : this.buttonStyle.backgroundColor.getColorInteger());
        GlStateManager.pushAttrib();
        RenderUtils.applyColor(this.hovered ? this.buttonStyle.hoveredContentColor : this.buttonStyle.contentColor);
        RenderUtils.renderText(Minecraft.getMinecraft(), this.displayString, this.xPosition + this.horizontalMargin, this.yPosition + verticalMargin, 1, shadow);
        GlStateManager.popAttrib();
    }

    public boolean mousePressed(Minecraft mc, int mouseX, int mouseY) {
        return mouseX >= this.xPosition && mouseY >= this.yPosition && mouseX < this.xPosition + this.width && mouseY < this.yPosition + this.height;
    }

    public int getWidth(){
        return this.width;
    }

    public int getHeight(){
        return this.height;
    }

    public static class ButtonStyle{
        public final Color backgroundColor;
        public final Color hoveredBackgroundColor;
        public final Color contentColor;
        public final Color hoveredContentColor;
        public ButtonStyle(Color backgroundColor,  Color hoveredBackgroundColor, Color contentColor, Color hoveredContentColor){
            this.backgroundColor = backgroundColor;
            this.hoveredBackgroundColor = hoveredBackgroundColor;
            this.contentColor = contentColor;
            this.hoveredContentColor = hoveredContentColor;
        }
    }
}
