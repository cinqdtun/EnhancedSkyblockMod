package com.cinqdt1.Mod.features;

import com.cinqdt1.Mod.cinqdt1Mod;
import com.cinqdt1.Mod.config.ModConfiguration;
import com.cinqdt1.Mod.events.InitEvent;
import com.cinqdt1.Mod.events.RenderOverlay;
import com.cinqdt1.Mod.gui.GuiFeature;
import com.cinqdt1.Mod.utils.ItemUtils;
import com.cinqdt1.Mod.utils.RenderUtils;
import com.cinqdt1.Mod.utils.Utils;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.StringUtils;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.ArrayList;
import java.util.List;

public class MythologicalFeatures {

    public MythologicalMob digMob = MythologicalMob.EMPTY;

    @SubscribeEvent
    public void onInit(InitEvent event)
    {
        List<String> guiText = new ArrayList<>();
        guiText.add(EnumChatFormatting.DARK_GRAY + "[" + EnumChatFormatting.GRAY + "Lv260" + EnumChatFormatting.DARK_GRAY + "]" + EnumChatFormatting.DARK_GREEN + " Exalted Gaia Construct" + EnumChatFormatting.YELLOW + " 876k" + EnumChatFormatting.WHITE + "/" + EnumChatFormatting.GREEN + "1.5M" + EnumChatFormatting.RED + "❤");
        GuiFeature guiInfo = new GuiFeature(cinqdt1Mod.guiEdit.getId(), 0, 0, 0, "mythologicalHp", guiText, null);
        cinqdt1Mod.guiEdit.registerFeature(guiInfo);
        guiText.clear();
        guiText.add(EnumChatFormatting.RED + "20m 33s");
        guiInfo = new GuiFeature(cinqdt1Mod.guiEdit.getId(), 4, 3, 0, "mythologicalLastInqui", guiText, ItemUtils.getInquiHeadTexture());
        cinqdt1Mod.guiEdit.registerFeature(guiInfo);
    }
    @SubscribeEvent
    public void onRenderMythoHp(RenderOverlay event){
        if(!Utils.inSkyblock) return;
        if(!ModConfiguration.hpMythologicalState) return;
        if(Minecraft.getMinecraft().theWorld.loadedEntityList == null) return;
        List<String> mythologicalHpText = new ArrayList<>();

        for(Entity entity : Minecraft.getMinecraft().theWorld.loadedEntityList) {
            if (entity.getCustomNameTag() == null || entity.getCustomNameTag().isEmpty()) continue;
            String unformattedName = StringUtils.stripControlCodes(entity.getCustomNameTag());
            if(unformattedName.contains("Minos Inquisitor")) {
                mythologicalHpText.add(entity.getCustomNameTag());
                try {
                    RenderUtils.renderOverlay(cinqdt1Mod.newModConfig.getInteger("mythologicalHp", "position", "x"), cinqdt1Mod.newModConfig.getInteger("mythologicalHp", "position", "y"), cinqdt1Mod.newModConfig.getFloat("mythologicalHp", "position", "scale"), null, 0, 0, mythologicalHpText, 0);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return;
            }
        }
        for(Entity entity : Minecraft.getMinecraft().theWorld.loadedEntityList){
            if(entity.getCustomNameTag() == null || entity.getCustomNameTag().isEmpty()) continue;
            String unformattedName = StringUtils.stripControlCodes(entity.getCustomNameTag());
            if(unformattedName.contains("Minos Hunter") && digMob == MythologicalMob.HUNTER){
                mythologicalHpText.add(entity.getCustomNameTag());
                try{
                    RenderUtils.renderOverlay(cinqdt1Mod.newModConfig.getInteger("mythologicalHp", "position", "x"), cinqdt1Mod.newModConfig.getInteger("mythologicalHp", "position", "y"), cinqdt1Mod.newModConfig.getFloat("mythologicalHp", "position", "scale"), null, 0,0, mythologicalHpText, 0);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return;
            }else if(unformattedName.contains("Bagheera") && digMob == MythologicalMob.LYNXES){
                mythologicalHpText.add(entity.getCustomNameTag());
                try{
                    RenderUtils.renderOverlay(cinqdt1Mod.newModConfig.getInteger("mythologicalHp", "position", "x"), cinqdt1Mod.newModConfig.getInteger("mythologicalHp", "position", "y"), cinqdt1Mod.newModConfig.getFloat("mythologicalHp", "position", "scale"), null, 0,0, mythologicalHpText, 0);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return;
            }else if(unformattedName.contains("Minotaur") && digMob == MythologicalMob.MINOTAUR){
                mythologicalHpText.add(entity.getCustomNameTag());
                try{
                    RenderUtils.renderOverlay(cinqdt1Mod.newModConfig.getInteger("mythologicalHp", "position", "x"), cinqdt1Mod.newModConfig.getInteger("mythologicalHp", "position", "y"), cinqdt1Mod.newModConfig.getFloat("mythologicalHp", "position", "scale"), null, 0,0, mythologicalHpText, 0);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return;
            }else if(unformattedName.contains("Gaia Construct") && digMob == MythologicalMob.GAIA){
                mythologicalHpText.add(entity.getCustomNameTag());
                try{
                    RenderUtils.renderOverlay(cinqdt1Mod.newModConfig.getInteger("mythologicalHp", "position", "x"), cinqdt1Mod.newModConfig.getInteger("mythologicalHp", "position", "y"), cinqdt1Mod.newModConfig.getFloat("mythologicalHp", "position", "scale"), null, 0,0, mythologicalHpText, 0);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return;
            }else if(unformattedName.contains("Minos Champion") && digMob == MythologicalMob.CHAMPION){
                mythologicalHpText.add(entity.getCustomNameTag());
                try{
                    RenderUtils.renderOverlay(cinqdt1Mod.newModConfig.getInteger("mythologicalHp", "position", "x"), cinqdt1Mod.newModConfig.getInteger("mythologicalHp", "position", "y"), cinqdt1Mod.newModConfig.getFloat("mythologicalHp", "position", "scale"), null, 0,0, mythologicalHpText, 0);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return;
            }
        }
    }

    @SubscribeEvent
    public void onRenderLastInqui(RenderOverlay event) throws Exception {
        if (!Utils.inSkyblock) return;
        if (!ModConfiguration.lastInquiState) return;
        if (Minecraft.getMinecraft().theWorld.loadedEntityList == null) return;
        List<String> mythologicalLastInquiText = new ArrayList<>();
        int last_inquisitor_t = cinqdt1Mod.newModConfig.getInteger("mythologicalLastInqui", "stats", "lastInqui");
        String formattedDuration = Utils.convertToFormattedDuration( (int)(System.currentTimeMillis() / 1000) - last_inquisitor_t, "N/A", true);
        mythologicalLastInquiText.add(EnumChatFormatting.RED + (last_inquisitor_t == 0 ? "N/A" : formattedDuration));
        try{
            RenderUtils.renderOverlay(cinqdt1Mod.newModConfig.getInteger("mythologicalLastInqui", "position", "x"), cinqdt1Mod.newModConfig.getInteger("mythologicalLastInqui", "position", "y"), cinqdt1Mod.newModConfig.getFloat("mythologicalLastInqui", "position", "scale"), ItemUtils.getInquiHeadTexture(), 4,3, mythologicalLastInquiText, 0);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public void onChat(ClientChatReceivedEvent event) throws Exception {
        String message = StringUtils.stripControlCodes(event.message.getUnformattedText());
        if (!Utils.inSkyblock) return;
        if (event.type == 2) return;
        if (message.contains(":")) return;

        if (message.contains("a Minos Hunter!")) {
            digMob = MythologicalMob.HUNTER;
        } else if (message.contains("Siamese Lynxes!")) {
            digMob = MythologicalMob.LYNXES;
        } else if (message.contains("a Minotaur!")) {
            digMob = MythologicalMob.MINOTAUR;
        } else if (message.contains("a Gaia Construct!")) {
            digMob = MythologicalMob.GAIA;
        } else if (message.contains("a Minos Champion!")) {
            Minecraft mc = Minecraft.getMinecraft();
            List<Entity> listWorldEntity = mc.theWorld.getLoadedEntityList();
            for (Entity entity : listWorldEntity) {
                if (entity.getName().contains("Minos Champion")) {
                    digMob = MythologicalMob.CHAMPION;
                } else if (entity.getName().contains("Minos Inquisitor")) {
                    digMob = MythologicalMob.INQUISITOR;
                    cinqdt1Mod.newModConfig.setInteger("mythologicalLastInqui", "stats", "lastInqui", (int) (System.currentTimeMillis() / 1000));
                    cinqdt1Mod.newModConfig.saveConfig();
                }
            }
        } else if (message.contains("a Minos Inquisitor!")) {
            digMob = MythologicalMob.INQUISITOR;
            cinqdt1Mod.newModConfig.setInteger("mythologicalLastInqui", "stats", "lastInqui", (int) (System.currentTimeMillis() / 1000));
            cinqdt1Mod.newModConfig.saveConfig();
        }
    }
    enum MythologicalMob{
        HUNTER,
        LYNXES,
        MINOTAUR,
        GAIA,
        CHAMPION,
        INQUISITOR,
        EMPTY
    }
}
