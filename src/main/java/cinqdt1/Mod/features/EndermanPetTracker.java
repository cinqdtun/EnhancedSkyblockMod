package cinqdt1.Mod.features;

import cinqdt1.Mod.cinqdt1Mod;
import cinqdt1.Mod.utils.RenderUtils;
import cinqdt1.Mod.utils.Utils;
import cinqdt1.Mod.config.ModConfiguration;
import cinqdt1.Mod.events.RenderOverlay;
import cinqdt1.Mod.utils.DisplayTitle;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.StringUtils;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.ArrayList;
import java.util.List;

public class EndermanPetTracker {

	@SubscribeEvent(priority = EventPriority.HIGHEST)
    public void onChat(ClientChatReceivedEvent event) {
        String message = StringUtils.stripControlCodes(event.message.getUnformattedText());

        if (!Utils.inSkyblock) return;
        if (event.type == 2) return;
        if (message.contains(":")) return;
        
        if(message.startsWith("PET DROP! Enderman")) {
			try {
				String endermanDrop = event.message.getFormattedText();
				if (endermanDrop.contains(EnumChatFormatting.BLUE + "Enderman")) {
					//Rare
					cinqdt1Mod.newModConfig.setInteger("endermanPetTracker", "loot", "rareEndermanPet",cinqdt1Mod.newModConfig.getInteger("endermanPetTracker", "loot", "rareEndermanPet") + 1);
					cinqdt1Mod.newModConfig.saveConfig();
				} else if (endermanDrop.contains(EnumChatFormatting.DARK_PURPLE + "Enderman")) {
					//Epic
					cinqdt1Mod.newModConfig.setInteger("endermanPetTracker", "loot", "epicEndermanPet",cinqdt1Mod.newModConfig.getInteger("endermanPetTracker", "loot", "epicEndermanPet") + 1);
					cinqdt1Mod.newModConfig.saveConfig();
				} else if (endermanDrop.contains(EnumChatFormatting.GOLD + "Enderman")) {
					//Legendary
					cinqdt1Mod.newModConfig.setInteger("endermanPetTracker", "loot", "legendaryEndermanPet",cinqdt1Mod.newModConfig.getInteger("endermanPetTracker", "loot", "legendaryEndermanPet") + 1);
					cinqdt1Mod.newModConfig.saveConfig();
					DisplayTitle.displayTitle(EnumChatFormatting.GOLD + "ENDERMAN PET !", 5);
				} else {
					Utils.sendModMessage(EnumChatFormatting.RED + "The mod have encountred an error when he trying to determine what rarity of enderman you got !");
				}
			}catch (Exception e){
				e.printStackTrace();
			}
        }
	}
	
	@SubscribeEvent
	public void renderPlayerInfo(RenderOverlay event) {
		if(!ModConfiguration.showEndermanTrackerWhileInEnd) return;
		if(!Utils.isInScoreboard("The End")) return;
		try{
			List<String> endermanPetTrackerText = new ArrayList<>();
			endermanPetTrackerText.add(EnumChatFormatting.BLUE + "" + EnumChatFormatting.BOLD + "Rare\n" +
					EnumChatFormatting.BLUE+ "Enderman\n" +
					EnumChatFormatting.DARK_PURPLE + "" + EnumChatFormatting.BOLD + "Epic\n" +
					EnumChatFormatting.DARK_PURPLE + "Enderman:\n" +
					EnumChatFormatting.GOLD + "" + EnumChatFormatting.BOLD + "Legendary\n" +
					EnumChatFormatting.GOLD + "Enderman:");
			endermanPetTrackerText.add(EnumChatFormatting.BLUE + "\n" +
					EnumChatFormatting.BLUE + cinqdt1Mod.newModConfig.getInteger("endermanPetTracker", "loot", "rareEndermanPet") + "\n" +
					EnumChatFormatting.DARK_PURPLE + "\n" +
					EnumChatFormatting.DARK_PURPLE + cinqdt1Mod.newModConfig.getInteger("endermanPetTracker", "loot", "epicEndermanPet") + "\n" +
					EnumChatFormatting.GOLD + "\n" +
					EnumChatFormatting.GOLD + cinqdt1Mod.newModConfig.getInteger("endermanPetTracker", "loot", "legendaryEndermanPet"));
			RenderUtils.renderOverlay(cinqdt1Mod.newModConfig.getInteger("endermanPetTracker", "position", "x"), cinqdt1Mod.newModConfig.getInteger("endermanPetTracker", "position", "y"), cinqdt1Mod.newModConfig.getFloat("endermanPetTracker", "position", "scale"), null, 0, 0, endermanPetTrackerText, 20);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}