package cinqdt1.Mod.features;

import cinqdt1.Mod.cinqdt1Mod;
import cinqdt1.Mod.events.FishingEvent;
import cinqdt1.Mod.events.RenderOverlay;
import cinqdt1.Mod.utils.RenderUtils;
import cinqdt1.Mod.utils.Utils;
import cinqdt1.Mod.config.ModConfiguration;
import net.minecraft.util.EnumChatFormatting;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.ArrayList;
import java.util.List;

public class BobberTimer {
	public boolean isPlayerFishing = false;
	public int startTimeFishing = 0;
	
	@SubscribeEvent
	public void startFishing(FishingEvent.Start event) {
		if(!Utils.inCrimsonIsle) return;
		isPlayerFishing = true;
		startTimeFishing = (int) (System.currentTimeMillis() / 1000);
	}
	
	@SubscribeEvent
	public void stopFishing(FishingEvent.Stop event) {
		if(!Utils.inCrimsonIsle) return;
		isPlayerFishing = false;
		startTimeFishing = 0;
	}
	
	@SubscribeEvent
    public void renderPlayerInfo(RenderOverlay event) {
        if (!ModConfiguration.bobberTimerOnCrimsonIsle) return;
		if(!Utils.inCrimsonIsle) return;
		if(!isPlayerFishing) return;
		List<String> bobberTimerText = new ArrayList<>();
		bobberTimerText.add(EnumChatFormatting.AQUA + "Bobber Time:");
		bobberTimerText.add(EnumChatFormatting.AQUA + "" + Utils.getTimeBetween(startTimeFishing, (int) (System.currentTimeMillis() / 1000)));
		try {
			RenderUtils.renderOverlay(cinqdt1Mod.newModConfig.getInteger("bobberTimer", "position", "x"), cinqdt1Mod.newModConfig.getInteger("bobberTimer", "position", "y"), cinqdt1Mod.newModConfig.getFloat("bobberTimer", "position", "scale"), null, 0, 0, bobberTimerText, 20);
		} catch (Exception e) {
			e.printStackTrace();
		}
    }
}
