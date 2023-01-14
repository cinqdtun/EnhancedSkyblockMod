package cinqdt1.Mod.gui.elements;

import cinqdt1.Mod.core.Color;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;

public abstract class GuiContainer extends Gui {
    protected int x;
    protected int y;
    protected int width;
    protected int height;
    protected int contentHeight;
    protected Color backgroundColor;
    protected ScrollBar bar;
    public GuiContainer(int x, int y, int width, int height, Color backgroundColor, int contentHeight){
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.backgroundColor = backgroundColor;
        this.contentHeight = contentHeight;
        this.bar = new ScrollBar(x + width + 10, y, 5, height, contentHeight, height);
    }

    public void draw(Minecraft mc, int mouseX, int mouseY, boolean shouldRenderEffects){
        bar.drawScrollBar(mouseX, mouseY);
        drawRect(x, y, x + width, y + height, backgroundColor.getColorInteger());
    }

    public void mouseMoved(int mouseX, int mouseY){
        bar.mouseMoved(mouseX, mouseY);
    }

    public void mouseClicked(int mouseX, int mouseY, int mouseButton){
        bar.mouseClicked(mouseX, mouseY, mouseButton);
    }

    public void mouseReleased(int mouseX, int mouseY, int state){
        bar.mouseReleased(mouseX, mouseY, state);
    }
}
