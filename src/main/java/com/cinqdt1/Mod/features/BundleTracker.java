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
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityArmorStand;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.StringUtils;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.client.event.RenderLivingEvent;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class BundleTracker {

    private final Pattern bobombPattern = Pattern.compile("Bob-omb\\s?x?(\\d{2})");
    private final Pattern divanFragmentPattern = Pattern.compile("Divan Fragment\\s?x?(\\d{0,2})");
    private final List<UUID> countedUUIDS = new ArrayList<>();
    private final Minecraft mc = Minecraft.getMinecraft();
    @SubscribeEvent
    public void onInit(InitEvent event)
    {
        List<String> guiText = new ArrayList<>();
        guiText.add(EnumChatFormatting.YELLOW + "" + EnumChatFormatting.BOLD + "Crystal Loot Bundle\n" +
                EnumChatFormatting.GOLD + "Bob-omb :\n" +
                EnumChatFormatting.GOLD + "Pickonimbus 2000:\n" +
                EnumChatFormatting.GOLD + "Prehistoric Egg:\n" +
                EnumChatFormatting.GOLD + "Divan's Fragment:\n" +
                EnumChatFormatting.GOLD + "Recall Potion:\n" +
                EnumChatFormatting.GOLD + "Jaderald:\n" +
                EnumChatFormatting.GOLD + "Divan's Alloy:\n" +
                EnumChatFormatting.GOLD + "Fortune IV:\n" +
                EnumChatFormatting.GOLD + "Quick Claw:\n" +
                EnumChatFormatting.GOLD + "Gemstone Mixture:\n" +
                EnumChatFormatting.GOLD + "Bundle Runs:");
        guiText.add("\n" +
                EnumChatFormatting.GOLD + "96\n" +
                EnumChatFormatting.GOLD + "4\n" +
                EnumChatFormatting.GOLD + "5\n" +
                EnumChatFormatting.GOLD + "2\n" +
                EnumChatFormatting.GOLD + "0\n" +
                EnumChatFormatting.GOLD + "1\n" +
                EnumChatFormatting.GOLD + "0\n" +
                EnumChatFormatting.GOLD + "1\n" +
                EnumChatFormatting.GOLD + "0\n" +
                EnumChatFormatting.GOLD + "0\n" +
                EnumChatFormatting.GOLD + "8");
        GuiFeature guiInfo = new GuiFeature(cinqdt1Mod.guiEdit.getId(), 0, 0, 0, "bundleTracker", guiText, null);
        cinqdt1Mod.guiEdit.registerFeature(guiInfo);
    }
    //TODO : Change event to an event not related to render
    @SubscribeEvent
    public void onBeforeRenderEntity(RenderLivingEvent.Pre<EntityLivingBase> event) {
        try {
            Entity entity = event.entity;
            if (!entity.isInvisible() || !(entity instanceof EntityArmorStand) || !Utils.inNucleus) return;
            EntityArmorStand armorStand = (EntityArmorStand) entity;
            UUID armorStandUUID = armorStand.getUniqueID();
            ItemStack headSlot = armorStand.getCurrentArmor(3);
            ItemStack handSlot = armorStand.getHeldItem();
            for (UUID uuid : countedUUIDS) {
                if (armorStandUUID.equals(uuid)) return;
            }
            if (entity.hasCustomName() && headSlot == null && handSlot == null) {
                /*
                 * Handle divan's fragment & bob-omb
                 */
                String unformattedName = StringUtils.stripControlCodes(entity.getName());
                Matcher matchBobomb = bobombPattern.matcher(unformattedName);
                Matcher matchDivanFragment = divanFragmentPattern.matcher(unformattedName);
                if (matchBobomb.find()) {
                    if (matchBobomb.groupCount() < 1) {
                        cinqdt1Mod.newModConfig.setInteger("bundleTracker", "loot", "bobomb", cinqdt1Mod.newModConfig.getInteger("bundleTracker", "loot", "bobomb") + 1);
                    } else {
                        cinqdt1Mod.newModConfig.setInteger("bundleTracker", "loot", "bobomb", cinqdt1Mod.newModConfig.getInteger("bundleTracker", "loot", "bobomb") + Integer.parseInt(matchBobomb.group(1)));
                    }
                    cinqdt1Mod.newModConfig.saveConfig();
                    countedUUIDS.add(armorStandUUID);
                } else if (matchDivanFragment.find()) {
                    if (matchDivanFragment.groupCount() < 1) {
                        cinqdt1Mod.newModConfig.setInteger("bundleTracker", "loot", "divanFragment", cinqdt1Mod.newModConfig.getInteger("bundleTracker", "loot", "divanFragment") + 1);
                    } else {
                        cinqdt1Mod.newModConfig.setInteger("bundleTracker", "loot", "divanFragment", cinqdt1Mod.newModConfig.getInteger("bundleTracker", "loot", "divanFragment") + Integer.parseInt(matchDivanFragment.group(1)));
                    }
                    cinqdt1Mod.newModConfig.saveConfig();
                    countedUUIDS.add(armorStandUUID);
                }
            } else if (headSlot != null) {
                /*
                 * Handle Recall Potion & Jaderald & Divan's Alloy & Quick Claw & gemstone mixture
                 */
                String unformattedName = StringUtils.stripControlCodes(headSlot.getDisplayName());
                switch (unformattedName) {
                    case "Gemstone Mixture":
                        cinqdt1Mod.newModConfig.setInteger("bundleTracker", "loot", "gemstoneMixture", cinqdt1Mod.newModConfig.getInteger("bundleTracker", "loot", "gemstoneMixture") + 1);
                        cinqdt1Mod.newModConfig.saveConfig();
                        countedUUIDS.add(armorStandUUID);
                        break;
                    case "Recall Potion":
                        cinqdt1Mod.newModConfig.setInteger("bundleTracker", "loot", "recallPotion", cinqdt1Mod.newModConfig.getInteger("bundleTracker", "loot", "recallPotion") + 1);
                        cinqdt1Mod.newModConfig.saveConfig();
                        countedUUIDS.add(armorStandUUID);
                        break;
                    case "Prehistoric Egg":
                        cinqdt1Mod.newModConfig.setInteger("bundleTracker", "loot", "prehistoricEgg", cinqdt1Mod.newModConfig.getInteger("bundleTracker", "loot", "prehistoricEgg") + 1);
                        cinqdt1Mod.newModConfig.saveConfig();
                        countedUUIDS.add(armorStandUUID);
                        break;
                    case "Jaderald":
                        cinqdt1Mod.newModConfig.setInteger("bundleTracker", "loot", "jaderald", cinqdt1Mod.newModConfig.getInteger("bundleTracker", "loot", "jaderald") + 1);
                        cinqdt1Mod.newModConfig.saveConfig();
                        countedUUIDS.add(armorStandUUID);
                        break;
                    case "Divan's Alloy":
                        cinqdt1Mod.newModConfig.setInteger("bundleTracker", "loot", "divanAlloy", cinqdt1Mod.newModConfig.getInteger("bundleTracker", "loot", "divanAlloy") + 1);
                        cinqdt1Mod.newModConfig.saveConfig();
                        countedUUIDS.add(armorStandUUID);
                        break;
                    case "Quick Claw":
                        cinqdt1Mod.newModConfig.setInteger("bundleTracker", "loot", "quickClaw", cinqdt1Mod.newModConfig.getInteger("bundleTracker", "loot", "quickClaw") + 1);
                        cinqdt1Mod.newModConfig.saveConfig();
                        countedUUIDS.add(armorStandUUID);
                }
            } else if (handSlot != null) {
                String unformattedName = StringUtils.stripControlCodes(handSlot.getDisplayName());
                switch (unformattedName) {
                    case "Pickonimbus 2000":
                        cinqdt1Mod.newModConfig.setInteger("bundleTracker", "loot", "pickonimbus2000", cinqdt1Mod.newModConfig.getInteger("bundleTracker", "loot", "pickonimbus2000") + 1);
                        cinqdt1Mod.newModConfig.saveConfig();
                        countedUUIDS.add(armorStandUUID);
                        break;
                    case "Enchanted Book":
                        cinqdt1Mod.newModConfig.setInteger("bundleTracker", "loot", "fortuneIV", cinqdt1Mod.newModConfig.getInteger("bundleTracker", "loot", "fortuneIV") + 1);
                        cinqdt1Mod.newModConfig.saveConfig();
                        countedUUIDS.add(armorStandUUID);
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @SubscribeEvent
    public void renderPlayerInfo(RenderOverlay event) {
        if(!ModConfiguration.bundleTrackerState) return;
        try{
            List<String> bundleTrackerText = new ArrayList<>();
            bundleTrackerText.add(EnumChatFormatting.YELLOW + "" + EnumChatFormatting.BOLD + "Crystal Loot Bundle\n" +
                    EnumChatFormatting.GOLD + "Bob-omb :\n" +
                    EnumChatFormatting.GOLD + "Pickonimbus 2000:\n" +
                    EnumChatFormatting.GOLD + "Prehistoric Egg:\n" +
                    EnumChatFormatting.GOLD + "Divan's Fragment:\n" +
                    EnumChatFormatting.GOLD + "Recall Potion:\n" +
                    EnumChatFormatting.GOLD + "Jaderald:\n" +
                    EnumChatFormatting.GOLD + "Divan's Alloy:\n" +
                    EnumChatFormatting.GOLD + "Fortune IV:\n" +
                    EnumChatFormatting.GOLD + "Quick Claw:\n" +
                    EnumChatFormatting.GOLD + "Gemstone Mixture:\n" +
                    EnumChatFormatting.GOLD + "Bundle Runs:");
            bundleTrackerText.add("\n" +
                    EnumChatFormatting.GOLD + cinqdt1Mod.newModConfig.getInteger("bundleTracker", "loot", "bobomb") + "\n" +
                    EnumChatFormatting.GOLD + cinqdt1Mod.newModConfig.getInteger("bundleTracker", "loot", "pickonimbus2000") + "\n" +
                    EnumChatFormatting.GOLD + cinqdt1Mod.newModConfig.getInteger("bundleTracker", "loot", "prehistoricEgg") + "\n" +
                    EnumChatFormatting.GOLD + cinqdt1Mod.newModConfig.getInteger("bundleTracker", "loot", "divanFragment") + "\n" +
                    EnumChatFormatting.GOLD + cinqdt1Mod.newModConfig.getInteger("bundleTracker", "loot", "recallPotion") + "\n" +
                    EnumChatFormatting.GOLD + cinqdt1Mod.newModConfig.getInteger("bundleTracker", "loot", "jaderald") + "\n" +
                    EnumChatFormatting.GOLD + cinqdt1Mod.newModConfig.getInteger("bundleTracker", "loot", "divanAlloy") + "\n" +
                    EnumChatFormatting.GOLD + cinqdt1Mod.newModConfig.getInteger("bundleTracker", "loot", "fortuneIV") + "\n" +
                    EnumChatFormatting.GOLD + cinqdt1Mod.newModConfig.getInteger("bundleTracker", "loot", "quickClaw") + "\n" +
                    EnumChatFormatting.GOLD + cinqdt1Mod.newModConfig.getInteger("bundleTracker", "loot", "gemstoneMixture") + "\n" +
                    EnumChatFormatting.GOLD + cinqdt1Mod.newModConfig.getInteger("bundleTracker", "runs", "bundleNumber"));
            RenderUtils.renderOverlay(cinqdt1Mod.newModConfig.getInteger("bundleTracker", "position", "x"), cinqdt1Mod.newModConfig.getInteger("bundleTracker", "position", "y"), cinqdt1Mod.newModConfig.getFloat("bundleTracker", "position", "scale"), null, 0, 0, bundleTrackerText, 0);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @SubscribeEvent
    public void onWorldLoad(WorldEvent.Load event){
        countedUUIDS.clear();
    }

    @SubscribeEvent
    public void onChat(ClientChatReceivedEvent event){
        String message = StringUtils.stripControlCodes(event.message.getUnformattedText());
        if (!Utils.inNucleus) return;
        if (event.type == 2) return;
        if (message.contains(":")) return;

        if(!message.equals("  You've earned a Crystal Loot Bundle!")) return;
        try {
            cinqdt1Mod.newModConfig.setInteger("bundleTracker", "runs", "bundleNumber", cinqdt1Mod.newModConfig.getInteger("bundleTracker", "runs", "bundleNumber") + 1);
            cinqdt1Mod.newModConfig.saveConfig();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
