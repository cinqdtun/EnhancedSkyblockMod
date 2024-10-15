package com.cinqdt1.Mod.features;

import com.cinqdt1.Mod.cinqdt1Mod;
import com.cinqdt1.Mod.config.ModConfiguration;
import com.cinqdt1.Mod.events.InitEvent;
import com.cinqdt1.Mod.events.RenderOverlay;
import com.cinqdt1.Mod.gui.GuiFeature;
import com.cinqdt1.Mod.utils.ItemUtils;
import com.cinqdt1.Mod.utils.RenderUtils;
import com.cinqdt1.Mod.utils.Utils;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.StringUtils;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class BatFirework {
    private final Pattern candyPattern = Pattern.compile("^You received (\\d{1,2}) Purple Candies in your Trick or Treat Bag!$");
    @SubscribeEvent
    public void onInit(InitEvent event)
    {
        List<String> guiText = new ArrayList<>();
        guiText.add(EnumChatFormatting.RED + "11.31");
        GuiFeature guiInfo = new GuiFeature(cinqdt1Mod.guiEdit.getId(), 4, 3, 0, "batFirework", guiText, ItemUtils.getBatFireworkTexture());
        cinqdt1Mod.guiEdit.registerFeature(guiInfo);
    }
    @SubscribeEvent
    public void onChat(ClientChatReceivedEvent event){
        String message = StringUtils.stripControlCodes(event.message.getUnformattedText());

        if (event.type == 2) return;
        if (message.contains(":")) return;

        Matcher candyMatcher = candyPattern.matcher(message);

        if(!candyMatcher.find()) return;

        try{
            int candy = Integer.parseInt(candyMatcher.group(1));
            cinqdt1Mod.newModConfig.setInteger("batFirework", "loot", "candy", cinqdt1Mod.newModConfig.getInteger("batFirework", "loot", "candy") + candy);
            cinqdt1Mod.newModConfig.setInteger("batFirework", "loot", "fireworks_lauched", cinqdt1Mod.newModConfig.getInteger("batFirework", "loot", "fireworks_lauched") + 1);
            cinqdt1Mod.newModConfig.saveConfig();
        }catch (Exception e){
            //Utils.sendModMessage(e.getMessage());

        }
    }

    @SubscribeEvent
    public void onRenderOverlay(RenderOverlay event){
        if(!Utils.inSkyblock) return;
        if(!ModConfiguration.batFireworkStatsState) return;
        try {
            int candy = cinqdt1Mod.newModConfig.getInteger("batFirework", "loot", "candy");
            int fireworks_lauched = cinqdt1Mod.newModConfig.getInteger("batFirework", "loot", "fireworks_lauched");
            List<String> averageCandyFireworkText = new ArrayList<>();
            averageCandyFireworkText.add(EnumChatFormatting.RED + String.format("%.2f", (fireworks_lauched > 0) ? ((double)candy / fireworks_lauched) : 0));
            RenderUtils.renderOverlay(cinqdt1Mod.newModConfig.getInteger("batFirework", "position", "x"), cinqdt1Mod.newModConfig.getInteger("batFirework", "position", "y"), cinqdt1Mod.newModConfig.getFloat("batFirework", "position", "scale"), ItemUtils.getBatFireworkTexture(), 4, 3, averageCandyFireworkText, 0);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
