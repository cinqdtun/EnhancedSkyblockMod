package cinqdt1.Mod.features;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import cinqdt1.Mod.cinqdt1Mod;
import cinqdt1.Mod.config.ModConfiguration;
import cinqdt1.Mod.events.GuiChestBackgroundDrawnEvent;
import cinqdt1.Mod.utils.Utils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.inventory.GuiChest;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.init.Items;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ContainerChest;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.GuiScreenEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.ReflectionHelper;
import org.lwjgl.opengl.GL11;
import scala.Int;

public class HighlightChestDungeons {
	public static final ResourceLocation GUI_BACKGROUND = new ResourceLocation("esm", "gui/gui_default_180x28.png");
    private final Pattern unopenedChestPattern= Pattern.compile("^Chests expire in .*!$");
	@SubscribeEvent
    public void onGuiRender(GuiChestBackgroundDrawnEvent event) {
		if (event.displayName.startsWith("Croesus")) {
			List<Slot> invSlots = event.slots;
			List<Integer> durationsChests = new ArrayList<>();;
			GuiChest inventory = (GuiChest) event.gui;

			int unopenedChests = 0;
			for (Slot slot : invSlots) {
				ItemStack stack = slot.getStack();
				if (slot.inventory == Minecraft.getMinecraft().thePlayer.inventory || stack == null) continue;
				if (stack.getItem() != Items.skull) continue;
				List<String> lore = stack.getTooltip(Minecraft.getMinecraft().thePlayer, false);
				if (Utils.containsPattern(lore, unopenedChestPattern)) {
					if(ModConfiguration.unopenedChestsHighlight) Utils.drawOnSlot(event.chestSize, slot.xDisplayPosition, slot.yDisplayPosition, 0xBF0EAC35);
					String durationChest = Utils.matchFirstLineArray(lore, unopenedChestPattern);
					if(durationChest != null) {
						if (durationChest.length() > 0) {
							durationsChests.add(Utils.convertFormattedDuration(durationChest));
						}
					}
					unopenedChests++;
				}
			}
			ItemStack stackHandItem = Minecraft.getMinecraft().thePlayer.inventory.getItemStack();
			if(stackHandItem != null) {
				List<String> loreHandItem = stackHandItem.getTooltip(Minecraft.getMinecraft().thePlayer, false);
				if (Utils.containsPattern(loreHandItem, unopenedChestPattern)) {
					String durationChest = Utils.matchFirstLineArray(loreHandItem, unopenedChestPattern);
					if(durationChest != null) {
						if (durationChest.length() > 0) {
							durationsChests.add(Utils.convertFormattedDuration(durationChest));
						}
					}
					unopenedChests++;
				}
			}
			if(ModConfiguration.unopenedChestsOverlay) {
				try {
					Field xSizeField = ReflectionHelper.findField(GuiContainer.class, "xSize", "field_146999_f");
					Field guiLeftField = ReflectionHelper.findField(GuiContainer.class, "guiLeft", "field_147003_i");
					Field guiTopField = ReflectionHelper.findField(GuiContainer.class, "guiTop", "field_147009_r");
					Minecraft.getMinecraft().getTextureManager().bindTexture(HighlightChestDungeons.GUI_BACKGROUND);
					int xOverlay = (int) guiLeftField.get(inventory) + (int) xSizeField.get(inventory) + 4;
					int yOverlay = (int) guiTopField.get(inventory);
					int overlayTextureWidth = 180;
					int overlayTextureHeight = 101;
					GlStateManager.color(1, 1, 1, 1);
					String lowestDuration = Utils.convertToFormattedDuration(Utils.lowestNumber(durationsChests), "UNKNOWN", false);
					Utils.guiDrawTexturedModalRect(inventory, xOverlay, yOverlay, overlayTextureWidth, overlayTextureHeight);
					String displayText = EnumChatFormatting.DARK_GRAY + "You have " + EnumChatFormatting.BOLD + unopenedChests + EnumChatFormatting.RESET + EnumChatFormatting.DARK_GRAY + " unclaimed chests !\n" +
							EnumChatFormatting.DARK_GRAY + "The oldest will expire in " + EnumChatFormatting.BOLD + lowestDuration;
					Utils.renderMultiLineTextWithSpace(Minecraft.getMinecraft(), displayText, 2, xOverlay + 5, yOverlay + 5, false);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}
}
