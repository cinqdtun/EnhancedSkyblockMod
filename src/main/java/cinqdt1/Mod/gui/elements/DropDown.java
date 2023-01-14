package cinqdt1.Mod.gui.elements;

import cinqdt1.Mod.core.Color;
import cinqdt1.Mod.utils.RenderUtils;
import cinqdt1.Mod.utils.Utils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.ResourceLocation;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;

public class DropDown extends Button{
    private final ResourceLocation dropDownIcon = new ResourceLocation("esm", "icons/downArrow.png");
    public static ButtonStyle defaultDropDown =  new ButtonStyle(new Color((byte) 70, (byte) 70, (byte) 70), new Color((byte) 110, (byte) 110, (byte) 110), new Color((byte) 255, (byte) 255, (byte) 255), new Color((byte) 255, (byte) 255, (byte) 255));
    private int selectedItem;
    private final List<String> items;
    private boolean isDropDownOpen;
    private boolean isDrownDownReversed;

    public DropDown(int x, int y, int horizontalMargin, int verticalMargin, ButtonStyle style, @Nonnull List<String> items, boolean shadow, @Nullable String dataBinding){
        super(x, y, horizontalMargin, verticalMargin, style, items.get(0), shadow, dataBinding);
        this.items = items;
        this.selectedItem = 0;
        this.isDropDownOpen = false;
        int longestItem = 0;
        for(String item : items){
            longestItem = Math.max(longestItem, Utils.getStringWidth(item));
        }
        this.width = 16 + 3 * horizontalMargin + longestItem;
    }

    @Override
    public void drawButton(Minecraft mc, int mouseX, int mouseY, boolean shouldRenderEffects){
        if(isDropDownOpen){
            this.isDrownDownReversed = this.yPosition + this.height * items.size() > new ScaledResolution(mc).getScaledHeight();
            int y = this.yPosition;
            for(int i = 0; i < items.size(); i++){
                if(i == this.selectedItem) continue;
                String item = items.get(i);
                if(this.isDrownDownReversed){
                    y -= this.height;
                }else{
                    y += this.height;
                }
                boolean isElementHovered = mouseX >= this.xPosition && mouseY >= y && mouseX < this.xPosition + this.width && mouseY < y + this.height;
                Gui.drawRect(this.xPosition, y, this.xPosition + this.width, y + this.height, isElementHovered && shouldRenderEffects ? this.buttonStyle.hoveredBackgroundColor.getColorInteger() : this.buttonStyle.backgroundColor.getColorInteger());
                RenderUtils.applyColor(this.hovered ? this.buttonStyle.hoveredContentColor : this.buttonStyle.contentColor);
                RenderUtils.renderText(Minecraft.getMinecraft(), item, this.xPosition + this.horizontalMargin, y + verticalMargin, 1, false);
            }
        }
        this.hovered = mouseX >= this.xPosition && mouseY >= this.yPosition && mouseX < this.xPosition + this.width && mouseY < this.yPosition + this.height;
        String buttonText = items.get(selectedItem);
        Gui.drawRect(this.xPosition, this.yPosition, this.xPosition + this.width, this.yPosition + this.height, this.hovered && shouldRenderEffects ? this.buttonStyle.hoveredBackgroundColor.getColorInteger() : this.buttonStyle.backgroundColor.getColorInteger());
        RenderUtils.applyColor(this.hovered ? this.buttonStyle.hoveredContentColor : this.buttonStyle.contentColor);
        RenderUtils.renderText(Minecraft.getMinecraft(), buttonText, this.xPosition + this.horizontalMargin, this.yPosition + verticalMargin, 1, false);
        mc.getTextureManager().bindTexture(dropDownIcon);
        GlStateManager.color(1f, 1f, 1f, 1f);
        Gui.drawModalRectWithCustomSizedTexture(this.xPosition + this.width - this.horizontalMargin - 16, this.yPosition + (height - 16) / 2, 0, 0, 16, 16, 16, 16);

    }

    public int getSelectedIndex(){
        return this.selectedItem;
    }

    public void mouseClicked(int mouseX, int mouseY, int mouseButton){
        if(mouseButton == 0){
            if(isDropDownOpen) {
                if(mouseX >= this.xPosition && mouseX < this.xPosition + this.width) {
                    if (this.isDrownDownReversed) {
                        if(mouseY >= this.yPosition - this.height * (items.size() - 1) && mouseY < this.yPosition){
                            int dropDownSelectedItem = Math.abs(mouseY - this.yPosition) / this.height;
                            System.out.println(dropDownSelectedItem);
                            this.selectedItem = this.selectedItem > dropDownSelectedItem ? dropDownSelectedItem : dropDownSelectedItem + 1;
                        }
                    }else{
                        if (mouseY >= this.yPosition + this.height && mouseY < this.yPosition + this.height * items.size()) {
                            int dropDownSelectedItem = (mouseY - this.yPosition - this.height) / this.height;
                            this.selectedItem = this.selectedItem > dropDownSelectedItem ? dropDownSelectedItem : dropDownSelectedItem + 1;
                        }
                    }
                }
                isDropDownOpen = false;
            }else if(this.mousePressed(Minecraft.getMinecraft(), mouseX, mouseY) && !isDropDownOpen){
                isDropDownOpen = true;
            }
        }
    }
}
