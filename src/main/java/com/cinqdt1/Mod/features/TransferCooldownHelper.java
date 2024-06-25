package com.cinqdt1.Mod.features;

import com.cinqdt1.Mod.config.ModConfiguration;
import com.cinqdt1.Mod.events.GuiContainerClickEvent;
import com.cinqdt1.Mod.utils.Utils;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.ContainerChest;
import net.minecraft.util.EnumChatFormatting;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

public class TransferCooldownHelper {
    public static boolean isOnCooldown = false;
    public static boolean isDelayed = false;
    public static long cooldownStart = 0;

    public static ClickInfos lastClick = null;

    @SubscribeEvent
    public void onTick(TickEvent.ClientTickEvent event){
        if (event.phase != TickEvent.Phase.START) return;
        if (!Utils.inSkyblock) return;
        if (System.currentTimeMillis() - cooldownStart > 3001 && isOnCooldown) {
            isOnCooldown = false;
            if (isDelayed) {
                isDelayed = false;
                if(((ContainerChest) Minecraft.getMinecraft().thePlayer.openContainer).getLowerChestInventory().getDisplayName().getUnformattedText().trim().startsWith("SkyBlock Hub Selector") && lastClick != null) {
                    Minecraft.getMinecraft().playerController.windowClick(lastClick.windowId, lastClick.slotId, lastClick.mouseButtonClicked, lastClick.mode, Minecraft.getMinecraft().thePlayer);
                    lastClick = null;
                }
            }
        }
    }

    @SubscribeEvent
    public void onLoadWorld(WorldEvent.Load event) {
        if (!Utils.inSkyblock) return;
        isOnCooldown = true;
        cooldownStart = System.currentTimeMillis();
    }

    @SubscribeEvent
    public void onTooltip(ItemTooltipEvent event){
        if (!Utils.inSkyblock) return;
        if(!ModConfiguration.hubTransferCooldownState) return;
        if (Minecraft.getMinecraft().thePlayer.openContainer == null) return;
        //if (((ContainerChest) Minecraft.getMinecraft().thePlayer.openContainer.inventorySlots).getLowerChestInventory().getDisplayName().getUnformattedText().trim().equals("Skyblock Hub Selector")) return;
        if (Block.getBlockFromItem(event.itemStack.getItem()) != Blocks.quartz_block) return;
        long timElapsed = System.currentTimeMillis() - cooldownStart;
        event.toolTip.add((isOnCooldown && timElapsed < 3000) ? (isDelayed) ? EnumChatFormatting.GOLD + "Transfer delayed in " + Math.round((3000 - timElapsed) / 10.0) / 100.0 + "s" : EnumChatFormatting.RED + "Transfer cooldown in " + Math.round((3000 - timElapsed) / 10.0) / 100.0 + "s" : EnumChatFormatting.GREEN + "Transfer cooldown ready");
    }

    @SubscribeEvent
    public void onContainerClick(GuiContainerClickEvent event) {
        if (!Utils.inSkyblock) return;
        if(!ModConfiguration.hubTransferCooldownState) return;
        if (event.slotIn.inventory.getDisplayName().getUnformattedText().trim().startsWith("SkyBlock Hub Selector")) {
            if (Block.getBlockFromItem(event.slotIn.getStack().getItem()) != Blocks.quartz_block) return;
            if (isOnCooldown) {
                isDelayed = !isDelayed;
                lastClick = new ClickInfos(event.windowId, event.slotId, event.mouseButtonClicked, event.mode);
                event.setCanceled(true);
            }
        }
    }

    private static class ClickInfos {
        public int windowId;
        public int slotId;
        public int mouseButtonClicked;
        public int mode;
        public ClickInfos(int windowId, int slotId, int mouseButtonClicked, int mode) {
            this.windowId = windowId;
            this.slotId = slotId;
            this.mouseButtonClicked = mouseButtonClicked;
            this.mode = mode;
        }
    }
}

