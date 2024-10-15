package com.cinqdt1.Mod.utils;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.item.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.lwjgl.opengl.GL11;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

public class RenderUtils {
    public static final int DEFAULT_WIDTH = 640;
    public static void renderText(Minecraft mc, String text, float x, float y, double scale, boolean shadow) {
        double scaleReset = Math.pow(scale, -1);
        GL11.glScaled(scale, scale, scale);
        for (String line : text.split("\n")) {
            mc.fontRendererObj.drawString(line, (float) (x / scale), (float) (y / scale), 0xFFFFFF, shadow);
            y += mc.fontRendererObj.FONT_HEIGHT * scale;
        }
        GL11.glScaled(scaleReset, scaleReset, scaleReset);
        GlStateManager.color(1, 1, 1, 1);
    }
    /* Taken from Skyblock Addons
     * https://github.com/BiscuitDevelopment/SkyblockAddons
     */
    public static void renderItem(ItemStack item, float x, float y, float scale) {
        double scaleReset = Math.pow(scale, -1);
        GL11.glScaled(scale, scale, scale);
        GlStateManager.enableRescaleNormal();
        RenderHelper.enableGUIStandardItemLighting();
        GlStateManager.enableDepth();

        GlStateManager.pushMatrix();
        GlStateManager.translate(x / scale, y / scale, 0);
        Minecraft.getMinecraft().getRenderItem().renderItemIntoGUI(item, 0, 0);
        GlStateManager.popMatrix();

        RenderHelper.disableStandardItemLighting();
        GlStateManager.disableRescaleNormal();
        GL11.glScaled(scaleReset, scaleReset, scaleReset);
    }

    public static void renderOverlay(float x, float y, float scale, @Nullable ItemStack renderItem, int xTextOffset, int yTextOffset, @NotNull List<String> text, int linesOffset){
        List<Integer> spaceBetweenLines = new ArrayList<>();
        int tWidth = renderItem != null ? 16 : 0;

        //x += 3 * scale;
        //y += 3 * scale;
        spaceBetweenLines.add(0);

        int i = 1;
        for(String line : text){
            String[] splitText = line.split("\n");
            int longestLineText = 0;
            for(String s : splitText){
                int stringLength = Utils.getStringWidth(s);
                if(stringLength > longestLineText) longestLineText = stringLength;
            }
            spaceBetweenLines.add(longestLineText + linesOffset + spaceBetweenLines.get(i - 1));
            i++;
        }

        if(renderItem != null){
            renderItem(renderItem, getScaledRatio(x + 3 * scale), getScaledRatio(y + 3 * scale), getScaledRatio(scale));
        }
        i = 0;
        for(String line : text){
            RenderUtils.renderText(Minecraft.getMinecraft(), line, getScaledRatio(x + (3 + tWidth + xTextOffset + spaceBetweenLines.get(i)) * scale), getScaledRatio(y + (3 + yTextOffset) * scale), getScaledRatio(scale), true);
            i++;
        }
    }
    public static int getUnscaledRatio(int value){
        ScaledResolution sr = new ScaledResolution(Minecraft.getMinecraft());
        return (int)((double)value / sr.getScaledWidth_double() * DEFAULT_WIDTH);
    }
    public static double getUnscaledRatio(double value){
        ScaledResolution sr = new ScaledResolution(Minecraft.getMinecraft());
        return value / sr.getScaledWidth_double() * DEFAULT_WIDTH;
    }
    public static int getScaledRatio(int value){
        ScaledResolution sr = new ScaledResolution(Minecraft.getMinecraft());
        return (int)((double)value / DEFAULT_WIDTH * sr.getScaledWidth_double());
    }
    public static float getScaledRatio(float value){
        ScaledResolution sr = new ScaledResolution(Minecraft.getMinecraft());
        return (float)((double)value / DEFAULT_WIDTH * sr.getScaledWidth_double());
    }
}
