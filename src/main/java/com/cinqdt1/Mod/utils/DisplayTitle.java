package com.cinqdt1.Mod.utils;

import com.cinqdt1.Mod.events.RenderOverlay;
import com.cinqdt1.Mod.handlers.TitleRender;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class DisplayTitle {
	
	private static String textToDisplay;
	private static int timeToDisplay;
	private static double startTime = 0;
	
	private static boolean display = false;
	
	public static void displayTitle(String text, int sec) {
		textToDisplay = text;
		timeToDisplay = sec;
		startTime = System.currentTimeMillis() / 1000;
		display = true;
	}
	
	@SubscribeEvent
    public void renderPlayerInfo(RenderOverlay event) {
		if(display) {
			if(System.currentTimeMillis() / 1000 == startTime + timeToDisplay) {
				display = false;
			}else {
				TitleRender.drawTitle(textToDisplay);
			}
		}
	}
}
