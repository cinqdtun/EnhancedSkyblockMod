package cinqdt1.Mod.handlers;

import cinqdt1.Mod.utils.RenderUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;

public class TitleRender {
	public static void drawTitle(String text) {
		Minecraft mc = Minecraft.getMinecraft();
		ScaledResolution scaledResolution = new ScaledResolution(mc);
		
		int height = scaledResolution.getScaledHeight();
		int width = scaledResolution.getScaledWidth();
		int drawHeight = 0;
		String[] splitText = text.split("\n");
		for (String title : splitText) {
			int textLength = mc.fontRendererObj.getStringWidth(title);

			double scale = 4;
			if (textLength * scale > (width * 0.9F)) {
				scale = (width * 0.9F) / (float) textLength;
			}

			int titleX = (int) ((width / 2) - (textLength * scale / 2));
			int titleY = (int) ((height * 0.45) / scale) + (int) (drawHeight * scale);
			RenderUtils.renderText(mc, title, titleX, titleY, scale, true);
			drawHeight += mc.fontRendererObj.FONT_HEIGHT;
		}
	}
}
