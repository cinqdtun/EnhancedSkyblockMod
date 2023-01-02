package cinqdt1.Mod.utils;

import cinqdt1.Mod.core.Color;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.model.ModelBlaze;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MathHelper;
import org.jetbrains.annotations.NotNull;
import org.lwjgl.opengl.GL11;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

public class RenderUtils {
    public static final int DEFAULT_WIDTH = 640;
    public static final int DEFAULT_HEIGHT = 360;
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
        GlStateManager.depthFunc(GL11.GL_LEQUAL);
        Minecraft.getMinecraft().getRenderItem().renderItemIntoGUI(item, 0, 0);
        GlStateManager.popMatrix();
        //GlStateManager.depthFunc(GL11.GL_LEQUAL);
        RenderHelper.disableStandardItemLighting();
        GlStateManager.disableRescaleNormal();
        GL11.glScaled(scaleReset, scaleReset, scaleReset);
    }

    public static void renderOverlay(float x, float y, float scale, @Nullable ItemStack renderItem, int xTextOffset, int yTextOffset, @NotNull List<String> text, int linesOffset){
        List<Integer> spaceBetweenLines = new ArrayList<>();
        int tWidth = renderItem != null ? 16 : 0;

        x += 3 * scale;
        y += 3 * scale;
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
            renderItem(renderItem, getScaledWidthRatio(x + 3 * scale), getScaledWidthRatio(y + 3 * scale), getScaledWidthRatio(scale));
        }
        i = 0;
        for(String line : text){
            RenderUtils.renderText(Minecraft.getMinecraft(), line, getScaledWidthRatio(x + (3 + tWidth + xTextOffset + spaceBetweenLines.get(i)) * scale), getScaledWidthRatio(y + (3 + yTextOffset) * scale), getScaledWidthRatio(scale), true);
            i++;
        }
    }
    public static int getUnscaledWidthRatio(int value){
        ScaledResolution sr = new ScaledResolution(Minecraft.getMinecraft());
        return (int)((double)value / sr.getScaledWidth_double() * DEFAULT_WIDTH);
    }
    public static double getUnscaledWidthRatio(double value){
        ScaledResolution sr = new ScaledResolution(Minecraft.getMinecraft());
        return value / sr.getScaledWidth_double() * DEFAULT_WIDTH;
    }
    public static int getScaledWidthRatio(int value){
        ScaledResolution sr = new ScaledResolution(Minecraft.getMinecraft());
        return (int)((double)value / DEFAULT_WIDTH * sr.getScaledWidth_double());
    }
    public static float getScaledWidthRatio(float value){
        ScaledResolution sr = new ScaledResolution(Minecraft.getMinecraft());
        return (float)((double)value / DEFAULT_WIDTH * sr.getScaledWidth_double());
    }

    public static int getUnscaledHeightRatio(int value){
        ScaledResolution sr = new ScaledResolution(Minecraft.getMinecraft());
        return (int)((double)value / sr.getScaledHeight_double() * DEFAULT_HEIGHT);
    }
    public static double getUnscaledHeightRatio(double value){
        ScaledResolution sr = new ScaledResolution(Minecraft.getMinecraft());
        return value / sr.getScaledHeight_double() * DEFAULT_HEIGHT;
    }
    public static int getScaledHeightRatio(int value){
        ScaledResolution sr = new ScaledResolution(Minecraft.getMinecraft());
        return (int)((double)value / DEFAULT_HEIGHT * sr.getScaledHeight_double());
    }
    public static float getScaledHeightRatio(float value) {
        ScaledResolution sr = new ScaledResolution(Minecraft.getMinecraft());
        return (float) ((double) value / DEFAULT_HEIGHT * sr.getScaledHeight_double());
    }

    public static void drawRect(int x, int y, int width, int height, int borderThickness, Color color){
        Tessellator tessellator = Tessellator.getInstance();
        WorldRenderer worldrenderer = tessellator.getWorldRenderer();
        GlStateManager.enableBlend();
        GlStateManager.disableTexture2D();
        GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
        GlStateManager.color(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha());
        worldrenderer.begin(7, DefaultVertexFormats.POSITION);

        worldrenderer.pos(x, (double)y + borderThickness, 0.0D).endVertex();
        worldrenderer.pos((double)x + width + borderThickness , (double)y + borderThickness, 0.0D).endVertex();
        worldrenderer.pos((double)x + width + borderThickness, y, 0.0D).endVertex();
        worldrenderer.pos(x, y, 0.0D).endVertex();

        worldrenderer.pos(x + width + borderThickness, (double)y + height + borderThickness, 0.0D).endVertex();
        worldrenderer.pos((double)x + width + borderThickness * 2, (double)y + height + borderThickness, 0.0D).endVertex();
        worldrenderer.pos((double)x + width + borderThickness * 2, y, 0.0D).endVertex();
        worldrenderer.pos(x + width + borderThickness, y, 0.0D).endVertex();

        worldrenderer.pos(x + borderThickness, (double)y + height + borderThickness * 2, 0.0D).endVertex();
        worldrenderer.pos((double)x + width + borderThickness * 2, (double)y + height + borderThickness * 2, 0.0D).endVertex();
        worldrenderer.pos((double)x + width + borderThickness * 2, y + height + borderThickness, 0.0D).endVertex();
        worldrenderer.pos(x + borderThickness, y + height + borderThickness, 0.0D).endVertex();

        worldrenderer.pos(x, (double)y + height + borderThickness * 2, 0.0D).endVertex();
        worldrenderer.pos((double)x + borderThickness, (double)y + height + borderThickness * 2, 0.0D).endVertex();
        worldrenderer.pos((double)x + borderThickness, y + borderThickness, 0.0D).endVertex();
        worldrenderer.pos(x, y + borderThickness, 0.0D).endVertex();

        tessellator.draw();
        GlStateManager.enableTexture2D();
        GlStateManager.disableBlend();
    }
}
