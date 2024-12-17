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
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.StringUtils;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class ScathaMining {
    private boolean isScathaSpawned = false;
    private SCATHA_TYPE scathaType = SCATHA_TYPE.UNKNOWN;
    private long scathaSpawnTime = 0;
    @SubscribeEvent
    public void onInit(InitEvent event)
    {
        List<String> guiText = new ArrayList<>();
        guiText.add(EnumChatFormatting.RED + "19.5s");
        GuiFeature guiInfo = new GuiFeature(cinqdt1Mod.guiEdit.getId(), 0, 0, 0, "scathaCoolodwn", guiText, null);
        cinqdt1Mod.guiEdit.registerFeature(guiInfo);
    }
    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public void onChat(ClientChatReceivedEvent event) {
        String message = StringUtils.stripControlCodes(event.message.getUnformattedText());

        if (!Utils.inSkyblock) return;
        if (event.type == 2) return;
        if (message.contains(":")) return;

        if (message.contains("You hear the sound of something approaching...")){
            isScathaSpawned = true;
            scathaSpawnTime = System.currentTimeMillis();
        }
    }

    @SubscribeEvent
    public void onTick(@NotNull TickEvent.ClientTickEvent event) {
        if (event.phase != TickEvent.Phase.START) return;
        if (!isScathaSpawned) return;
        if (scathaSpawnTime - System.currentTimeMillis() >= 5000)
        {
            isScathaSpawned = false;
            return;
        }

        Minecraft mc = Minecraft.getMinecraft();
        List<Entity> listWorldEntity = mc.theWorld.getLoadedEntityList();
        for (Entity entity : listWorldEntity) {
            BlockPos entityPos = entity.getPosition();
            double distance = mc.thePlayer.getPosition().distanceSq(entityPos);
            if (distance > 200)
                continue;
            if (entity.getName().contains("Scatha")) {
                scathaType = SCATHA_TYPE.SCATHA;
                Utils.sendModMessage("Scatha detected");
                Utils.showTitle("Scatha Spawned", "");
                isScathaSpawned = false;
                break;
            } else if (entity.getName().contains("Worm")){
                scathaType = SCATHA_TYPE.WORM;
                Utils.sendModMessage("Worm detected");
                Utils.showTitle("Worm Spawned", "");
                isScathaSpawned = false;
                break;
            }
        }
    }

    @SubscribeEvent
    public void renderPlayerInfo(RenderOverlay event){
        if(!Utils.inSkyblock) return;
        if(!ModConfiguration.scathaCooldown) return;
        long timeLeft = scathaSpawnTime + 30000 - System.currentTimeMillis();
        List<String> scathaText = new ArrayList<>();
        scathaText.add((timeLeft > 0) ? EnumChatFormatting.RED + "" + timeLeft / 1000 + "s" : EnumChatFormatting.GREEN + "Ready");
        try {
            RenderUtils.renderOverlay(cinqdt1Mod.newModConfig.getInteger("scathaCoolodwn", "position", "x"), cinqdt1Mod.newModConfig.getInteger("scathaCoolodwn", "position", "y"), cinqdt1Mod.newModConfig.getFloat("scathaCoolodwn", "position", "scale"), null, 0, 0, scathaText, 0);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    enum SCATHA_TYPE {
        UNKNOWN,
        SCATHA,
        WORM
    }
}
