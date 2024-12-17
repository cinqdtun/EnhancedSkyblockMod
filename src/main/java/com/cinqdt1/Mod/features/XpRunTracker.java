package com.cinqdt1.Mod.features;

import com.cinqdt1.Mod.cinqdt1Mod;
import com.cinqdt1.Mod.config.ModConfiguration;
import com.cinqdt1.Mod.events.DungeonEvent;
import com.cinqdt1.Mod.events.InitEvent;
import com.cinqdt1.Mod.events.RenderOverlay;
import com.cinqdt1.Mod.gui.GuiFeature;
import com.cinqdt1.Mod.utils.ApiUtils;
import com.cinqdt1.Mod.utils.RenderUtils;
import com.cinqdt1.Mod.utils.Utils;
import com.google.gson.JsonObject;
import net.minecraft.client.Minecraft;
import net.minecraft.util.EnumChatFormatting;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class XpRunTracker {

	public static final ArrayList<Integer> xpNeededToMaxPet = new ArrayList<>(Arrays.asList(25353230, 210255385));
	public static final Minecraft mc = Minecraft.getMinecraft();
	public static boolean countRun = true;
	private String petName = null;
	private int petXpStart = 0;
	private int totalPetXp = 0;
	private PetType petType = PetType.LVL100;
	public void onInit(InitEvent event)
	{
		List<String> guiText = new ArrayList<>();
		guiText.add(EnumChatFormatting.DARK_GRAY + "" + EnumChatFormatting.BOLD + "Xp Runs\n" +
				EnumChatFormatting.GRAY + "Current pet:\n" +
				EnumChatFormatting.GRAY + "Average xp/run:\n" +
				EnumChatFormatting.GRAY + "Runs needed to lvl max:");
		guiText.add(EnumChatFormatting.DARK_GRAY + "\n" +
				EnumChatFormatting.GRAY + "Ender Dragon\n" +
				EnumChatFormatting.GRAY + "169 456\n" +
				EnumChatFormatting.GRAY + "98");
		GuiFeature guiInfo = new GuiFeature(cinqdt1Mod.guiEdit.getId(), 0, 0, 20, "xpRunTracker", guiText, null);
		cinqdt1Mod.guiEdit.registerFeature(guiInfo);
	}
	@SubscribeEvent
	public void onExitDungeon(DungeonEvent.Leave event) {
		if(event.floor != 6) return;
		if(!ModConfiguration.xpRunF6 || !countRun) return;
		new Thread(() -> {
			try {
				String UUID = Utils.getFormattedPlayerUUID(mc.thePlayer);
				JsonObject response = ApiUtils.getProfileJson(UUID, ModConfiguration.apiKey);
				if (response == null) return;
				String currentPetName = ApiUtils.getPetName(response, UUID);
				double currentPetXp = ApiUtils.getActivePetXp(response, UUID);
				if (currentPetName == null || currentPetXp < 0 || !currentPetName.equals(petName)) return;
				cinqdt1Mod.newModConfig.set("xpRunTracker", "stats", "runs", cinqdt1Mod.newModConfig.getInteger("xpRunTracker", "stats", "runs") + 1);
				cinqdt1Mod.newModConfig.set("xpRunTracker", "stats", "totalXp", cinqdt1Mod.newModConfig.getInteger("xpRunTracker", "stats", "totalXp") + ((int) currentPetXp - petXpStart));
				cinqdt1Mod.newModConfig.saveConfig();
				totalPetXp = (int) currentPetXp;
				petXpStart = 0;
			}catch (Exception e){
				e.printStackTrace();
			}
		}).start();
	}

	@SubscribeEvent
	public void onEnteredInDungeon(DungeonEvent.Entered event) {
		if(event.floor != 6) return;
		if(!ModConfiguration.xpRunF6) return;
		new Thread(() -> {
			String UUID = Utils.getFormattedPlayerUUID(mc.thePlayer);
			JsonObject response = ApiUtils.getProfileJson(Utils.getFormattedPlayerUUID(mc.thePlayer), ModConfiguration.apiKey);
			if(response == null) return;
			String currentPetName = ApiUtils.getPetName(response, UUID);
			double currentPetXp = ApiUtils.getActivePetXp(response, UUID) ;
			if(currentPetName == null || currentPetXp < 0) return;
			petName = currentPetName;
			petXpStart = (int)currentPetXp;
			totalPetXp = (int)currentPetXp;
			if(petName.equals("GOLDEN_DRAGON")){
				petType = PetType.LVL200;
			}else{
				petType = PetType.LVL100;
			}
		}).start();
		countRun = true;
	}
	
	@SubscribeEvent
	public void renderPlayerInfo(RenderOverlay event) {
		if(!ModConfiguration.xpRunF6) return;
		try{
			int runs = cinqdt1Mod.newModConfig.getInteger("xpRunTracker", "stats", "runs");
			int totalXp = cinqdt1Mod.newModConfig.getInteger("xpRunTracker", "stats", "totalXp");
			List<String> xpRunTrackerText = new ArrayList<>();
			xpRunTrackerText.add(EnumChatFormatting.DARK_GRAY + "" + EnumChatFormatting.BOLD + "Xp Runs\n" +
					EnumChatFormatting.GRAY + "Current pet:\n" +
					EnumChatFormatting.GRAY + "Average xp/run:\n" +
					EnumChatFormatting.GRAY + "Runs needed to lvl max:");
			xpRunTrackerText.add(EnumChatFormatting.DARK_GRAY + "\n" +
					EnumChatFormatting.GRAY + ((petName != null) ? Utils.formatString(petName) : "unknown") + "\n" +
					EnumChatFormatting.GRAY + ((runs > 0) ? totalXp / runs : 0) + "\n" +
					EnumChatFormatting.GRAY + ((runs > 0 && totalXp > 0 & petName != null) ?  ((petType == PetType.LVL200) ? xpNeededToMaxPet.get(1) - totalPetXp : xpNeededToMaxPet.get(0) - totalPetXp) / (totalXp / runs) : 0));
			RenderUtils.renderOverlay(cinqdt1Mod.newModConfig.getInteger("xpRunTracker", "position", "x"), cinqdt1Mod.newModConfig.getInteger("xpRunTracker", "position", "y"), cinqdt1Mod.newModConfig.getFloat("xpRunTracker", "position", "scale"), null,0 , 0, xpRunTrackerText, 20);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	enum PetType {
		LVL100, LVL200
	}
}
