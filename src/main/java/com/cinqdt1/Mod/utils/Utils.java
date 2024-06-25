package com.cinqdt1.Mod.utils;

import com.cinqdt1.Mod.handlers.ScoreboardHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.gui.inventory.GuiChest;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.init.Items;
import net.minecraft.inventory.ContainerChest;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.JsonToNBT;
import net.minecraft.nbt.NBTException;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.scoreboard.ScoreObjective;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.StringUtils;
import net.minecraftforge.common.util.Constants;
import org.lwjgl.opengl.GL11;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Utils {
	public static final String defaultUUID = "00000000-0000-0000-0000-000000000000";
	public static boolean inSkyblock = false;
	public static boolean inDungeon = false;
	public static boolean inF3 = false;
	public static boolean inF6 = false;
	public static boolean inF7 = false;
	public static boolean inCrimsonIsle = false;
	public static boolean inNucleus = false;
	public static boolean inDivan = false;

	public static List<String> crimsonIsleSubLocations = Arrays.asList("Barbarian Outpost","The Bastion","Blazing Volcano","Burning Desert","Cathedral","Crimson Fields","Crimson Isle","Dojo","Dragontail","Forgotten Skull","Kuudra's End","Mage Outpost","Magma Chamber","Mystic Marsh","Odger's Hut","Ruins of Ashfang","Scarleton","Stronghold","The Wasteland","Matriarch's Lair","Belly of the Beast","Aura's Lab");
	
	public static void checkForSkyblock() {
		Minecraft mc = Minecraft.getMinecraft();
		if (mc != null && mc.theWorld != null && !mc.isSingleplayer()) {
			ScoreObjective scoreboardObj = mc.theWorld.getScoreboard().getObjectiveInDisplaySlot(1);
			if (scoreboardObj != null) {
				String scObjName = ScoreboardHandler.cleanSB(scoreboardObj.getDisplayName());
				if (scObjName.contains("SKYBLOCK")) {
					inSkyblock = true;
					return;
				}
			}
		}
		inSkyblock = false;
	}
	public static void checkForDungeon() {
		if (inSkyblock) {
			if (isInScoreboard("The Catacombs")) {
	    		inDungeon = true;
	    		return;
			}
	    	inDungeon = false;
		}
	}
	public static void checkForF3() {
		if (inSkyblock) {
			if (isInScoreboard("The Catacombs (F3)") || isInScoreboard("The Catacombs (M3)") ||
					(isInScoreboard("Healthy") && isInScoreboard("Reinforced") && isInScoreboard("Laser") && isInScoreboard("Chaos"))) {
				inF3 = true;
				return;
			}
			inF3 = false;
		}
	}
	public static void checkForF6() {
		if (inDungeon) {
			if (isInScoreboard("The Catacombs (F6)") || isInScoreboard("The Catacombs (M6)")) {
	    		inF6 = true;
	    		return;
			}
	    	inF6 = false;
		}
	}
	public static void checkForF7() {
		if (inDungeon) {
			if (isInScoreboard("The Catacombs (F7)") || isInScoreboard("The Catacombs (M7)")) {
	    		inF7 = true;
	    		return;
			}
	    	inF7 = false;
		}
	}
	public static void checkForCrimsonIsle() {
		if (inSkyblock) {
			for(String location : crimsonIsleSubLocations) {
				if (isInScoreboard(location)) {
	    			inCrimsonIsle = true;
	    			return;
				}
			}
    		
		}
		inCrimsonIsle = false;
	}
	public static void checkForNucleus(){
		if (inSkyblock) {
			if (isInScoreboard("Crystal Nucleus")) {
					inNucleus = true;
					return;
			}
		}
		inNucleus = false;
	}
	public static void checkForDivan(){
		if (inSkyblock) {
			if (isInScoreboard("Mines of Divan")) {
				inDivan = true;
				return;
			}
		}
		inDivan = false;
	}
	
	public static boolean isInScoreboard(String text) {
		List<String> scoreboard = ScoreboardHandler.getSidebarLines();
		for (String s : scoreboard) {
			String sCleaned = ScoreboardHandler.cleanSB(s);
			if (sCleaned.contains(text)) return true;
		}
		return false;
	}

	public static String getMatchInScoreboard(String text) {
		List<String> scoreboard = ScoreboardHandler.getSidebarLines();
		for (String s : scoreboard) {
			String sCleaned = ScoreboardHandler.cleanSB(s);
			if (sCleaned.contains(text)) return s;
		}
		return null;
	}
	public static String getTimeBetween(double timeOne, double timeTwo) {
		double secondsBetween = Math.floor(timeTwo - timeOne);
		
		String timeFormatted;
		int days;
		int hours;
		int minutes;
		int seconds;
		
		if (secondsBetween > 86400) {
			// More than 1d, display #d#h
			days = (int) (secondsBetween / 86400);
			hours = (int) (secondsBetween % 86400 / 3600);
			timeFormatted = days + "d" + hours + "h";
		} else if (secondsBetween > 3600) {
			// More than 1h, display #h#m
			hours = (int) (secondsBetween / 3600);
			minutes = (int) (secondsBetween % 3600 / 60);
			timeFormatted = hours + "h" + minutes + "m";
		} else {
			// Display #m#s
			minutes = (int) (secondsBetween / 60);
			seconds = (int) (secondsBetween % 60);
			timeFormatted = minutes + "m" + seconds + "s";
		}
		
		return timeFormatted;
	}
	public static int getIntColorFromRGBAColor(int r,int g,int b,int a) {
		return (a << 24) | ((r & 255) << 16) | ((g & 255) << 8) | (b & 255);
	}
	public static void sendModMessage(String message) {
		if(Minecraft.getMinecraft().thePlayer == null) return;
		Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText(EnumChatFormatting.GOLD + "[5dt1's Mod] " + EnumChatFormatting.RESET + message));
	}
	public static String formatInt(int value) {
		NumberFormat myFormat = NumberFormat.getInstance();
		myFormat.setGroupingUsed(true);
		return myFormat.format(value);
	}
	public static String getSkyBlockID(ItemStack item) {
        if(item != null) {
            NBTTagCompound extraAttributes = item.getSubCompound("ExtraAttributes", false);
            if(extraAttributes != null && extraAttributes.hasKey("id")) {
                return extraAttributes.getString("id");
            }
        }
        return "";
    }
	public static int getUnformattedHp(String formattedHp) {
		char[] formattedHpCharArray = formattedHp.toCharArray();
		char lastChar = formattedHpCharArray[formattedHpCharArray.length - 1];
		if(!Character.isLetter(lastChar)) return Integer.valueOf(formattedHp);
		int mainHp = Integer.valueOf(formattedHp.substring(0, formattedHp.length() - 1));
		switch(lastChar) {
			case 'k':
			case 'K':
				return mainHp * 1000;
			case 'm':
			case 'M':
				return mainHp * 1000000;
			default:
				return mainHp;
		}
	}
	 public static String getGuiName(GuiScreen gui) {
	        if(gui instanceof GuiChest) {
	            return ((ContainerChest) ((GuiChest) gui).inventorySlots).getLowerChestInventory().getDisplayName().getUnformattedText();
	        }
	        return "";
	    }
	 public static boolean containsPattern(List<String> array, Pattern pattern) {
		 for(String line : array) {
			 if(line.length() < 1) continue;
			 String unformattedLine = StringUtils.stripControlCodes(line);
			 Matcher matchLineResult = pattern.matcher(unformattedLine);
			 if(matchLineResult.find()) {
				 return true;
			 }
		 }
		 return false;
	 }
	 
	 public static void drawOnSlot(int size, int xSlotPos, int ySlotPos, int colour) {
			ScaledResolution sr = new ScaledResolution(Minecraft.getMinecraft());
			int guiLeft = (sr.getScaledWidth() - 176) / 2;
			int guiTop = (sr.getScaledHeight() - 222) / 2;
			int x = guiLeft + xSlotPos;
			int y = guiTop + ySlotPos;
			// Move down when chest isn't 6 rows
			if (size != 90) y += (6 - (size - 36) / 9) * 9;
			GL11.glTranslated(0, 0, 1);
			Gui.drawRect(x, y, x + 16, y + 16, colour);
			GL11.glTranslated(0, 0, -1);
	}

	public static void guiDrawTexturedModalRect(GuiScreen gui, int x, int y, int xOffset, int yOffset, int width, int height){
		GlStateManager.disableLighting();
		gui.drawTexturedModalRect(x , y, xOffset, yOffset, width, height);
	}

	public static void guiDrawTexturedModalRect(GuiScreen gui, int x, int y, int width, int height){
		GlStateManager.disableLighting();
		gui.drawTexturedModalRect(x , y, 0, 0, width, height);
	}
	public static void drawText(Minecraft mc, String text, int x, int y , boolean shadow){
		RenderUtils.renderText(mc, text, x, y, 1, shadow);
	}

	/**
	 *Separate each line by \n
	 */
	public static void renderMultiLineTextWithSpace(Minecraft mc, String text, int pixelsBetweenLines, int x, int y, boolean shadow){
		int i = 0;
		for(String line : text.split("\n")){
			if(line.length() < 1) continue;
			mc.fontRendererObj.drawString(line, x, y + i * (pixelsBetweenLines + mc.fontRendererObj.FONT_HEIGHT), 0xFFFFFF, shadow);
			i++;
		}
	}

	public static int convertFormattedDuration(String duration){
		int durationSecondsInt = 0;
		Pattern dayPattern = Pattern.compile("(\\d+)d");
		Pattern hourPattern = Pattern.compile("(\\d+)h");
		Pattern minutePattern = Pattern.compile("(\\d+)m");
		Pattern secondPattern = Pattern.compile("(\\d+)s");
		Matcher matcherDay = dayPattern.matcher(duration);
		Matcher matcherHour = hourPattern.matcher(duration);
		Matcher matcherMinute = minutePattern.matcher(duration);
		Matcher matcherSecond = secondPattern.matcher(duration);
		if(matcherDay.find()){
			durationSecondsInt += Integer.parseInt(matcherDay.group(1)) * 86400;
		}
		if(matcherHour.find()){
			durationSecondsInt += Integer.parseInt(matcherHour.group(1)) * 3600;
		}
		if(matcherMinute.find()){
			durationSecondsInt += Integer.parseInt(matcherMinute.group(1)) * 60;
		}
		if(matcherSecond.find()){
			durationSecondsInt += Integer.parseInt(matcherSecond.group(1));
		}
		return  durationSecondsInt;
	}

	public static String matchFirstLineArray(List<String> array, Pattern pattern){
		for(String line : array){
			if(line.length() < 1) continue;
			String unformattedLine = StringUtils.stripControlCodes(line);
			Matcher matchLineResult = pattern.matcher(unformattedLine);
			if(matchLineResult.find()) {
				return unformattedLine;
			}
		}
		return null;
	}

	public static int lowestNumber(List<Integer> array){
		if(array.size() < 1) return Integer.MIN_VALUE;
		int lowest = array.get(0);
		boolean firstIteration = false;
		for(int number : array) {
			if(!firstIteration){
				firstIteration = true;
				continue;
			}
			lowest = Math.min(lowest, number);
		}
		return lowest;
	}

	public static String convertToFormattedDuration(int duration, String errorString, boolean space){
		if(duration < 1) return errorString;
		int rest = duration % 86400;
		int days = duration / 86400;
		int hours = 0;
		int minutes = 0;
		int seconds = 0;
		if(rest > 0){
			hours = rest / 3600;
			rest = rest % 3600;
			if(rest > 0){
				minutes = rest / 60;
				seconds = rest % 60;
			}
		}
		//Days Hours Minutes Seconds
		if(space) return (days > 0 ? days + "d " : "") + (hours > 0 ? hours + "h " : "") + (minutes > 0 ? minutes + "m " : "") + (seconds > 0 ? seconds + "s" : "");
		return (days > 0 ? days + "d": "") + (hours > 0 ? hours + "h" : "") + (minutes > 0 ? minutes + "m" : "") + (seconds > 0 ? seconds + "s" : "");
	}

	public static List<ItemStack> convertSlotArrayToItemStack(List<Slot> array, IInventory inventoryFilter){
		List<ItemStack> itemStacksArray = new ArrayList<>();
		for(Slot slot : array){

			if(slot == null || slot.inventory == inventoryFilter) continue;
			itemStacksArray.add(slot.getStack());
		}
		return itemStacksArray;
	}
	public static List<ItemStack> convertSlotArrayToItemStack(List<Slot> array){
		List<ItemStack> itemStacksArray = new ArrayList<>();
		for(Slot slot : array){

			if(slot == null) continue;
			itemStacksArray.add(slot.getStack());
		}
		return itemStacksArray;
	}
	public static String getFirstGroupMatchArray(List<String> array, Pattern pattern){
		for(String line : array){
			if(line.length() < 1) continue;
			String unformattedLine = StringUtils.stripControlCodes(line);
			Matcher matchLineResult = pattern.matcher(unformattedLine);
			if(matchLineResult.find()) {
				if(matchLineResult.group(1) != null){
					return matchLineResult.group(1);
				}
				return unformattedLine;
			}
		}
		return null;
	}
	public static double convertFormattedNumber(String rawNumber) {
		return Double.parseDouble(rawNumber.replaceAll(",",""));
	}
	public static String convertDoubleToString(double number){
		String big = new BigDecimal(String.format(Locale.US , "%f", number)).stripTrailingZeros().toPlainString();
		return big;

	}
	public static String formatStringNumber(String number){
		String[] array = number.split("\\.");
		String numString = array[0];

		String newString = "";
		for(int i = 0; i < numString.length() ; i++){
			if((numString.length() - i - 1) % 3 == 0 && (numString.length() - i - 1) != 0){
				newString += numString.charAt(i) + ",";
			}else{
				newString += numString.charAt(i);
			}
		}
		if(array.length == 1) return newString;
		newString += '.' + array[1];
		return newString;
	}

	public static int getIndexFirstMatchLineArray(List<String> array, Pattern pattern){
		int i = 0;
		for(String line : array){
			if(line.length() < 1) continue;
			String unformattedLine = StringUtils.stripControlCodes(line);
			Matcher matchLineResult = pattern.matcher(unformattedLine);
			if(matchLineResult.find()) {
				return i;
			}
			i++;
		}
		return -1;
	}

	public static String getFormattedPlayerUUID(EntityPlayerSP player){
		return player.getUniqueID().toString().replaceAll("-", "");
	}

	public static String formatString(String string){
		StringBuilder formattedString = new StringBuilder();
		boolean startWord = true;
		for(char c : string.toCharArray()) {
			if(Character.isUpperCase(c) && !startWord) {
				formattedString.append(Character.toLowerCase(c));
			}else if(c == '_' && !startWord) {
				formattedString.append(' ');
				startWord = true;
			}else if(startWord) {
				formattedString.append(c);
				startWord = false;
			}
		}
		return formattedString.toString();
	}

	public static double transformTickToSeconds(int ticks){
		int sub = ticks % 2;
		return (ticks - sub) * 0.1 / 2;
	}
	public static int negate(int value) {
		return -value;
	}
	public static double negate(double value) {
		return -value;
	}

	public static int convertRomanToDecimal(String roman){
		int decimalValue = 0;
		for(int i = 0; i < roman.length(); i++){
			char currentChar = roman.charAt(i);
			if (currentChar == 'I'){
				if(i + 1 < roman.length()){
					char nextChar = roman.charAt(i + 1);
					if(nextChar == 'V'){
						decimalValue -= 1;
						continue;
					}
				}
				decimalValue += 1;
			}else if(currentChar == 'V'){
				decimalValue += 5;
			}
		}
		return  decimalValue;
	}

	public static String getHeadTexture(ItemStack head){
		if(head.getItem() != Items.skull) return null;
		if(!head.getTagCompound().hasKey("SkullOwner")) return null;
		if(!head.getTagCompound().getCompoundTag("SkullOwner").hasKey("Properties")) return null;
		if(!head.getTagCompound().getCompoundTag("SkullOwner").getCompoundTag("Properties").hasKey("textures", Constants.NBT.TAG_COMPOUND)) return null;
		if(!head.getTagCompound().getCompoundTag("SkullOwner").getCompoundTag("Properties").getTagList("textures", Constants.NBT.TAG_COMPOUND).getCompoundTagAt(0).hasKey("Value")) return null;
		return head.getTagCompound().getCompoundTag("SkullOwner").getCompoundTag("Properties").getTagList("textures", Constants.NBT.TAG_COMPOUND).getCompoundTagAt(0).getString("Value");
	}

	public static ItemStack createTexturedAndSetId(String texture, String id, String customUUID){
		ItemStack head = new ItemStack(Items.skull);
		head.setItemDamage(3);
		try{
			head.setTagCompound(JsonToNBT.getTagFromJson("{ExtraAttributes:{id:\"" + id + "\"}, SkullOwner:{Id:" + customUUID + ",Properties:{textures:[{Value:" + texture + "}]} } }"));
		}catch (NBTException ignored){}
		return head;
	}

	public static ItemStack setIdItem(ItemStack item, String id){
		try{
			item.setTagCompound(JsonToNBT.getTagFromJson("{ExtraAttributes:{id:\"" + id + "\"} }"));
		}catch (NBTException ignored){}
		return item;
	}
	/*
	 * Taken from Minecraft
	 * At FontRenderer#getStringWidth
	 * For fixing some error of rendering with Optifine cut sometime before the end
	 */
	public static int getStringWidth(String text)
	{
		if (text == null)
		{
			return 0;
		}
		else
		{
			int i = 0;
			boolean flag = false;

			for (int j = 0; j < text.length(); ++j)
			{
				char c0 = text.charAt(j);
				int k = Minecraft.getMinecraft().fontRendererObj.getCharWidth(c0);

				if (k < 0 && j < text.length() - 1)
				{
					++j;
					c0 = text.charAt(j);

					if (c0 != 108 && c0 != 76)
					{
						if (c0 == 114 || c0 == 82)
						{
							flag = false;
						}
					}
					else
					{
						flag = true;
					}

					k = 0;
				}

				i += k;

				if (flag && k > 0)
				{
					++i;
				}
			}

			return i;
		}
	}

	public static float roundFloat(float value){
		return new BigDecimal(value).setScale(1, RoundingMode.HALF_UP).floatValue();
	}

	public static double roundToHalfDown(int decimal,double value){
		if(decimal <= 0) return value;
		int factor = 2 * (int)Math.pow(10, decimal - 1);
		return Math.floor(value * factor) / factor;
	}
}