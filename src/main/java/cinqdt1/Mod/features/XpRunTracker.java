package cinqdt1.Mod.features;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import cinqdt1.Mod.cinqdt1Mod;
import cinqdt1.Mod.utils.RenderUtils;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import cinqdt1.Mod.config.ModConfiguration;
import cinqdt1.Mod.events.DungeonEvent;
import cinqdt1.Mod.events.RenderOverlay;
import cinqdt1.Mod.handlers.APIHandler;
import cinqdt1.Mod.utils.Utils;
import net.minecraft.client.Minecraft;
import net.minecraft.util.EnumChatFormatting;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import static cinqdt1.Mod.utils.Utils.formatString;

public class XpRunTracker {

	private String petName = null;
	private int petXpStart = 0;
	private int totalPetXp = 0;
	private PetType petType = PetType.LVL100;
	public static final ArrayList<Integer> xpNeededToMaxPet = new ArrayList<>(Arrays.asList(25353230, 210255385));
	public static final Minecraft mc = Minecraft.getMinecraft();
	public static boolean countRun = true;
	enum PetType {
		LVL100, LVL200
	}


	@SubscribeEvent
	public void onExitDungeon(DungeonEvent.Leave event) {
		if(event.floor != 6) return;
		if(!ModConfiguration.xpRunF6 || !countRun) return;
		new Thread(() -> {
			try {
				String UUID = Utils.getFormattedPlayerUUID(mc.thePlayer);
				JsonObject response = getProfileJson(UUID);
				if (response == null) return;
				String currentPetName = getPetName(response, UUID);
				double currentPetXp = getActivePetXp(response, UUID);
				if (currentPetName == null || currentPetXp < 0 || !currentPetName.equals(petName)) return;
				cinqdt1Mod.newModConfig.setInteger("xpRunTracker", "stats", "runs", cinqdt1Mod.newModConfig.getInteger("xpRunTracker", "stats", "runs") + 1);
				cinqdt1Mod.newModConfig.setInteger("xpRunTracker", "stats", "totalXp", cinqdt1Mod.newModConfig.getInteger("xpRunTracker", "stats", "totalXp") + ((int) currentPetXp - petXpStart));
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
			JsonObject response = getProfileJson(Utils.getFormattedPlayerUUID(mc.thePlayer));
			if(response == null) return;
			String currentPetName = getPetName(response, UUID);
			double currentPetXp = getActivePetXp(response, UUID) ;
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

	private JsonObject getProfileJson(String UUID){
		String latestProfile = APIHandler.getLatestProfileID(UUID, ModConfiguration.apiKey);
		if (latestProfile == null) return null;
		JsonObject response = APIHandler.getResponse("https://api.hypixel.net/skyblock/profile?profile=" + latestProfile + "&key=" + ModConfiguration.apiKey, true);
		if (!response.get("success").getAsBoolean()) return null;
		return response;
	}

	private String getPetName(JsonObject profile, String UUID){
		JsonArray petsArray = profile.get("profile").getAsJsonObject().get("members").getAsJsonObject().get(UUID).getAsJsonObject().get("pets").getAsJsonArray();
		for (JsonElement pet : petsArray) {
			JsonObject petJSON = pet.getAsJsonObject();
			if(petJSON.get("active").getAsBoolean()) {
				return petJSON.get("type").getAsString();
			}
		}
		return null;
	}

	private double getActivePetXp(JsonObject profile, String UUID){
		JsonArray petsArray = profile.get("profile").getAsJsonObject().get("members").getAsJsonObject().get(UUID).getAsJsonObject().get("pets").getAsJsonArray();
		for (JsonElement pet : petsArray) {
			JsonObject petJSON = pet.getAsJsonObject();
			if(petJSON.get("active").getAsBoolean()) {
				return petJSON.get("exp").getAsDouble();
			}
		}
		return -1;
	}
}
