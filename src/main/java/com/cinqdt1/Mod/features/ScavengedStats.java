package com.cinqdt1.Mod.features;

import com.cinqdt1.Mod.cinqdt1Mod;
import com.cinqdt1.Mod.config.ModConfiguration;
import com.cinqdt1.Mod.events.RenderOverlay;
import com.cinqdt1.Mod.utils.ItemUtils;
import com.cinqdt1.Mod.utils.RenderUtils;
import com.cinqdt1.Mod.utils.Utils;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.StringUtils;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class ScavengedStats {

    private final Pattern scavengedPattern = Pattern.compile("^You found Scavenged [a-zA-Z]* [a-zA-Z]* with your Metal Detector!$");
    private int tickElapsed = 0;

    @SubscribeEvent
    public void onTick(TickEvent.ClientTickEvent event){
        if(event.phase != TickEvent.Phase.START) return;
        if(!Utils.inDivan) return;
        if(!ModConfiguration.scavengedStatsTrackState) return;
        tickElapsed++;
        if(tickElapsed % 20 != 0) return;
        try {
            cinqdt1Mod.newModConfig.setInteger("scathaCoolodwn", "stats", "time", cinqdt1Mod.newModConfig.getInteger("scavengedStats", "stats", "time") + 1);
            cinqdt1Mod.newModConfig.saveConfig();
            tickElapsed = 0;
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @SubscribeEvent
    public void onChat(ClientChatReceivedEvent event) {
        if(!ModConfiguration.scavengedStatsTrackState) return;
        String message = StringUtils.stripControlCodes(event.message.getUnformattedText());

        if(event.type == 2) return;
        if(message.contains(":")) return;
        if(!scavengedPattern.matcher(message).find()) return;

        try{
            cinqdt1Mod.newModConfig.setInteger("scavengedStats", "loot", "piece", cinqdt1Mod.newModConfig.getInteger("scavengedStats", "loot", "piece") + 1);
            cinqdt1Mod.newModConfig.saveConfig();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @SubscribeEvent
    public void onRenderOverlay(RenderOverlay event){
        if(!Utils.inDivan) return;
        if(!ModConfiguration.scavengedStatsState) return;
        try {
            List<String> scavengedStatsText = new ArrayList<>();
            scavengedStatsText.add(EnumChatFormatting.GOLD + "" + EnumChatFormatting.BOLD + "Scavenged Stats\n" +
                    "Avg time per pair: " + Utils.convertToFormattedDuration(cinqdt1Mod.newModConfig.getInteger("scavengedStats", "loot", "piece") != 0 ? (int) ((float)cinqdt1Mod.newModConfig.getInteger("scavengedStats", "stats", "time") / cinqdt1Mod.newModConfig.getInteger("scavengedStats", "loot", "piece") * 4) : 0, "UNKNOWN", true));
            RenderUtils.renderOverlay(cinqdt1Mod.newModConfig.getInteger("scavengedStats", "position", "x"), cinqdt1Mod.newModConfig.getInteger("scavengedStats", "position", "y"), cinqdt1Mod.newModConfig.getFloat("scavengedStats", "position", "scale"), ItemUtils.getScavengedStatsTexture(), 4, 3, scavengedStatsText, 0);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
