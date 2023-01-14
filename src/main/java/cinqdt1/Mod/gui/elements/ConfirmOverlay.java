package cinqdt1.Mod.gui.elements;

import cinqdt1.Mod.core.Color;
import cinqdt1.Mod.gui.BaseGuiScreen;
import cinqdt1.Mod.utils.RenderUtils;
import cinqdt1.Mod.utils.Utils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;

public class ConfirmOverlay {
    public static final int NO_ACTION = 0x00;
    public static final int BLOCK_USER = 0x01;
    private final BaseGuiScreen parent;
    private final Button yesButton;
    private final Button noButton;
    private boolean isAsking;
    private int action;
    private byte[] params;
    public ConfirmOverlay(BaseGuiScreen parent){
        this.parent = parent;
        this.action = NO_ACTION;
        yesButton = new Button(0, 0, (50 - Utils.getStringWidth("Yes")) / 2, 4, Button.defaultButton, "Yes", false, null);
        noButton = new Button( 0, 0, (50 - Utils.getStringWidth("No")) / 2, 4, Button.redButton, "No", false, null);
    }

    public void ask(int action, byte[] params){
        if(!this.isAsking){
            this.isAsking = true;
            this.action = action;
            this.params = params;
        }
    }
    public void draw(Minecraft mc, int mouseX, int mouseY) {
        ScaledResolution sr = new ScaledResolution(mc);
        int horizontalMargin = (sr.getScaledWidth() - 130)/ 2;
        int verticalMargin = (sr.getScaledHeight() - 50) / 2;
        yesButton.xPosition = horizontalMargin + 5;
        yesButton.yPosition = sr.getScaledHeight() - verticalMargin - 5 - 8 - Minecraft.getMinecraft().fontRendererObj.FONT_HEIGHT;
        noButton.xPosition = sr.getScaledWidth() - horizontalMargin - 5 - 50;
        noButton.yPosition = sr.getScaledHeight() - verticalMargin - 5 - 8 - Minecraft.getMinecraft().fontRendererObj.FONT_HEIGHT;
        Gui.drawRect(0, 0, sr.getScaledWidth(), sr.getScaledHeight(), -1072689136);
        Gui.drawRect(horizontalMargin, verticalMargin, sr.getScaledWidth() - horizontalMargin, sr.getScaledHeight() - verticalMargin, Utils.getIntColorFromRGBAColor(48,48,48,255));
        RenderUtils.renderText(mc, "Are you sure?", horizontalMargin + 5 + (float)(130 - Utils.getStringWidth("Are you sure?")) / 2, verticalMargin + 5, 1, false);
        RenderUtils.drawRect(horizontalMargin - 1, verticalMargin - 1, 130, 50, 1, new Color((byte) 0, (byte) 0, (byte) 0));
        yesButton.drawButton(mc, mouseX, mouseY, true);
        noButton.drawButton(mc, mouseX, mouseY, true);
    }

    public boolean getAskingState(){
        return this.isAsking;
    }

    public void mouseClicked(int mouseX, int mouseY, int mouseButton) {
        if(mouseButton == 0) {
            if (yesButton.mousePressed(Minecraft.getMinecraft(), mouseX, mouseY)) {
                this.isAsking = false;
                this.parent.confirmResult(true, action, params);
            } else if (noButton.mousePressed(Minecraft.getMinecraft(), mouseX, mouseY)) {
                this.isAsking = false;
                this.parent.confirmResult(false, action, params);
            }
        }
    }
}
