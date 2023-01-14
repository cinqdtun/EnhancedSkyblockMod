package cinqdt1.Mod.gui;

import cinqdt1.Mod.gui.elements.*;
import cinqdt1.Mod.utils.ColorsUtils;
import cinqdt1.Mod.utils.ItemUtils;
import cinqdt1.Mod.utils.RenderUtils;
import cinqdt1.Mod.utils.Utils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.ResourceLocation;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class PartyFinderGUI extends BaseGuiScreen {
    protected DropDown partyType;
    protected DropDown partySize;
    protected ElementsList elementsList;
    public static final ResourceLocation REFRESH_BUTTON = new ResourceLocation("esm", "icons/RefreshIcon.png");
    @Override
    public boolean doesGuiPauseGame() {
        return false;
    }
    @Override
    public void initGui() {
        super.initGui();
        this.customButtonList.clear();
        ScaledResolution sr = new ScaledResolution(Minecraft.getMinecraft());
        double marginHorizontal = (sr.getScaledWidth_double() - RenderUtils.getScaledHeightRatio(500)) / 2;
        double marginVertical = RenderUtils.getScaledHeightRatio(35);
        Button btn = new Button((int) (marginHorizontal + 10), (int) (sr.getScaledHeight() - marginVertical - 25), 5, 5, Button.defaultButton, "Create party", false, "button_create_party");
        IconButton refreshBtn = new IconButton((int) (sr.getScaledWidth() - marginHorizontal - 24), (int) (sr.getScaledHeight() - marginVertical - 25),1,1, Button.defaultButton, REFRESH_BUTTON, false, "button_refresh");
        List<String> partyVisibilityList = new ArrayList<>();
        partyVisibilityList.add("Public");
        partyVisibilityList.add("Private");
        List<String> partySizeList = new ArrayList<>();
        partySizeList.add("2 players");
        partySizeList.add("3 players");
        partySizeList.add("4 players");
        partyType = new DropDown((int) (marginHorizontal + btn.getWidth() + 15), (int) (sr.getScaledHeight() - marginVertical - 25), 5, 5, DropDown.defaultDropDown, partyVisibilityList, false, "dropdown_party_visibility");
        partySize= new DropDown((int) (marginHorizontal + btn.getWidth() + partyType.getWidth() + 20), (int) (sr.getScaledHeight() - marginVertical - 25), 5, 5, DropDown.defaultDropDown, partySizeList, false, "dropdown_party_visibility");
        this.customButtonList.add(btn);
        this.customButtonList.add(refreshBtn);
        this.customButtonList.add(partyType);
        this.customButtonList.add(partySize);
        this.elementsList = new ElementsList((int) (marginHorizontal + 10), (int) (marginVertical + 10), (int) (sr.getScaledWidth() - 2 * marginHorizontal - 31), (int) (sr.getScaledHeight() - 2 * marginVertical - 40), this);
        this.elementsList.init();
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        ScaledResolution sr = new ScaledResolution(Minecraft.getMinecraft());
        double marginHorizontal = (sr.getScaledWidth_double() - RenderUtils.getScaledHeightRatio(500)) / 2;
        double marginVertical = RenderUtils.getScaledHeightRatio(35);

        drawRect(0, 0, sr.getScaledWidth(), sr.getScaledHeight(), ColorsUtils.DEFAULT_BACKGROUND_GUI_COLOR.getColorInteger());

        elementsList.draw(Minecraft.getMinecraft(), mouseX, mouseY, !this.confirmOverlay.getAskingState());

        //Drawing for hiding the overflow display of the container
        GlStateManager.disableDepth();
        drawRect((int) marginHorizontal, 0, (int) (sr.getScaledWidth() - marginHorizontal), (int) (marginVertical + 10), ColorsUtils.DEFAULT_BACKGROUND_GUI_COLOR.getColorInteger());
        drawRect((int) marginHorizontal, (int) (sr.getScaledHeight() - marginVertical - 30), (int) (sr.getScaledWidth() - marginHorizontal), sr.getScaledHeight(), ColorsUtils.DEFAULT_BACKGROUND_GUI_COLOR.getColorInteger());

        //Drawing some elements related to GUI style
        RenderUtils.drawRect((int) marginHorizontal, (int) marginVertical, (int) (sr.getScaledWidth() - 2 * marginHorizontal), (int) (sr.getScaledHeight() - 2 * marginVertical), 1, ColorsUtils.BLACK);

        mouseMoved(mouseX, mouseY);
        super.drawScreen(mouseX, mouseY, partialTicks);
        GlStateManager.enableDepth();
    }

    private void mouseMoved(int mouseX, int mouseY) {
        if(confirmOverlay.getAskingState()) return;
        elementsList.mouseMoved(mouseX, mouseY);
    }

    @Override
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
        super.mouseClicked(mouseX, mouseY, mouseButton);
        if(!this.confirmOverlay.getAskingState()){
            elementsList.mouseClicked(mouseX, mouseY, mouseButton);
            partyType.mouseClicked(mouseX, mouseY, mouseButton);
            partySize.mouseClicked(mouseX, mouseY, mouseButton);
        }
    }

    @Override
    public void mouseReleased(int mouseX, int mouseY, int state) {
        super.mouseReleased(mouseX, mouseY, state);
        if(!this.confirmOverlay.getAskingState()) elementsList.mouseReleased(mouseX, mouseY, state);
    }

    @Override
    public void confirmResult(boolean result, int id, byte[] params) {
        if(result){
            if(id == ConfirmOverlay.BLOCK_USER){
                Utils.sendModMessage("You have blocked successfully the user \"" + new String(params, StandardCharsets.UTF_8) + "\"");
            }
        }
    }

    class ElementsList extends GuiContainer{
        public List<Button> buttons = new ArrayList<>();
        public List<ExpandButton> expandButtons = new ArrayList<>();
        private final PartyFinderGUI parentGui;
        public List<Button> othersButtons = new ArrayList<>();
        public int items;
        private int enabledExpandButtons;
        private int buttonWidth;
        private int centeredButtonHeight;

        private int centeredTextHeight;
        public ResourceLocation IGNORE_ICON = new ResourceLocation("esm", "icons/ignore_icon.png");

        public ElementsList(int x, int y, int width, int height, PartyFinderGUI gui){
            super(x, y, width, height, ColorsUtils.DEFAULT_LIST_BACKGROUND_COLOR,80 * 30 + 3);
            this.parentGui = gui;
            this.items = 80;
            this.buttonWidth = Utils.getStringWidth("Join") + 10;
            this.centeredButtonHeight =  (17 - mc.fontRendererObj.FONT_HEIGHT) / 2;
            this.centeredTextHeight = (27 - mc.fontRendererObj.FONT_HEIGHT) / 2;
        }

        public void init(){
            this.expandButtons.clear();
            this.buttons.clear();
            this.enabledExpandButtons = 0;

            this.buttonWidth = Utils.getStringWidth("Join") + 10;
            this.centeredButtonHeight =  (17 - mc.fontRendererObj.FONT_HEIGHT) / 2;
            this.centeredTextHeight = (27 - mc.fontRendererObj.FONT_HEIGHT) / 2;

            float barValue = this.bar.getStartPos();
            int yStart = (int) (this.contentHeight * barValue - this.height * barValue);

            for(int i = 0; i < items; i++){
                this.expandButtons.add(new ExpandButton(this.x + width - 24, this.y - yStart + i * 30 + 8, 0, 0));
                this.buttons.add(new Button( this.x + width - buttonWidth - 29, this.y - yStart + i * 30 + centeredButtonHeight + 3, 5,5, Button.defaultButton, "Join", false, "partyid:"));
            }
        }

        public void update(){
            this.buttonWidth = Utils.getStringWidth("Join") + 10;
            this.centeredButtonHeight =  (17 - mc.fontRendererObj.FONT_HEIGHT) / 2;
            this.centeredTextHeight = (27 - mc.fontRendererObj.FONT_HEIGHT) / 2;

            this.contentHeight = 30 * items + 60 * this.enabledExpandButtons + 3;
            this.bar.updateContentHeight(this.contentHeight);

        }
        @Override
        public void draw(Minecraft mc, int mouseX, int mouseY, boolean shouldRenderEffects) {
            super.draw(mc, mouseX, mouseY, shouldRenderEffects);
            this.othersButtons.clear();

            int yStart = (int) this.bar.getStartPos();
            int developedItemsDrawn = 0;

            for(int i = 0; i < items; i++){
                this.expandButtons.get(i).yPosition = this.y - yStart + i * 30 + 60 * developedItemsDrawn + 8;
                this.buttons.get(i).yPosition = this.y - yStart + i * 30 + centeredButtonHeight + 60 * developedItemsDrawn + 3;

                Gui.drawRect(this.x + 3, this.y - yStart + i * 30 + 60 * developedItemsDrawn + 3 , this.x + this.width - 3, this.y - yStart + i * 30 + developedItemsDrawn * 60 + 30, ColorsUtils.DEFAULT_LIST_ITEM_BACKGROUND_COLOR.getColorInteger());
                RenderUtils.renderItem(ItemUtils.getQuestionMark(), this.x + 8,this.y - yStart + i * 30 + 60 * developedItemsDrawn + 8,1);
                RenderUtils.renderText(mc, "Player's Party",this.x + 27,this.y - yStart + centeredTextHeight + i * 30 + 60 * developedItemsDrawn + 3,1,false);

                this.buttons.get(i).drawButton(mc, mouseX, mouseY, shouldRenderEffects);
                this.expandButtons.get(i).drawButton(mc, mouseX, mouseY);

                if(this.expandButtons.get(i).extended){
                    for(int j = 0; j < 2; j++) {
                        String playerName = "Player87969ezwin";
                        Gui.drawRect(this.x + 3, this.y - yStart + (i + j) * 30 + developedItemsDrawn * 60 + 30, this.x + this.width - 3, this.y - yStart + (i + j + 1) * 30 + developedItemsDrawn * 60 + 30, ColorsUtils.LIST_ITEM_LIGHTER_BACKGROUND_COLOR.getColorInteger());
                        RenderUtils.renderItem(ItemUtils.getQuestionMark(), this.x + 8, this.y - yStart + (i + j) * 30 + developedItemsDrawn * 60 + 37, 1);
                        RenderUtils.renderText(mc, playerName, this.x + 27, this.y - yStart + (i + j) * 30 + 60 * developedItemsDrawn + (16 - mc.fontRendererObj.FONT_HEIGHT) / 2 + 37, 1, false);
                        IconButton ignoreBtn = new IconButton(this.x + this.width - 26, this.y - yStart + (i +j) * 30 + developedItemsDrawn * 60 + 36, 1, 1, IconButton.transparentBackgroundRedIconButton, IGNORE_ICON, false, "blockuser:" + playerName);
                        ignoreBtn.drawButton(mc, mouseX, mouseY, shouldRenderEffects);
                        this.othersButtons.add(ignoreBtn);
                    }
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
                                //TODO: Send packet to join party
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
                                this.update();
                            }
                        }
                    }
                    for (Button button : othersButtons) {
                        if (button.dataBinding.startsWith("blockuser:")) {
                            if (button.mousePressed(mc, mouseX, mouseY)) {
                                this.parentGui.confirmOverlay.ask(ConfirmOverlay.BLOCK_USER, button.dataBinding.substring(10).getBytes());
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
