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
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.monster.EntityGiantZombie;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.StringUtils;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

import java.util.ArrayList;
import java.util.List;

public class FragRunTracker {

	private static GiantType giantType = GiantType.UNDEFINED;
	private static GiantState giantState = GiantState.DEAD;
	private static BloodState bloodState = BloodState.CLOSED;
	private final int jollyPinkArmorColor = 16716947;
	@SubscribeEvent
	public void onInit(InitEvent event)
	{
		List<String> guiText = new ArrayList<>();
		guiText.add(EnumChatFormatting.GREEN + "" + EnumChatFormatting.BOLD +"Giants\n" +
				EnumChatFormatting.DARK_GREEN + "Diamante's:\n" +
				EnumChatFormatting.DARK_GREEN + "L.A.S.R.'s:\n" +
				EnumChatFormatting.DARK_GREEN + "Bigfoot's:\n" +
				EnumChatFormatting.DARK_GREEN + "Jolly Pink:\n" +
				EnumChatFormatting.GREEN + "" + EnumChatFormatting.BOLD + "Giants Drops\n" +
				EnumChatFormatting.DARK_GREEN + "Diamante's Handles:\n" +
				EnumChatFormatting.DARK_GREEN + "L.A.S.R.'s Eyes:\n" +
				EnumChatFormatting.DARK_GREEN + "Bigfoot's Lassos:\n" +
				EnumChatFormatting.DARK_GREEN + "Jolly Pink Rocks:");
		guiText.add(EnumChatFormatting.GREEN + "\n" +
				EnumChatFormatting.DARK_GREEN + "5\n" +
				EnumChatFormatting.DARK_GREEN + "3\n" +
				EnumChatFormatting.DARK_GREEN + "4\n" +
				EnumChatFormatting.DARK_GREEN + "2\n" +
				EnumChatFormatting.GREEN + "\n" +
				EnumChatFormatting.DARK_GREEN + "4\n" +
				EnumChatFormatting.DARK_GREEN + "2\n" +
				EnumChatFormatting.DARK_GREEN + "3\n" +
				EnumChatFormatting.DARK_GREEN + "2");
		guiText.add("\n" +
				EnumChatFormatting.YELLOW  + "35%\n" +
				EnumChatFormatting.YELLOW  + "21%\n" +
				EnumChatFormatting.YELLOW  + "28%\n" +
				EnumChatFormatting.YELLOW  + "14%\n" +
				"\n" +
				EnumChatFormatting.YELLOW  + "80%\n" +
				EnumChatFormatting.YELLOW  + "66%\n" +
				EnumChatFormatting.YELLOW  + "75%\n" +
				EnumChatFormatting.YELLOW  + "100%");
		GuiFeature guiInfo = new GuiFeature(cinqdt1Mod.guiEdit.getId(), 0, 0, 20, "fragRunTracker", guiText, null);
		cinqdt1Mod.guiEdit.registerFeature(guiInfo);
	}
	@SubscribeEvent
	public void onTick(TickEvent.ClientTickEvent event) {
		if(event.phase != TickEvent.Phase.START) return;
		if(Minecraft.getMinecraft().theWorld == null) return;
		if(!Utils.inF7) return;
		List<Entity> listWorldEntity = Minecraft.getMinecraft().theWorld.getLoadedEntityList();
		for(Entity entity : listWorldEntity) {
			if (giantState == GiantState.DEAD && giantType == GiantType.UNDEFINED && bloodState == BloodState.OPENED) {
				if (!(entity instanceof EntityGiantZombie) || !entity.getName().contains("Giant")) continue;
				ItemStack chestplate = ((EntityGiantZombie) entity).getCurrentArmor(2);
				ItemStack leggings = ((EntityGiantZombie) entity).getCurrentArmor(1);
				ItemStack boots = ((EntityGiantZombie) entity).getCurrentArmor(0);
				if (chestplate != null && leggings != null) {
					Item cpItem = chestplate.getItem();
					if (cpItem == Items.diamond_chestplate) {
						Utils.sendModMessage(EnumChatFormatting.GRAY + "A Diamante's giant has spawned !");
						giantType = GiantType.DIAMANTE;
						giantState = GiantState.ALIVE;
						try {
							cinqdt1Mod.newModConfig.setInteger("fragRunTracker", "giant", "diamanteGiant", cinqdt1Mod.newModConfig.getInteger("fragRunTracker", "giant", "diamanteGiant") + 1);
							cinqdt1Mod.newModConfig.saveConfig();
						}catch (Exception e){
							e.printStackTrace();
						}
					} else {
						int colorValue = ((ItemArmor) cpItem).getColor(chestplate);
						if (colorValue == jollyPinkArmorColor) {
							Utils.sendModMessage(EnumChatFormatting.GRAY + "A Jolly Pink giant has spawned !");
							giantType = GiantType.JOLLYPINK;
							giantState = GiantState.ALIVE;
							try {
								cinqdt1Mod.newModConfig.setInteger("fragRunTracker", "giant", "jollyPinkGiant", cinqdt1Mod.newModConfig.getInteger("fragRunTracker", "giant", "jollyPinkGiant") + 1);
								cinqdt1Mod.newModConfig.saveConfig();
							}catch (Exception e){
								e.printStackTrace();
							}
						} else {
							Utils.sendModMessage(EnumChatFormatting.GRAY + "A L.A.S.R. giant has spawned !");
							giantType = GiantType.LASR;
							giantState = GiantState.ALIVE;
							try {
								cinqdt1Mod.newModConfig.setInteger("fragRunTracker", "giant", "LASRGiant", cinqdt1Mod.newModConfig.getInteger("fragRunTracker", "giant", "LASRGiant") + 1);
								cinqdt1Mod.newModConfig.saveConfig();
							}catch (Exception e){
								e.printStackTrace();
							}
						}
					}
				} else if (boots != null) {
					Utils.sendModMessage(EnumChatFormatting.GRAY + "A Bigfoot giant has spawned !");
					giantType = GiantType.BIGFOOT;
					giantState = GiantState.ALIVE;
					try {
						cinqdt1Mod.newModConfig.setInteger("fragRunTracker", "giant", "bigFootGiant", cinqdt1Mod.newModConfig.getInteger("fragRunTracker", "giant", "bigFootGiant") + 1);
						cinqdt1Mod.newModConfig.saveConfig();
					}catch (Exception e){
						e.printStackTrace();
					}
				}
			} else if (giantState == GiantState.DEAD && giantType != GiantType.UNDEFINED && bloodState == BloodState.OPENED) {
				if (!(entity instanceof EntityItem)) continue;
				Item itemType = ((EntityItem) entity).getEntityItem().getItem();
				String entityName = StringUtils.stripControlCodes(((EntityItem) entity).getEntityItem().getDisplayName());
				if (itemType == Items.diamond_horse_armor && giantType == GiantType.DIAMANTE && entityName.equals("Diamante's Handle")) {
					Utils.sendModMessage(EnumChatFormatting.GRAY + "You have obtained a " + EnumChatFormatting.DARK_PURPLE + "" + EnumChatFormatting.BOLD + "DIAMANTE'S HANDLE " + EnumChatFormatting.RESET + "" + EnumChatFormatting.GRAY + "from the giant !");
					try {
						cinqdt1Mod.newModConfig.setInteger("fragRunTracker", "loot", "diamanteItem", cinqdt1Mod.newModConfig.getInteger("fragRunTracker", "loot", "diamanteItem") + 1);
						cinqdt1Mod.newModConfig.saveConfig();
					}catch (Exception e){
						e.printStackTrace();
					}
					giantType = GiantType.UNDEFINED;
				} else if (itemType == Items.ender_eye && giantType == GiantType.LASR && entityName.equals("L.A.S.R.'s Eye")) {
					Utils.sendModMessage(EnumChatFormatting.GRAY + "You have obtained a " + EnumChatFormatting.DARK_PURPLE + "" + EnumChatFormatting.BOLD + "L.A.S.R.'S EYE " + EnumChatFormatting.RESET + "" + EnumChatFormatting.GRAY + "from the giant !");
					try {
						cinqdt1Mod.newModConfig.setInteger("fragRunTracker", "loot", "LASRItem", cinqdt1Mod.newModConfig.getInteger("fragRunTracker", "loot", "LASRItem") + 1);
						cinqdt1Mod.newModConfig.saveConfig();
					}catch (Exception e){
						e.printStackTrace();
					}
					giantType = GiantType.UNDEFINED;
				} else if (itemType == Items.lead && giantType == GiantType.BIGFOOT && entityName.equals("Bigfoot's Lasso")) {
					Utils.sendModMessage(EnumChatFormatting.GRAY + "You have obtained a " + EnumChatFormatting.DARK_PURPLE + "" + EnumChatFormatting.BOLD + "BIGFOOT'S LASSO " + EnumChatFormatting.RESET + "" + EnumChatFormatting.GRAY + "from the giant !");
					try {
						cinqdt1Mod.newModConfig.setInteger("fragRunTracker", "loot", "bigFootItem", cinqdt1Mod.newModConfig.getInteger("fragRunTracker", "loot", "bigFootItem") + 1);
						cinqdt1Mod.newModConfig.saveConfig();
					}catch (Exception e){
						e.printStackTrace();
					}
					giantType = GiantType.UNDEFINED;
				} else if (itemType == Items.firework_charge && giantType == GiantType.JOLLYPINK && entityName.equals("Jolly Pink Rock")) {
					Utils.sendModMessage(EnumChatFormatting.GRAY + "You have obtained a " + EnumChatFormatting.DARK_PURPLE + "" + EnumChatFormatting.BOLD + "JOLLY PINK ROCK " + EnumChatFormatting.RESET + "" + EnumChatFormatting.GRAY + "from the giant !");
					try {
						cinqdt1Mod.newModConfig.setInteger("fragRunTracker", "loot", "jollyPinkItem", cinqdt1Mod.newModConfig.getInteger("fragRunTracker", "loot", "jollyPinkItem") + 1);
						cinqdt1Mod.newModConfig.saveConfig();
					}catch (Exception e){
						e.printStackTrace();
					}
					giantType = GiantType.UNDEFINED;
				}
			}
		}
	}

	@SubscribeEvent
	public void renderPlayerInfo(RenderOverlay event) {
		if (!ModConfiguration.fragRunTrackerForF7) return;
		try {
			int diamanteGiant = cinqdt1Mod.newModConfig.getInteger("fragRunTracker", "giant", "diamanteGiant");
			int LASRGiant = cinqdt1Mod.newModConfig.getInteger("fragRunTracker", "giant", "LASRGiant");
			int bigFootGiant = cinqdt1Mod.newModConfig.getInteger("fragRunTracker", "giant", "bigFootGiant");
			int jollyPinkGiant = cinqdt1Mod.newModConfig.getInteger("fragRunTracker", "giant", "jollyPinkGiant");

			int diamanteItem = cinqdt1Mod.newModConfig.getInteger("fragRunTracker", "loot", "diamanteItem");
			int LASRItem = cinqdt1Mod.newModConfig.getInteger("fragRunTracker", "loot", "LASRItem");
			int bigFootItem = cinqdt1Mod.newModConfig.getInteger("fragRunTracker", "loot", "bigFootItem");
			int jollyPinkItem = cinqdt1Mod.newModConfig.getInteger("fragRunTracker", "loot", "jollyPinkItem");

			List<String> fragRunTrackerText = new ArrayList<>();
			fragRunTrackerText.add(EnumChatFormatting.GREEN + "" + EnumChatFormatting.BOLD +"Giants\n" +
					EnumChatFormatting.DARK_GREEN + "Diamante's:\n" +
					EnumChatFormatting.DARK_GREEN + "L.A.S.R.'s:\n" +
					EnumChatFormatting.DARK_GREEN + "Bigfoot's:\n" +
					EnumChatFormatting.DARK_GREEN + "Jolly Pink:\n" +
					EnumChatFormatting.GREEN + "" + EnumChatFormatting.BOLD + "Giants Drops\n" +
					EnumChatFormatting.DARK_GREEN + "Diamante's Handles:\n" +
					EnumChatFormatting.DARK_GREEN + "L.A.S.R.'s Eyes:\n" +
					EnumChatFormatting.DARK_GREEN + "Bigfoot's Lassos:\n" +
					EnumChatFormatting.DARK_GREEN + "Jolly Pink Rocks:");
			fragRunTrackerText.add(EnumChatFormatting.GREEN + "\n" +
					EnumChatFormatting.DARK_GREEN + diamanteGiant + "\n" +
					EnumChatFormatting.DARK_GREEN + LASRGiant + "\n" +
					EnumChatFormatting.DARK_GREEN + bigFootGiant + "\n" +
					EnumChatFormatting.DARK_GREEN + jollyPinkGiant + "\n" +
					EnumChatFormatting.GREEN + "\n" +
					EnumChatFormatting.DARK_GREEN + diamanteItem +"\n" +
					EnumChatFormatting.DARK_GREEN + LASRItem +"\n" +
					EnumChatFormatting.DARK_GREEN + bigFootItem +"\n" +
					EnumChatFormatting.DARK_GREEN + jollyPinkItem);
			if(ModConfiguration.advancedStatsForFragRunTrackerForF7) {
				int totalGiants = diamanteGiant + LASRGiant + bigFootGiant + jollyPinkGiant;
				fragRunTrackerText.add("\n" +
						EnumChatFormatting.YELLOW + ((totalGiants > 0) ? diamanteGiant * 100 / totalGiants : 0) + "%\n" +
						EnumChatFormatting.YELLOW + ((totalGiants > 0) ? LASRGiant * 100 / totalGiants : 0) + "%\n" +
						EnumChatFormatting.YELLOW + ((totalGiants > 0) ? bigFootGiant * 100 / totalGiants : 0) + "%\n" +
						EnumChatFormatting.YELLOW + ((totalGiants > 0) ? jollyPinkGiant * 100 / totalGiants : 0) + "%\n" +
						"\n" +
						EnumChatFormatting.YELLOW + ((diamanteGiant > 0) ? diamanteItem * 100 / diamanteGiant : 0) + "%\n" +
						EnumChatFormatting.YELLOW + ((LASRGiant > 0) ? LASRItem * 100 / LASRGiant : 0) + "%\n" +
						EnumChatFormatting.YELLOW + ((bigFootGiant > 0) ? bigFootItem * 100 / bigFootGiant : 0) + "%\n" +
						EnumChatFormatting.YELLOW + ((jollyPinkGiant > 0) ? jollyPinkItem * 100 / jollyPinkGiant : 0) + "%");
			}
			RenderUtils.renderOverlay(cinqdt1Mod.newModConfig.getInteger("fragRunTracker", "position", "x"), cinqdt1Mod.newModConfig.getInteger("fragRunTracker", "position", "y"), cinqdt1Mod.newModConfig.getFloat("fragRunTracker", "position", "scale"), null, 0, 0, fragRunTrackerText, 20);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@SubscribeEvent
	public void onEntityDeath(LivingDeathEvent event) {
		if(!(event.entity instanceof EntityGiantZombie) && !event.entity.getName().contains("Giant")) return;
		giantState = GiantState.DEAD;
	}

	@SubscribeEvent
	public void onChat(ClientChatReceivedEvent event){
		String message = StringUtils.stripControlCodes(event.message.getUnformattedText());

		if (!Utils.inF7) return;
		if (event.type == 2) return;
		if (message.contains(":")) return;

		if(message.equals("The BLOOD DOOR has been opened!")) bloodState = BloodState.OPENED;
	}
	
	@SubscribeEvent
	public void onWorldChange(WorldEvent.Load event){
		giantType = GiantType.UNDEFINED;
		giantState = GiantState.DEAD;
		bloodState = BloodState.CLOSED;
	}
	enum GiantType {
		  UNDEFINED, DIAMANTE, JOLLYPINK, LASR, BIGFOOT
	}

	enum GiantState {
		DEAD, ALIVE
	}

	enum BloodState {
		OPENED, CLOSED
	}


}
