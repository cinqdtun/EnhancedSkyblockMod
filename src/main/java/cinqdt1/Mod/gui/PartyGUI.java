package cinqdt1.Mod.gui;

import cinqdt1.Mod.core.Color;
import cinqdt1.Mod.gui.elements.*;
import cinqdt1.Mod.utils.ItemUtils;
import cinqdt1.Mod.utils.RenderUtils;
import cinqdt1.Mod.utils.Utils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.*;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class PartyGUI extends GuiScreen {
    protected ElementsList elementsList;
    protected DropDown dropDown;
    public static final ResourceLocation REFRESH_BUTTON = new ResourceLocation("esm", "icons/RefreshIcon.png");
    public static final Color buttonColor = new Color((byte) 60, (byte) 200, (byte) 40);
    @Override
    public boolean doesGuiPauseGame() {
        return false;
    }

    @Override
    public void initGui() {
        super.initGui();
        ScaledResolution sr = new ScaledResolution(Minecraft.getMinecraft());
        double marginHorizontal = (sr.getScaledWidth_double() - RenderUtils.getScaledHeightRatio(500)) / 2;
        double marginVertical = RenderUtils.getScaledHeightRatio(35);
        Button btn = new Button((int) (marginHorizontal + 10), (int) (sr.getScaledHeight() - marginVertical - 25), 5, 5, buttonColor, "Create party", false);
        IconButton refreshBtn = new IconButton((int) (sr.getScaledWidth() - marginHorizontal - 24), (int) (sr.getScaledHeight() - marginVertical - 25),1,1, buttonColor, REFRESH_BUTTON, false);
        this.buttonList.add(btn);
        this.buttonList.add(refreshBtn);
        List<String> elements = new ArrayList<>();
        elements.add("Text");
        this.dropDown = new DropDown((int) (sr.getScaledWidth() - marginHorizontal - 60), (int) (sr.getScaledHeight() - marginVertical - 25), 5, 5, new Color((byte) 60, (byte) 200, (byte) 40), elements);
        this.elementsList = new ElementsList((int) (marginHorizontal + 10), (int) (marginVertical + 10), (int) (sr.getScaledWidth() - 2 * marginHorizontal - 31), (int) (sr.getScaledHeight() - 2 * marginVertical - 40));
        this.elementsList.init();
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        ScaledResolution sr = new ScaledResolution(Minecraft.getMinecraft());
        double marginHorizontal = (sr.getScaledWidth_double() - RenderUtils.getScaledHeightRatio(500)) / 2;
        double marginVertical = RenderUtils.getScaledHeightRatio(35);

        //Drawing the background
        drawRect(0, 0, sr.getScaledWidth(), sr.getScaledHeight(), Utils.getIntColorFromRGBAColor(48,48,48,255));
        //drawRect((int) marginHorizontal, (int) marginVertical, (int) (sr.getScaledWidth() - marginHorizontal), (int) (sr.getScaledHeight() - marginVertical), Utils.getIntColorFromRGBAColor(48,48,48,255));

        elementsList.draw(Minecraft.getMinecraft(), mouseX, mouseY);
        //dropDown.drawDropDownButton(Minecraft.getMinecraft(), mouseX, mouseY);

        //Drawing for hiding the overflow display of the container
        GlStateManager.disableDepth();
        drawRect((int) marginHorizontal, 0, (int) (sr.getScaledWidth() - marginHorizontal), (int) (marginVertical + 10), Utils.getIntColorFromRGBAColor(48,48,48,255));
        drawRect((int) marginHorizontal, (int) (sr.getScaledHeight() - marginVertical - 30), (int) (sr.getScaledWidth() - marginHorizontal), sr.getScaledHeight(), Utils.getIntColorFromRGBAColor(48,48,48,255));

        //Drawing some elements related to GUI style
        RenderUtils.drawRect((int) marginHorizontal, (int) marginVertical, (int) (sr.getScaledWidth() - 2 * marginHorizontal), (int) (sr.getScaledHeight() - 2 * marginVertical), 1, new Color((byte) 0, (byte) 0, (byte) 0));

        mouseMoved(mouseX, mouseY);
        super.drawScreen(mouseX, mouseY, partialTicks);
        GlStateManager.enableDepth();
    }

    private void mouseMoved(int mouseX, int mouseY) {
        elementsList.mouseMoved(mouseX, mouseY);
    }

    @Override
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
        elementsList.mouseClicked(mouseX, mouseY, mouseButton);
        super.mouseClicked(mouseX, mouseY, mouseButton);
    }

    @Override
    public void actionPerformed(GuiButton button) {

    }

    @Override
    public void mouseReleased(int mouseX, int mouseY, int state) {
        elementsList.mouseReleased(mouseX, mouseY, state);
        super.mouseReleased(mouseX, mouseY, state);
    }

    class ElementsList extends GuiContainer{
        public List<Button> buttons = new ArrayList<>();
        public List<ExpandButton> expandButtons = new ArrayList<>();
        public ResourceLocation WEIGHT_ICON = new ResourceLocation("esm", "icons/training_weight.png");
        public int items;
        private int enabledExpandButtons;
        private int buttonWidth;
        private int centeredButtonHeight;

        private int centeredTextHeight;

        public ElementsList(int x, int y, int width, int height) {
            super(x, y, width, height, new Color((byte) 65, (byte) 65, (byte) 65),80 * 30 + 3);
            this.items = 80;
            this.buttonWidth = Utils.getStringWidth("Join") + 2 * 5;
            this.centeredButtonHeight =  (27 - mc.fontRendererObj.FONT_HEIGHT - 2 * 5) / 2;
            this.centeredTextHeight = (27 - mc.fontRendererObj.FONT_HEIGHT) / 2;
        }

        public void init(){
            this.expandButtons.clear();
            this.buttons.clear();
            this.enabledExpandButtons = 0;

            this.buttonWidth = Utils.getStringWidth("Join") + 2 * 5;
            this.centeredButtonHeight =  (27 - mc.fontRendererObj.FONT_HEIGHT - 2 * 5) / 2;
            this.centeredTextHeight = (27 - mc.fontRendererObj.FONT_HEIGHT) / 2;

            float barValue = this.bar.getStartPos();
            int yStart = (int) (this.contentHeight * barValue - this.height * barValue);

            for(int i = 0; i < items; i++){
                this.expandButtons.add(new ExpandButton(this.x + width - 24, this.y - yStart + i * 30 + 8, 0, 0));
                this.buttons.add(new Button( this.x + width - buttonWidth - 29, this.y - yStart + i * 30 + centeredButtonHeight + 3, 5,5, buttonColor, "Join", false));
            }
        }

        public void update(){
            this.buttonWidth = Utils.getStringWidth("Join") + 2 * 5;
            this.centeredButtonHeight =  (27 - mc.fontRendererObj.FONT_HEIGHT - 2 * 5) / 2;
            this.centeredTextHeight = (27 - mc.fontRendererObj.FONT_HEIGHT) / 2;

            this.contentHeight = 30 * items + 3 + 60 * this.enabledExpandButtons;
            this.bar.updateContentHeight(this.contentHeight);

        }
        @Override
        public void draw(Minecraft mc, int mouseX, int mouseY){
            super.draw(mc, mouseX, mouseY);

            int yStart = (int) this.bar.getStartPos();
            int developedItemsDrawn = 0;

            for(int i = 0; i < items; i++){
                this.expandButtons.get(i).yPosition = this.y - yStart + i * 30 + 8 + 60 * developedItemsDrawn;
                this.buttons.get(i).yPosition = this.y - yStart + i * 30 + centeredButtonHeight + 3 + 60 * developedItemsDrawn;

                Gui.drawRect(this.x + 3, this.y - yStart + i * 30 + 3 + 60 * developedItemsDrawn, this.x + this.width - 3, this.y - yStart + i * 30 + 30 + 60 * developedItemsDrawn, new Color((byte) 89, (byte) 89, (byte) 89).getColorInteger());
                RenderUtils.renderItem(ItemUtils.getQuestionMark(), this.x + 8,this.y - yStart + i * 30 + 8 + 60 * developedItemsDrawn,1);
                RenderUtils.renderText(mc, "Player's Party",this.x + 27,this.y - yStart + i * 30  + centeredTextHeight + 3 + 60 * developedItemsDrawn,1,false);

                this.buttons.get(i).drawButton(mc, mouseX, mouseY);
                this.expandButtons.get(i).drawButton(mc, mouseX, mouseY);

                //RenderUtils.renderText(mc, String.valueOf(i), this.x + 3, this.y - yStart + i * 30 + 3 + 60 * developedItemsDrawn,1,false);
                if(this.expandButtons.get(i).extended){
                    Gui.drawRect(this.x + 3, this.y - yStart + i * 30 + 30 + 60 * developedItemsDrawn, this.x + this.width - 3, this.y - yStart + i * 30 + 90 + 60 * developedItemsDrawn, new Color((byte) 110, (byte) 110, (byte) 110).getColorInteger());
                    RenderUtils.renderItem(ItemUtils.getQuestionMark(), this.x + 8,this.y - yStart + i * 30 + 30 + 60 * developedItemsDrawn + 7,1);
                    RenderUtils.renderText(mc, "Player87969ezwin",this.x + 27,this.y - yStart + i * 30 + 30 + 60 * developedItemsDrawn + 7 + (16 - mc.fontRendererObj.FONT_HEIGHT) / 2,1,false);
                    //mc.getTextureManager().bindTexture(WEIGHT_ICON);
                    RenderUtils.renderItem(ItemUtils.getWeight(), (int)(this.x + 27 + Utils.getStringWidth("Player87969ezwin") + 10), (int) ((this.y - yStart + i * 30 + 30 + 60 * developedItemsDrawn + 7 + (16 - mc.fontRendererObj.FONT_HEIGHT) / 2) - 4),1);
                    //drawModalRectWithCustomSizedTexture((int) ((this.x + 27 + Utils.getStringWidth("Player87969ezwin")) + 10), (int) ((this.y - yStart + i * 30 + 30 + 60 * developedItemsDrawn + 7 + (16 - mc.fontRendererObj.FONT_HEIGHT) / 2) - 4),0, 0, 16, 16, 16, 16);
                    RenderUtils.renderText(mc, "Weight: 16540", this.x + 27 + Utils.getStringWidth("Player87969ezwin") + 5 + 16 + 5,this.y - yStart + i * 30 + 30 + 60 * developedItemsDrawn + 7 + (16 - mc.fontRendererObj.FONT_HEIGHT) / 2,1,false);
                    //RenderUtils.renderText(mc, "Weight : 16540", this.x + 27 + Utils.getStringWidth("Player87969ezwin") + 10 + Utils.getStringWidth("Skill Average : 49.8") + 10,this.y - yStart + i * 30 + 30 + 60 * developedItemsDrawn + 7 + (16 - mc.fontRendererObj.FONT_HEIGHT) / 2,1,false);
                    //mc.getTextureManager().bindTexture(CROWN_ICON);
                    //double scale = 0.5d;
                    //GL11.glScaled(scale,scale,scale);
                    //GlStateManager.color(1, 1, 1, 1);
                    //drawModalRectWithCustomSizedTexture((int) ((this.x + 27 + Utils.getStringWidth("Player87969ezwin"))), (int) ((this.y - yStart + i * 30 + 30 + 60 * developedItemsDrawn + 7 + mc.fontRendererObj.FONT_HEIGHT - 16)),0, 0, 16, 16, 16, 16);
                    //double scaleReset = Math.pow(scale, -1);
                    //GL11.glScaled(scaleReset,scaleReset,scaleReset);
                    RenderUtils.renderItem(ItemUtils.getQuestionMark(), this.x + 8,this.y - yStart + i * 30 + 30 + 60 * developedItemsDrawn + 7 + 30,1);
                    RenderUtils.renderText(mc, "Player87969ezwin",this.x + 27,this.y - yStart + i * 30 + 30 + 60 * developedItemsDrawn + 7 + 30 + (16 - mc.fontRendererObj.FONT_HEIGHT) / 2,1,false);
                    //Row 2
                    //RenderUtils.renderItem(ItemUtils.getQuestionMark(), this.x + 8,this.y - yStart + i * 30 + 30 + 60 * developedItemsDrawn + 7,1);
                    ///RenderUtils.renderText(mc, "Player87969ezwin",this.x + 27,this.y - yStart + i * 30 + 30 + 60 * developedItemsDrawn + 7 + (16 - mc.fontRendererObj.FONT_HEIGHT) / 2,1,false);
                    //RenderUtils.renderItem(ItemUtils.getQuestionMark(), this.x + 8,this.y - yStart + i * 30 + 30 + 60 * developedItemsDrawn + 7 + 30,1);
                   // RenderUtils.renderText(mc, "Player87969ezwin",this.x + 27,this.y - yStart + i * 30 + 30 + 60 * developedItemsDrawn + 7 + 30 + (16 - mc.fontRendererObj.FONT_HEIGHT) / 2,1,false);
                    developedItemsDrawn++;
                }
            }
        }

        @Override
        public void mouseClicked(int mouseX, int mouseY, int mouseButton){
            if (mouseButton == 0) {
                if(mouseX > this.x && mouseX < this.x + this.width && mouseY > this.y && mouseY < this.y + this.height) {
                    for (int i = 0; i < items; i++) {
                        Button btn = buttons.get(i);
                        if (btn.yPosition + btn.height > this.y && btn.yPosition < this.y + this.height) {
                            if (btn.mousePressed(mc, mouseX, mouseY)) {
                                //Utils.sendModMessage("Button pressed !");
                            }
                        }
                        ExpandButton expandButton = expandButtons.get(i);
                        if (expandButton.yPosition + expandButton.height > this.y && expandButton.yPosition < this.y + this.height) {
                            if(expandButton.mousePressed(mc, mouseX, mouseY)) {
                                expandButton.extended = !expandButton.extended;
                                if(expandButton.extended){
                                    enabledExpandButtons++;
                                }else{
                                    enabledExpandButtons--;
                                }
                                //System.out.println("Enabled expand buttons : " + enabledExpandButtons);
                                this.update();
                            }
                        }
                    }
                }
            }
            super.mouseClicked(mouseX, mouseY, mouseButton);
        }

        //@Override
        //public void mouseReleased
    }
}
