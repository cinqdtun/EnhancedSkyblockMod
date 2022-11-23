package cinqdt1.Mod.features;

import cinqdt1.Mod.cinqdt1Mod;
import cinqdt1.Mod.utils.Utils;
import gg.essential.api.EssentialAPI;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent;

public class FastOpenSettings {
	
	@SubscribeEvent
    public void onKey(InputEvent.KeyInputEvent event) {
        //if (!Utils.inSkyblock) return;
        if(cinqdt1Mod.keyBindings[0].isPressed()) {
			EssentialAPI.getGuiUtil().openScreen(cinqdt1Mod.instance.config.gui());
        }
	}
}
