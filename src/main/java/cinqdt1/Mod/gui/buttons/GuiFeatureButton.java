package cinqdt1.Mod.gui.buttons;

import cinqdt1.Mod.gui.EditLocations;
import cinqdt1.Mod.utils.RenderUtils;
import cinqdt1.Mod.utils.Utils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.SoundHandler;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.item.ItemStack;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

public class GuiFeatureButton extends GuiButton {
	protected float scale;
	private final ItemStack renderItem;
	private final int tWidth;
	private final int xTextOffset;
	private final int yTextOffset;
	private final List<String> text;
	private final List<Integer> spaceBetweenLines;
	private final int buttonOffset = 3;
	public final EditLocations.FeatureButton feature;
	private final int defaultButtonColor = Utils.getIntColorFromRGBAColor(255,255,255,100);
	private final int hoveredButtonColor = Utils.getIntColorFromRGBAColor(255,255,255,40);

	public GuiFeatureButton(int x, int y, float scale, @Nullable ItemStack renderItem, int xTextOffset, int yTextOffset, @NotNull List<String> text, int linesOffset, EditLocations.FeatureButton feature) {
		super(0, x, y, text.get(0));
		this.scale = scale;
		this.renderItem = renderItem;
		this.tWidth = renderItem != null ? 16 : 0;
		this.xTextOffset = xTextOffset;
		this.yTextOffset = yTextOffset;
		this.text = text;
		float fontHeight = Minecraft.getMinecraft().fontRendererObj.FONT_HEIGHT * scale;
		this.spaceBetweenLines = new ArrayList<>();
		this.spaceBetweenLines.add(0);
		this.height = 0;
		this.width = 0;
		this.feature = feature;

		float highestLine = 0;
		int i = 1;
		for(String line : text){
			String[] splitText = line.split("\n");
			int longestLineText = 0;
			if(highestLine < splitText.length * fontHeight) highestLine = splitText.length * fontHeight;
			for(String s : splitText){
				int stringLength = Utils.getStringWidth(s);
				if(stringLength > longestLineText) longestLineText = stringLength;
			}
			this.spaceBetweenLines.add(longestLineText + linesOffset + spaceBetweenLines.get(i - 1));
			this.width += longestLineText * scale;
			i++;
		}
		if(renderItem != null) this.width += tWidth * scale;
		this.width += (2 * buttonOffset + (text.size() - 1) * linesOffset + xTextOffset) * scale;
		this.height += Math.max(tWidth * scale, highestLine);
		this.height += (2 * buttonOffset + yTextOffset) * scale;
	}
	
	@Override
	public void drawButton(Minecraft mc, int mouseX, int mouseY) {
		this.hovered = mouseX >= xPosition && mouseY >= yPosition && mouseX < xPosition + width && mouseY < yPosition + height && !(mouseX >= xPosition + width - 3 && mouseY >= yPosition + height - 3);
		int color = isMouseOver() ? hoveredButtonColor : defaultButtonColor;
		drawRect((int) (xPosition - (scale * 2)), (int) (yPosition - (scale * 2)), xPosition + width, yPosition + height, color);
		if(renderItem != null){
			RenderUtils.renderItem(renderItem, xPosition + buttonOffset * scale, yPosition + buttonOffset * scale, scale);
		}
		int i = 0;
		for(String line : text){
			RenderUtils.renderText(Minecraft.getMinecraft(), line, xPosition + (buttonOffset + tWidth + xTextOffset + spaceBetweenLines.get(i)) * scale, yPosition + (buttonOffset + yTextOffset) * scale, scale, true);
			i++;
		}
	}
	
	@Override
	public void playPressSound(SoundHandler soundHandler) {}

	public float getScale(){
		return scale;
	}

	public float getNewScaleWithX(int xMoved){
		return (float)(width + xMoved) / (width / scale);
	}


}