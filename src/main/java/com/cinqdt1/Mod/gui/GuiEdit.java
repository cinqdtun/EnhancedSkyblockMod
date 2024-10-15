package com.cinqdt1.Mod.gui;

import com.cinqdt1.Mod.cinqdt1Mod;
import com.cinqdt1.Mod.gui.buttons.GuiFeatureButton;
import com.cinqdt1.Mod.gui.buttons.GuiFeatureButtonResizable;
import com.cinqdt1.Mod.utils.RenderUtils;
import gg.essential.api.EssentialAPI;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;

import java.util.ArrayList;
import java.util.List;

public class GuiEdit extends GuiScreen {
    private int buttonPerformed = -1;
    private int lastMouseX = -1;
    private int lastMouseY = -1;
    private GuiEdit.Action buttonAction = GuiEdit.Action.NONE;
    private List<GuiFeature> features = new ArrayList<>();
    private List<GuiFeatureButton> guiButtons = new ArrayList<>();
    private List<GuiFeatureButtonResizable> resizeGuiButtons = new ArrayList<>();

    public void registerFeature(GuiFeature feature){
        features.add(feature);
    }

    public int getId()
    {
        return features.size();
    }

    @Override
    public void initGui() {
        super.initGui();
        guiButtons.clear();
        resizeGuiButtons.clear();
        for (GuiFeature feature : features){
            try {
                GuiFeatureButton button = new GuiFeatureButton(cinqdt1Mod.newModConfig.getInteger(feature.getCoordinatesCategory(), "position", "x"),
                        cinqdt1Mod.newModConfig.getInteger(feature.getCoordinatesCategory(), "position", "y"),
                        cinqdt1Mod.newModConfig.getFloat(feature.getCoordinatesCategory(), "position", "scale"),
                        feature.getDisplayItem(), feature.getxTextOffset(), feature.getyTextOffset(), feature.getText(), feature.getLinesOffset(), feature.getId());
                GuiFeatureButtonResizable resizableButton = new GuiFeatureButtonResizable(button, feature.getId());
                guiButtons.add(button);
                resizeGuiButtons.add(resizableButton);
                this.buttonList.add(button);
                this.buttonList.add(resizableButton);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public boolean doesGuiPauseGame() {
        return false;
    }

    private void mouseMoved(int mouseX, int mouseY) {
        try {
            double xMoved = RenderUtils.getUnscaledRatio((double) (mouseX - lastMouseX));
            double yMoved = RenderUtils.getUnscaledRatio((double) (mouseY - lastMouseY));
            int i;
            for(i = 0; i < guiButtons.size(); i++){
                GuiFeatureButton button = guiButtons.get(i);
                if(buttonPerformed != i) continue;
                if (buttonAction == GuiEdit.Action.RESIZE) {
                    float scale = button.getNewScaleWithX(xMoved);
                    if (scale > 0.5f && scale < 6.0f)
                        cinqdt1Mod.newModConfig.setFloat(features.get(i).getCoordinatesCategory(), "position", "scale", scale);
                    cinqdt1Mod.newModConfig.saveConfig();
                    this.buttonList.clear();
                    initGui();
                    break;
                }else if(buttonAction == GuiEdit.Action.MOVE) {
                    double xValue = cinqdt1Mod.newModConfig.getDouble(features.get(i).getCoordinatesCategory(), "position", "x");
                    double yValue = cinqdt1Mod.newModConfig.getDouble(features.get(i).getCoordinatesCategory(), "position", "y");
                    cinqdt1Mod.newModConfig.setDouble(features.get(i).getCoordinatesCategory(), "position", "x", xValue + xMoved);
                    cinqdt1Mod.newModConfig.setDouble(features.get(i).getCoordinatesCategory(), "position", "y", yValue + yMoved);
                    cinqdt1Mod.newModConfig.saveConfig();
                    this.buttonList.clear();
                    initGui();
                    break;
                }
            }
            lastMouseX = mouseX;
            lastMouseY = mouseY;
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        this.drawDefaultBackground();
        mouseMoved(mouseX, mouseY);
        super.drawScreen(mouseX, mouseY, partialTicks);
    }

    @Override
    public void actionPerformed(GuiButton button) {
        if (button instanceof GuiFeatureButton) {
            buttonAction = GuiEdit.Action.MOVE;
            buttonPerformed = ((GuiFeatureButton) button).id;
        } else if (button instanceof GuiFeatureButtonResizable) {
            buttonAction = GuiEdit.Action.RESIZE;
            buttonPerformed = ((GuiFeatureButtonResizable) button).id;
        }
    }

    @Override
    public void mouseReleased(int mouseX, int mouseY, int state) {
        super.mouseReleased(mouseX, mouseY, state);
        buttonAction = GuiEdit.Action.NONE;
        buttonPerformed = -1;
    }

    @Override
    public void onGuiClosed()
    {
        EssentialAPI.getGuiUtil().openScreen(cinqdt1Mod.instance.config.gui());
    }

    public enum Action{
        MOVE,
        RESIZE,
        NONE
    }
}
