package cinqdt1.Mod.gui.elements;

import cinqdt1.Mod.cinqdt1Mod;
import cinqdt1.Mod.core.Color;
import cinqdt1.Mod.utils.RenderUtils;
import cinqdt1.Mod.utils.Utils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.ResourceLocation;

import javax.annotation.Nonnull;
import java.util.List;

public class DropDown extends Gui{
    private final ResourceLocation dropDownIcon = new ResourceLocation("esm", "icons/downArrow.png");
    private int x;
    private int y;
    private int width;
    private int height;
    private int horizontalMargin;
    private int verticalMargin;
    private Color backgroundColor;
    private List<String> items;
    private boolean hovered;
    private int selectedItem;
    public DropDown(int x, int y, int horizontalMargin, int verticalMargin, Color backgroundColor, @Nonnull List<String> items){
        this.x = x;
        this.y = y;
        this.items = items;
        this.backgroundColor = backgroundColor;
        this.horizontalMargin = horizontalMargin;
        this.verticalMargin = verticalMargin;
        this.selectedItem = 0;
        this.width = (horizontalMargin * 3)+ Utils.getStringWidth(items.get(selectedItem)) + 16;
        this.height = verticalMargin * 2 + Minecraft.getMinecraft().fontRendererObj.FONT_HEIGHT;
    }

    public void drawDropDownButton(Minecraft mc, int mouseX, int mouseY){
        this.hovered = mouseX >= this.x && mouseY >= this.y && mouseX < this.x + this.width && mouseY < this.y + this.height;
        String buttonText = items.get(selectedItem);
        Gui.drawRect(this.x, this.y, this.x + this.width, this.y + this.height, this.hovered ? backgroundColor.getLightenColor((byte) 36).getColorInteger() : backgroundColor.getColorInteger());
        RenderUtils.renderText(Minecraft.getMinecraft(), buttonText, this.x + this.horizontalMargin, this.y + verticalMargin, 1, false);


        mc.getTextureManager().bindTexture(dropDownIcon);
        //GlStateManager.enableBlend();
        GlStateManager.color(1f, 0f, 0f);
        Gui.drawModalRectWithCustomSizedTexture(this.x + this.width - this.horizontalMargin - 16, this.y + (height - 16) / 2, 0, 0, 16, 16, 16, 16);

    }

    public void drawDropDownMenu(Minecraft mc, int mouseX, int mouseY){

    }

    public int getSelectedIndex(){
        return this.selectedItem;
    }
}
