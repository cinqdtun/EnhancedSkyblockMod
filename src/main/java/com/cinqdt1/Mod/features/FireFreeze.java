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
import net.minecraft.entity.item.EntityArmorStand;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.StringUtils;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

import java.util.ArrayList;
import java.util.List;

public class FireFreeze {
    private int tick = -40;
    private boolean isDialogueHasSpawned = false;
    private boolean isCountdownFinished = false;

    @SubscribeEvent
    public void onInit(InitEvent event)
    {
        List<String> guiText = new ArrayList<>();
        guiText.add(EnumChatFormatting.GRAY + "Fire Freeze\n" +
                EnumChatFormatting.GRAY + "in " + EnumChatFormatting.RED + "1.3");
        GuiFeature guiInfo = new GuiFeature(cinqdt1Mod.guiEdit.getId(), 4, 3, 0, "fireFreeze", guiText, ItemUtils.getFireFreezeTexture());
        cinqdt1Mod.guiEdit.registerFeature(guiInfo);
    }
    @SubscribeEvent
    public void onTick(TickEvent.ClientTickEvent event){
        if(event.phase != TickEvent.Phase.START) return;
        if(!Utils.inF3) return;
        if(tick < 100 && isDialogueHasSpawned) tick++;
        if(isDialogueHasSpawned) return;
        if(Minecraft.getMinecraft().theWorld == null) return;
        List<Entity> entitiesLoaded = Minecraft.getMinecraft().theWorld.getLoadedEntityList();
        for(Entity entity : entitiesLoaded){
            if(!(entity instanceof EntityArmorStand)) continue;
            String unformattedName = StringUtils.stripControlCodes(entity.getName());
            if(unformattedName.equals("Even if you took my barrier down, I can still fight.")){
                isDialogueHasSpawned = true;
                return;
            }
        }
    }

    @SubscribeEvent
    public void onRenderOverlay(RenderOverlay event){
        if(!ModConfiguration.fireFreezeOverlayState) return;
        if(isCountdownFinished || !isDialogueHasSpawned) return;
        if(tick == 100) isCountdownFinished = true;
        List<String> fireFreezeText = new ArrayList<>();
        fireFreezeText.add(EnumChatFormatting.GRAY + "Fire Freeze" +
                EnumChatFormatting.GRAY + "in " + ((tick < 0) ? EnumChatFormatting.RED : EnumChatFormatting.GREEN) + Utils.convertDoubleToString(Utils.transformTickToSeconds((tick < 0) ? Utils.negate(tick) : tick)));
        try {
            RenderUtils.renderOverlay(cinqdt1Mod.newModConfig.getInteger("fireFreeze", "position", "x"), cinqdt1Mod.newModConfig.getInteger("fireFreeze", "position", "y"), cinqdt1Mod.newModConfig.getFloat("fireFreeze", "position", "scale"), ItemUtils.getFireFreezeTexture(), 4,3, fireFreezeText, 0);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @SubscribeEvent
    public void onLoadWorld(WorldEvent.Load event){
        tick = -40;
        isDialogueHasSpawned = false;
        isCountdownFinished = false;
    }
}
