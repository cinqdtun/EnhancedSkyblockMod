package com.cinqdt1.Mod.features;

import com.cinqdt1.Mod.cinqdt1Mod;
import com.cinqdt1.Mod.config.ModConfiguration;
import com.cinqdt1.Mod.events.InitEvent;
import com.cinqdt1.Mod.events.RenderOverlay;
import com.cinqdt1.Mod.gui.GuiFeature;
import com.cinqdt1.Mod.utils.RenderUtils;
import com.cinqdt1.Mod.utils.Utils;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.StringUtils;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.ArrayList;
import java.util.List;

public class AshfangFeatures {

    @SubscribeEvent
    public void onInit(InitEvent event)
    {
        List<String> guiText = new ArrayList<>();
        guiText.add(EnumChatFormatting.YELLOW + "﴾ " + EnumChatFormatting.DARK_GRAY + "[" + EnumChatFormatting.GRAY + "Lv200" + EnumChatFormatting.DARK_GRAY + "]" + EnumChatFormatting.DARK_GRAY + " Ashfang" + EnumChatFormatting.YELLOW + " 35M" + EnumChatFormatting.WHITE + "/" + EnumChatFormatting.GREEN + "50M" + EnumChatFormatting.RED + "❤" + EnumChatFormatting.YELLOW + " ﴿");
        GuiFeature guiInfo = new GuiFeature(cinqdt1Mod.guiEdit.getId(), 0, 0, 0,"ashfangHp", guiText, null);
        cinqdt1Mod.guiEdit.registerFeature(guiInfo);
    }
    @SubscribeEvent
    public void onRenderOverlay(RenderOverlay event){
        if (!Utils.inCrimsonIsle) return;
        if(!ModConfiguration.hpAshfangState) return;
        if (Minecraft.getMinecraft().theWorld.loadedEntityList == null) return;
        for(Entity entity : Minecraft.getMinecraft().theWorld.loadedEntityList) {
            if (entity.getCustomNameTag() == null || entity.getCustomNameTag().isEmpty()) continue;
            String unformattedName = StringUtils.stripControlCodes(entity.getCustomNameTag());
            if (unformattedName.contains("[Lv200] Ashfang")) {
                List<String> ashfangHpText = new ArrayList<>();
                ashfangHpText.add(entity.getCustomNameTag());
                try{
                    RenderUtils.renderOverlay(cinqdt1Mod.newModConfig.getInteger("ashfangHp", "position", "x"), cinqdt1Mod.newModConfig.getInteger("ashfangHp", "position", "y"), cinqdt1Mod.newModConfig.getFloat("ashfangHp", "position", "scale"), null, 0,0, ashfangHpText, 0);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return;
            }
        }

    }
}