package com.cinqdt1.Mod.gui.buttons;

import com.cinqdt1.Mod.gui.EditLocations;
import com.cinqdt1.Mod.utils.RenderUtils;
import com.cinqdt1.Mod.utils.Utils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.SoundHandler;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.item.ItemStack;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

public class GuiFeatureButton extends GuiButton {
	public final EditLocations.FeatureButton feature;
	private final ItemStack renderItem;
	private final int tWidth;
	private final int xTextOffset;
	private final int yTextOffset;
	private final List<String> text;
	private final List<Integer> spaceBetweenLines;
	private final int buttonOffset = 3;
	private final int defaultButtonColor = Utils.getIntColorFromRGBAColor(255,255,255,100);
	private final int hoveredButtonColor = Utils.getIntColorFromRGBAColor(255,255,255,40);
	protected float scale;
	private int baseWidth;

	public GuiFeatureButton(int x, int y, float scale, @Nullable ItemStack renderItem, int xTextOffset, int yTextOffset, @NotNull List<String> text, int linesOffset, EditLocations.FeatureButton feature) {
		super(0, RenderUtils.getScaledRatio(x), RenderUtils.getScaledRatio(y), text.get(0));
		this.scale = scale;
		this.renderItem = renderItem;
		this.tWidth = renderItem != null ? 16 : 0;
		this.xTextOffset = xTextOffset;
		this.yTextOffset = yTextOffset;
		this.text = text;
		float fontHeight = RenderUtils.getScaledRatio(Minecraft.getMinecraft().fontRendererObj.FONT_HEIGHT * scale);
		this.spaceBetweenLines = new ArrayList<>();
		this.spaceBetweenLines.add(0);
		this.baseWidth = 0;
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
			this.baseWidth += longestLineText;
			i++;
		}
		if(renderItem != null) this.baseWidth += tWidth;

		this.baseWidth += 2 * buttonOffset + (text.size() - 1) * linesOffset + xTextOffset;
		this.width = RenderUtils.getScaledRatio((int) (baseWidth * scale ));
		this.height += Math.max(RenderUtils.getScaledRatio(tWidth * scale), highestLine);
		this.height += RenderUtils.getScaledRatio((2 * buttonOffset + yTextOffset) * scale);
	}
	
	@Override
	public void drawButton(Minecraft mc, int mouseX, int mouseY) {
		this.hovered = mouseX >= xPosition && mouseY >= yPosition && mouseX < xPosition + width && mouseY < yPosition + height && !(mouseX >= xPosition + width - 3 && mouseY >= yPosition + height - 3);
		int color = isMouseOver() ? hoveredButtonColor : defaultButtonColor;
		drawRect(xPosition, yPosition, xPosition + width, yPosition + height, color);
		if(renderItem != null){
			RenderUtils.renderItem(renderItem, xPosition + RenderUtils.getScaledRatio(buttonOffset * scale), yPosition + RenderUtils.getScaledRatio(buttonOffset * scale), RenderUtils.getScaledRatio(scale));
		}
		int i = 0;
		for(String line : text){
			RenderUtils.renderText(Minecraft.getMinecraft(), line, (xPosition + RenderUtils.getScaledRatio(buttonOffset + tWidth + xTextOffset + spaceBetweenLines.get(i)) * scale), yPosition + RenderUtils.getScaledRatio((buttonOffset + yTextOffset) * scale), RenderUtils.getScaledRatio(scale), true);
			i++;
		}
	}
	
	@Override
	public void playPressSound(SoundHandler soundHandler) {}

	public float getScale(){
		return scale;
	}

	public float getNewScaleWithX(double xMoved){
		return (float)(baseWidth + xMoved) / (baseWidth / scale);
	}


}