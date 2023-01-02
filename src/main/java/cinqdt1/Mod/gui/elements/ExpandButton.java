package cinqdt1.Mod.gui.elements;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.ResourceLocation;

public class ExpandButton extends GuiButton {
    protected int horizontalMargin;
    protected int verticalMargin;
    public boolean extended = false;
    public static final ResourceLocation UP_ARROW = new ResourceLocation("esm", "icons/upArrow.png");
    public static final ResourceLocation DOWN_ARROW = new ResourceLocation("esm", "icons/downArrow.png");



    public ExpandButton(int x, int y, int horizontalMargin, int verticalMargin){
        super(0, x, y, null);
        this.horizontalMargin = horizontalMargin;
        this.verticalMargin = verticalMargin;
        this.width = horizontalMargin * 2 + 16;
        this.height = verticalMargin * 2 + 16;
    }

    @Override
    public void drawButton(Minecraft mc, int mouseX, int mouseY){
        if(extended){
            mc.getTextureManager().bindTexture(DOWN_ARROW);
        } else{
            mc.getTextureManager().bindTexture(UP_ARROW);
        }
        GlStateManager.color(1, 1, 1, 1);
        drawModalRectWithCustomSizedTexture(this.xPosition + this.horizontalMargin, this.yPosition + this.verticalMargin,0, 0, 16, 16, 16, 16);
    }
}
