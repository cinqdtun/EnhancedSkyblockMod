package cinqdt1.Mod.gui.elements;

import cinqdt1.Mod.core.Color;
import net.minecraft.client.gui.Gui;

public class ScrollBar extends Gui {
    private final int x;
    private final int y;
    private final int width;
    private final int height;
    private boolean hovered;
    private boolean clicked;
    public final double minValue;
    public double maxValue;
    private double value;
    private int clickOffset;
    private double scrollSize;
    private int contentHeight;
    private int displayHeight;
    public ScrollBar(int x, int y, int width, int height, int contentHeight, int displayHeight){
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.scrollSize = (float)height / ((float)contentHeight / height);
        this.minValue = 0;
        this.maxValue = height - this.scrollSize;
        this.value = this.minValue;
        this.contentHeight = contentHeight;
        this.displayHeight = displayHeight;

    }
    public void updateContentHeight(int newContentHeight){
        double oldStartPos = this.getStartPos();
        this.scrollSize = (float)this.height / ((float)newContentHeight / this.height);
        this.maxValue = this.height - this.scrollSize;
        this.contentHeight = newContentHeight;
        double offset = (oldStartPos == 0) ? 1 : this.getStartPos() / oldStartPos;
        this.value = Math.min(this.value / offset, this.maxValue);
    }

    public void drawScrollBar(int mouseX, int mouseY){
        this.hovered = mouseX >= this.x && mouseY >= this.y + this.value && mouseX < this.x + this.width && mouseY < this.y + this.value + this.scrollSize;
        drawRect(this.x, this.y, this.x + this.width, this.y + this.height, new Color((byte) 0, (byte) 0, (byte) 0).getColorInteger());
        drawRect(this.x, (int) (this.y + this.value), this.x + this.width, (int) (this.y + this.value + this.scrollSize), new Color((byte) 119, (byte) 119, (byte) 119).getColorInteger());
    }

    public boolean getHoverState(int mouseX, int mouseY){
        return mouseX >= this.x && mouseY >= this.y + this.value && mouseX < this.x + this.width && mouseY < this.y + this.value + this.scrollSize;
    }

    public float getStartPos(){
        float scrollRatio = this.maxValue > 0 ? (float) (this.value / this.maxValue) : 0;
        return (this.contentHeight - this.displayHeight) * scrollRatio;
    }

    public void updateClickedState(int mouseX, int mouseY, int mouseButton){
        this.clicked = getHoverState(mouseX, mouseY) && mouseButton == 0;
        if(this.clicked) this.clickOffset = (int) (mouseY - this.y - this.value);
    }

    public void updateValue(int mouseX, int mouseY){
        if(this.clicked) {
            this.value = Math.max(Math.min(mouseY - this.y - this.clickOffset, this.maxValue), this.minValue);
        }

    }
    public void mouseClicked(int mouseX, int mouseY, int mouseButton){
        updateClickedState(mouseX, mouseY, mouseButton);
    }
    public void mouseMoved(int mouseX, int mouseY){
        updateValue(mouseX, mouseY);
    }
    public void mouseReleased(int mouseX, int mouseY, int state){
        updateClickedState(mouseX, mouseY, -1);
        updateValue(mouseX, mouseY);
    }
}
