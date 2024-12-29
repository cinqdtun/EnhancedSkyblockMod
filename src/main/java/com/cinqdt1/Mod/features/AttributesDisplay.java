package com.cinqdt1.Mod.features;

import com.cinqdt1.Mod.config.ModConfiguration;
import com.cinqdt1.Mod.events.ItemEvent;
import com.cinqdt1.Mod.utils.Utils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.Map;

public class AttributesDisplay {
    private final float scaleAttributes = 0.6f;

    @SubscribeEvent
    public void onRenderItemOverlay(ItemEvent.RenderItemOverlay event) {
        if (!Utils.inSkyblock) return;
        if (event.itemStack == null) return;
        if (!ModConfiguration.showAttributesState && !ModConfiguration.showHighestAttributeState) return;
        Map<String, Integer> attributes = Utils.getAttributes(event.itemStack);
        if (attributes.isEmpty()) return;
        GlStateManager.disableLighting();
        GlStateManager.disableDepth();
        GlStateManager.disableBlend();
        if (ModConfiguration.showAttributesState )
        {
            GlStateManager.pushMatrix();
            GlStateManager.translate(event.xPos, event.yPos, 0.0f);
            GlStateManager.scale(scaleAttributes, scaleAttributes, 1.0f);
            drawAttributes(attributes, event.fontRenderer);
            GlStateManager.popMatrix();
        }
        if (ModConfiguration.showHighestAttributeState)
        {
            Map.Entry<String, Integer> maxEntry = Utils.getMaxEntry(attributes);
            String s = maxEntry == null ? null : String.valueOf(maxEntry.getValue());
            event.fontRenderer.drawStringWithShadow(s, (float)(event.xPos + 19 - 2 - event.fontRenderer.getStringWidth(s)), (float)(event.yPos + 6 + 3), 16777215);
        }
        GlStateManager.enableLighting();
        GlStateManager.enableDepth();
    }

    private void drawAttributes(Map<String, Integer> attributes, FontRenderer fr)
    {

        int draw = 0;
        for (Map.Entry<String, Integer> entry : attributes.entrySet()) {
            if (draw >= 2) return;
            switch (entry.getKey()) {
                case "arachno":
                    fr.drawStringWithShadow("Ar", 0, draw * Minecraft.getMinecraft().fontRendererObj.FONT_HEIGHT, 0xFFFFFF);
                    draw += 1;
                    break;
                case "arachno_resistance":
                    fr.drawStringWithShadow("AR", 0, draw * Minecraft.getMinecraft().fontRendererObj.FONT_HEIGHT, 0xFFFFFF);
                    draw += 1;
                    break;
                case "attack_speed":
                    fr.drawStringWithShadow("AS",  0, draw * Minecraft.getMinecraft().fontRendererObj.FONT_HEIGHT, 0xFFFFFF);
                    draw += 1;
                    break;
                case "blazing":
                    fr.drawStringWithShadow("Bl", 0, draw * Minecraft.getMinecraft().fontRendererObj.FONT_HEIGHT, 0xFFFFFF);
                    draw += 1;
                    break;
                case "blazing_fortune":
                    fr.drawStringWithShadow("BF", 0, draw * Minecraft.getMinecraft().fontRendererObj.FONT_HEIGHT, 0xFFFFFF);
                    draw += 1;
                    break;
                case "blazing_resistance":
                    fr.drawStringWithShadow("BR", 0, draw * Minecraft.getMinecraft().fontRendererObj.FONT_HEIGHT, 0xFFFFFF);
                    draw += 1;
                    break;
                case "breeze":
                    fr.drawStringWithShadow("Br", 0, draw * Minecraft.getMinecraft().fontRendererObj.FONT_HEIGHT, 0xFFFFFF);
                    draw += 1;
                    break;
                case "combo":
                    fr.drawStringWithShadow("Co", 0, draw * Minecraft.getMinecraft().fontRendererObj.FONT_HEIGHT, 0xFFFFFF);
                    draw += 1;
                    break;
                case "deadeye":
                    fr.drawStringWithShadow( "De", 0, draw * Minecraft.getMinecraft().fontRendererObj.FONT_HEIGHT, 0xFFFFFF);
                    draw += 1;
                    break;
                case "dominance":
                    fr.drawStringWithShadow( "Do", 0, draw * Minecraft.getMinecraft().fontRendererObj.FONT_HEIGHT, 0xFFFFFF);
                    draw += 1;
                    break;
                case "double_hook":
                    fr.drawStringWithShadow("DH", 0, draw * Minecraft.getMinecraft().fontRendererObj.FONT_HEIGHT, 0xFFFFFF);
                    draw += 1;
                    break;
                case "elite":
                    fr.drawStringWithShadow("El", 0, draw * Minecraft.getMinecraft().fontRendererObj.FONT_HEIGHT, 0xFFFFFF);
                    draw += 1;
                    break;
                case "ender":
                    fr.drawStringWithShadow("En", 0, draw * Minecraft.getMinecraft().fontRendererObj.FONT_HEIGHT, 0xFFFFFF);
                    draw += 1;
                    break;
                case "ender_resistance":
                    fr.drawStringWithShadow("ER", 0, draw * Minecraft.getMinecraft().fontRendererObj.FONT_HEIGHT, 0xFFFFFF);
                    draw += 1;
                    break;
                case "experience":
                    fr.drawStringWithShadow("Ex", 0, draw * Minecraft.getMinecraft().fontRendererObj.FONT_HEIGHT, 0xFFFFFF);
                    draw += 1;
                    break;
                case "fisherman":
                    fr.drawStringWithShadow("Fi", 0, draw * Minecraft.getMinecraft().fontRendererObj.FONT_HEIGHT, 0xFFFFFF);
                    draw += 1;
                    break;
                case "fishing_experience":
                    fr.drawStringWithShadow("FE", 0, draw * Minecraft.getMinecraft().fontRendererObj.FONT_HEIGHT, 0xFFFFFF);
                    draw += 1;
                    break;
                case "fishing_speed":
                    fr.drawStringWithShadow("FS", 0, draw * Minecraft.getMinecraft().fontRendererObj.FONT_HEIGHT, 0xFFFFFF);
                    draw += 1;
                    break;
                case "fortitude":
                    fr.drawStringWithShadow("Fo", 0, draw * Minecraft.getMinecraft().fontRendererObj.FONT_HEIGHT, 0xFFFFFF);
                    draw += 1;
                    break;
                case "hunter":
                    fr.drawStringWithShadow("Hu", 0, draw * Minecraft.getMinecraft().fontRendererObj.FONT_HEIGHT, 0xFFFFFF);
                    draw += 1;
                    break;
                case "ignition":
                    fr.drawStringWithShadow( "Ig", 0, draw * Minecraft.getMinecraft().fontRendererObj.FONT_HEIGHT, 0xFFFFFF);
                    draw += 1;
                    break;
                case "infection":
                    fr.drawStringWithShadow("In", 0, draw * Minecraft.getMinecraft().fontRendererObj.FONT_HEIGHT, 0xFFFFFF);
                    draw += 1;
                    break;
                case "life_recovery":
                    fr.drawStringWithShadow("LR", 0, draw * Minecraft.getMinecraft().fontRendererObj.FONT_HEIGHT, 0xFFFFFF);
                    draw += 1;
                    break;
                case "life_regeneration":
                    fr.drawStringWithShadow("Lr", 0, draw * Minecraft.getMinecraft().fontRendererObj.FONT_HEIGHT, 0xFFFFFF);
                    draw += 1;
                    break;
                case "lifeline":
                    fr.drawStringWithShadow("Li", 0, draw * Minecraft.getMinecraft().fontRendererObj.FONT_HEIGHT, 0xFFFFFF);
                    draw += 1;
                    break;
                case "magic_find":
                    fr.drawStringWithShadow("MF", 0, draw * Minecraft.getMinecraft().fontRendererObj.FONT_HEIGHT, 0xFFFFFF);
                    draw += 1;
                    break;
                case "mana_pool":
                    fr.drawStringWithShadow("MP", 0, draw * Minecraft.getMinecraft().fontRendererObj.FONT_HEIGHT, 0xFFFFFF);
                    draw += 1;
                    break;
                case "mana_regeneration":
                    fr.drawStringWithShadow("MR", 0, draw * Minecraft.getMinecraft().fontRendererObj.FONT_HEIGHT, 0xFFFFFF);
                    draw += 1;
                    break;
                case "mana_steal":
                    fr.drawStringWithShadow("MS", 0, draw * Minecraft.getMinecraft().fontRendererObj.FONT_HEIGHT, 0xFFFFFF);
                    draw += 1;
                    break;
                case "midas_touch":
                    fr.drawStringWithShadow("MT", 0, draw * Minecraft.getMinecraft().fontRendererObj.FONT_HEIGHT, 0xFFFFFF);
                    draw += 1;
                    break;
                case "speed":
                    fr.drawStringWithShadow("Sp", 0, draw * Minecraft.getMinecraft().fontRendererObj.FONT_HEIGHT, 0xFFFFFF);
                    draw += 1;
                    break;
                case "trophy_hunter":
                    fr.drawStringWithShadow("TH", 0, draw * Minecraft.getMinecraft().fontRendererObj.FONT_HEIGHT, 0xFFFFFF);
                    draw += 1;
                    break;
                case "undead":
                    fr.drawStringWithShadow("Un", 0, draw * Minecraft.getMinecraft().fontRendererObj.FONT_HEIGHT, 0xFFFFFF);
                    draw += 1;
                    break;
                case "undead_resistance":
                    fr.drawStringWithShadow("UR", 0, draw * Minecraft.getMinecraft().fontRendererObj.FONT_HEIGHT, 0xFFFFFF);
                    draw += 1;
                    break;
                case "veteran":
                    fr.drawStringWithShadow("Ve", 0, draw * Minecraft.getMinecraft().fontRendererObj.FONT_HEIGHT, 0xFFFFFF);
                    draw += 1;
                    break;
                case "vitality":
                    fr.drawStringWithShadow("Vi", 0, draw * Minecraft.getMinecraft().fontRendererObj.FONT_HEIGHT, 0xFFFFFF);
                    draw += 1;
                    break;
                case "warrior":
                    fr.drawStringWithShadow("Wa", 0, draw * Minecraft.getMinecraft().fontRendererObj.FONT_HEIGHT, 0xFFFFFF);
                    draw += 1;
                    break;
            }
        }
    }
}
