package com.cinqdt1.Mod.events;

import com.cinqdt1.Mod.core.handlers.PacketHandler;
import com.cinqdt1.Mod.gui.GuiEdit;
import com.cinqdt1.Mod.utils.Utils;
import gg.essential.vigilance.gui.SettingsGui;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.inventory.GuiChest;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ContainerChest;
import net.minecraft.inventory.Slot;
import net.minecraft.network.play.client.C01PacketChatMessage;
import net.minecraft.util.StringUtils;
import net.minecraftforge.client.GuiIngameForge;
import net.minecraftforge.client.event.GuiScreenEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.Phase;
import net.minecraftforge.fml.common.network.FMLNetworkEvent;

import java.util.List;

public class Event {
	public static Minecraft mc = Minecraft.getMinecraft();
    private int tickAmount = 1;
    private boolean bobberState = false;
    
	@SubscribeEvent(priority = EventPriority.HIGHEST)
	public void onTick(TickEvent.ClientTickEvent event) {
		if (event.phase != Phase.START) return;
	    tickAmount++;
	    if(mc.thePlayer != null) {
	    	if(bobberState && mc.thePlayer.fishEntity == null) {
				bobberState = false;
				MinecraftForge.EVENT_BUS.post(new FishingEvent.Stop(mc.thePlayer));
    		}else if(!bobberState && mc.thePlayer.fishEntity != null) {
				bobberState = true;
				MinecraftForge.EVENT_BUS.post(new FishingEvent.Start(mc.thePlayer));
    		}
	    }
		if(tickAmount % 20 == 0 && mc.thePlayer != null){
			boolean beforeCheckF6 = Utils.inF6;
			Utils.checkForSkyblock();
			Utils.checkForDungeon();
			Utils.checkForF3();
			Utils.checkForF6();
			Utils.checkForF7();
			Utils.checkForCrimsonIsle();
			Utils.checkForNucleus();
			Utils.checkForDivan();
			boolean AfterCheckF6 = Utils.inF6;
			if(!beforeCheckF6 && AfterCheckF6) {
				MinecraftForge.EVENT_BUS.post(new DungeonEvent.Entered(6));
			}else if(beforeCheckF6 && !AfterCheckF6) {
				MinecraftForge.EVENT_BUS.post(new DungeonEvent.Leave(6));
			}
		}
	}
	
	@SubscribeEvent
    public void onGuiRender(GuiScreenEvent.BackgroundDrawnEvent event) {
        if (!Utils.inSkyblock) return;
        if (event.gui instanceof GuiChest) {
            GuiChest inventory = (GuiChest) event.gui;
            Container containerChest = inventory.inventorySlots;
            if (containerChest instanceof ContainerChest) {
                List<Slot> invSlots = inventory.inventorySlots.inventorySlots;
                String displayName = ((ContainerChest) containerChest).getLowerChestInventory().getDisplayName().getUnformattedText().trim();
                int chestSize = inventory.inventorySlots.inventorySlots.size();
				int windowId = containerChest.windowId;
                MinecraftForge.EVENT_BUS.post(new GuiChestBackgroundDrawnEvent(displayName, chestSize, invSlots, event.gui, windowId));
            }
        }
    }

	
	 @SubscribeEvent
	 public void renderPlayerInfo(final RenderGameOverlayEvent.Post event) {
		 if (!(Minecraft.getMinecraft().ingameGUI instanceof GuiIngameForge)) return;
		 if (event.type != RenderGameOverlayEvent.ElementType.EXPERIENCE && event.type != RenderGameOverlayEvent.ElementType.JUMPBAR) return;
	     if (Minecraft.getMinecraft().currentScreen instanceof SettingsGui) return;
	     if (Minecraft.getMinecraft().currentScreen instanceof GuiEdit) return;
	     MinecraftForge.EVENT_BUS.post(new RenderOverlay());
	 }

	@SubscribeEvent
	public void onServerConnect(FMLNetworkEvent.ClientConnectedToServerEvent event) {
		event.manager.channel().pipeline().addBefore("packet_handler", "cdt_mod_packet_handler", new PacketHandler());
	}

	@SubscribeEvent(priority = EventPriority.HIGHEST)
	public void onPacket(PacketEvent.Sent event){
		if(!(event.packet instanceof C01PacketChatMessage)) return;
		String message = StringUtils.stripControlCodes(((C01PacketChatMessage)event.packet).getMessage());
		if(MinecraftForge.EVENT_BUS.post(new ChatEvent.Sent(message))) event.setCanceled(true);
	}
}
