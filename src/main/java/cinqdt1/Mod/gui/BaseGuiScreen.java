package cinqdt1.Mod.gui;

import cinqdt1.Mod.gui.elements.Button;
import cinqdt1.Mod.gui.elements.ConfirmOverlay;
import com.google.common.collect.Lists;
import net.minecraft.client.gui.GuiScreen;

import java.io.IOException;
import java.util.List;

public class BaseGuiScreen extends GuiScreen implements GuiCustomYesNoCallback {
    protected ConfirmOverlay confirmOverlay = new ConfirmOverlay(this);
    protected List<Button> customButtonList = Lists.newArrayList();

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        for (Button button : this.customButtonList) {
            button.drawButton(this.mc, mouseX, mouseY, !confirmOverlay.getAskingState());
        }
        if(confirmOverlay.getAskingState()) confirmOverlay.draw(mc, mouseX, mouseY);
    }

    @Override
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
        if(confirmOverlay.getAskingState()){
            confirmOverlay.mouseClicked(mouseX, mouseY, mouseButton);
            return;
        }
        super.mouseClicked(mouseX, mouseY, mouseButton);
    }

    @Override
    public void mouseReleased(int mouseX, int mouseY, int state) {
        if (confirmOverlay.getAskingState()) return;
        super.mouseReleased(mouseX, mouseY, state);
    }

    @Override
    public void confirmResult(boolean result, int id, byte[] params) {
        if(id == ConfirmOverlay.NO_ACTION) return;
        //Process the result
    }
}
