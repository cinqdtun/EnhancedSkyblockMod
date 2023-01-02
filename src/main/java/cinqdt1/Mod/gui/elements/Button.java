package cinqdt1.Mod.gui.elements;

import cinqdt1.Mod.core.Color;
import cinqdt1.Mod.utils.RenderUtils;
import cinqdt1.Mod.utils.Utils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;

public class Button extends GuiButton {
    private Color backgroundColor;
    private int horizontalMargin;
    private int verticalMargin;
    private boolean shadow;
    public Button(int x, int y, int horizontalMargin, int verticalMargin, Color backgroundColor, String text, boolean shadow){
        super(0, x, y, text);
        this.backgroundColor = backgroundColor;
        this.horizontalMargin = horizontalMargin;
        this.verticalMargin = verticalMargin;
        this.shadow = shadow;
        this.width = (horizontalMargin * 2)+ Utils.getStringWidth(this.displayString);
        this.height = verticalMargin * 2 + Minecraft.getMinecraft().fontRendererObj.FONT_HEIGHT;
    }

    @Override
    public void drawButton(Minecraft mc, int mouseX, int mouseY) {
        this.hovered = mouseX >= this.xPosition && mouseY >= this.yPosition && mouseX < this.xPosition + this.width && mouseY < this.yPosition + this.height;
        if(shadow) drawRect(this.xPosition + 1, this.yPosition + 1, this.xPosition + this.width + 1, this.yPosition + this.height + 1, new Color((byte) 0, (byte) 0, (byte) 0).getColorInteger());
        drawRect(this.xPosition, this.yPosition, this.xPosition + this.width, this.yPosition + this.height, this.hovered ? backgroundColor.getLightenColor((byte) 36).getColorInteger() : backgroundColor.getColorInteger());
        RenderUtils.renderText(Minecraft.getMinecraft(), this.displayString, this.xPosition + horizontalMargin, this.yPosition + verticalMargin, 1, shadow);
    }


}
