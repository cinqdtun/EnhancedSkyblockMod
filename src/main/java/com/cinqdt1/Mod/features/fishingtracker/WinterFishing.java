package com.cinqdt1.Mod.features.fishingtracker;

import com.cinqdt1.Mod.cinqdt1Mod;
import com.cinqdt1.Mod.config.ModConfiguration;
import com.cinqdt1.Mod.events.InitEvent;
import com.cinqdt1.Mod.events.RenderOverlay;
import com.cinqdt1.Mod.gui.GuiFeature;
import com.cinqdt1.Mod.utils.RenderUtils;
import com.cinqdt1.Mod.utils.Utils;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.StringUtils;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.ArrayList;
import java.util.List;

public class WinterFishing {
    private final String[] fishingCreatures = new String[]{"A Squid appeared.", "You caught a Sea Walker.", "Pitch Darkness reveals a Night Squid.", "You stumbled upon a Sea Guardian.", "It looks like you've disrupted the Sea Witch's brewing session. Watch out, she's furious!", "You reeled in a Sea Archer.", "The Rider of the Deep has emerged.", "Huh? A Catfish!", "Gross! A Sea Leech!", "You've discovered a Guardian Defender of the sea.", "You have awoken the Deep Sea Protector, prepare for a battle!", "The Water Hydra has come to test your strength.",
            "The Sea Emperor arises from the depths.", "Your Chumcap Bucket trembles, it's an Agarimoo.", "Is that even a fish? It's the Carrot King!",
            "A tiny fin emerges from the water, you've caught a Nurse Shark.", "You spot a fin as blue as the water it came from, it's a Blue Shark.", "A striped beast bounds from the depths, the wild Tiger Shark!",
            "Hide no longer, a Great White Shark has tracked your scent and thirsts for your blood!",
            "Frozen Steve fell into the pond long ago, never to resurface...until now!", "It's a snowman! He looks harmless.", "The Grinch stole Jerry's gifts... Get them back!", "What is this creature!?", "You found a forgotten Nutcracker laying beneath the ice.", "A Reindrake forms from the depths."
    };
    @SubscribeEvent
    public void onInit(InitEvent event)
    {
        List<String> guiText = new ArrayList<>();
        guiText.add(EnumChatFormatting.DARK_AQUA + "" + EnumChatFormatting.BOLD + "Winter Fishing\n" +
                EnumChatFormatting.AQUA + "Since last yeti :\n" +
                EnumChatFormatting.AQUA + "Since last reindrake");
        guiText.add(EnumChatFormatting.DARK_AQUA + "\n" +
                EnumChatFormatting.AQUA + "312 (3.34%)\n" +
                EnumChatFormatting.AQUA + "312 (0.34%)");
        GuiFeature guiInfo = new GuiFeature(cinqdt1Mod.guiEdit.getId(), 0, 0, 20, "winterFishing", guiText, null);
        cinqdt1Mod.guiEdit.registerFeature(guiInfo);
    }
    @SubscribeEvent
    public void onChat(ClientChatReceivedEvent event) throws Exception {
        String message = StringUtils.stripControlCodes(event.message.getUnformattedText());

        if (event.type == 2) return;
        if (message.contains(":")) return;

        for (String creature : fishingCreatures) {
            if (message.contains(creature)) {
                cinqdt1Mod.newModConfig.set("winterFishing", "drop", "totalSeaCreature", cinqdt1Mod.newModConfig.getInteger("winterFishing", "drop", "totalSeaCreature") + 1);
                if (message.contains("A Reindrake forms from the depths."))
                {
                    Utils.sendModMessage("You have caught a Reindrake after " + cinqdt1Mod.newModConfig.getInteger("winterFishing", "drop", "sinceReindrake") + " sea creatures!");
                    cinqdt1Mod.newModConfig.set("winterFishing", "drop", "sinceReindrake", 0);
                    cinqdt1Mod.newModConfig.set("winterFishing", "drop", "reindrake", cinqdt1Mod.newModConfig.getInteger("winterFishing", "drop", "reindrake") + 1);
                }
                else
                    cinqdt1Mod.newModConfig.set("winterFishing", "drop", "sinceReindrake", cinqdt1Mod.newModConfig.getInteger("winterFishing", "drop", "sinceReindrake") + 1);

                if (message.contains("What is this creature!?"))
                {
                    Utils.sendModMessage("You have caught a Yeti after " + cinqdt1Mod.newModConfig.getInteger("winterFishing", "drop", "sinceYeti") + " sea creatures!");
                    cinqdt1Mod.newModConfig.set("winterFishing", "drop", "sinceYeti", 0);
                    cinqdt1Mod.newModConfig.set("winterFishing", "drop", "yeti", cinqdt1Mod.newModConfig.getInteger("winterFishing", "drop", "yeti") + 1);
                }
                else
                    cinqdt1Mod.newModConfig.set("winterFishing", "drop", "sinceYeti", cinqdt1Mod.newModConfig.getInteger("winterFishing", "drop", "sinceYeti") + 1);

                cinqdt1Mod.newModConfig.saveConfig();
            }
        }
    }

    @SubscribeEvent
    public void renderPlayerInfo(RenderOverlay event) {
        if(!ModConfiguration.winterTrackState) return;
        try{
            int totalYeti = cinqdt1Mod.newModConfig.getInteger("winterFishing", "drop", "yeti");
            int totalReindrake = cinqdt1Mod.newModConfig.getInteger("winterFishing", "drop", "reindrake");
            int totalSeaCreature = cinqdt1Mod.newModConfig.getInteger("winterFishing", "drop", "totalSeaCreature");
            List<String> winterSeaCreatureText = new ArrayList<>();
            winterSeaCreatureText.add(EnumChatFormatting.DARK_AQUA + "" + EnumChatFormatting.BOLD + "Winter Fishing\n" +
                    EnumChatFormatting.AQUA + "Since last yeti :\n" +
                    EnumChatFormatting.AQUA + "Since last reindrake");
            winterSeaCreatureText.add(EnumChatFormatting.DARK_AQUA + "\n" +
                    EnumChatFormatting.AQUA + cinqdt1Mod.newModConfig.getInteger("winterFishing", "drop", "sinceYeti") + " (" + (totalYeti <= 0 ? "NA" : Math.round((double)totalSeaCreature / totalYeti * 100.0) / 100) + "%)" + "\n" +
                    EnumChatFormatting.AQUA + cinqdt1Mod.newModConfig.getInteger("winterFishing", "drop", "sinceReindrake") + " (" + (totalReindrake <= 0 ? "NA" : Math.round((double)totalSeaCreature / totalReindrake * 100.0) / 100) + "%)");
            RenderUtils.renderOverlay(cinqdt1Mod.newModConfig.getInteger("winterFishing", "position", "x"), cinqdt1Mod.newModConfig.getInteger("winterFishing", "position", "y"), cinqdt1Mod.newModConfig.getFloat("winterFishing", "position", "scale"), null, 0, 0, winterSeaCreatureText, 20);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
