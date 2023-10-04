package cinqdt1.Mod.gui;

import cinqdt1.Mod.cinqdt1Mod;
import cinqdt1.Mod.gui.buttons.GuiFeatureButton;
import cinqdt1.Mod.gui.buttons.GuiFeatureButtonResizable;
import cinqdt1.Mod.utils.ItemUtils;
import cinqdt1.Mod.utils.RenderUtils;
import cinqdt1.Mod.utils.Utils;
import gg.essential.api.EssentialAPI;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;

import java.util.ArrayList;
import java.util.List;

public class EditLocations extends GuiScreen{
	private FeatureButton buttonPerformed = FeatureButton.NONE;
	private Action buttonAction = Action.NONE;
	private int lastMouseX = -1;
	private int lastMouseY = -1;

	private GuiFeatureButton batFirework;
	private GuiFeatureButton bobberTimer;
	private GuiFeatureButton bundleTracker;
	private GuiFeatureButton fireFreezeOverlay;
	private GuiFeatureButton firePillarOverlay;
	private GuiFeatureButton fragRunTracker;
	private GuiFeatureButton endermanPetTracker;
	private GuiFeatureButton lowestHpSummonDisplay;
	private GuiFeatureButton mythologicalHp;
	private GuiFeatureButton scavengedStats;
	private GuiFeatureButton xpRunTracker;

	private GuiFeatureButtonResizable batFireworkResize;
	private GuiFeatureButtonResizable bobberTimerResize;
	private GuiFeatureButtonResizable bundleTrackerResize;
	private GuiFeatureButtonResizable fireFreezeOverlayResize;
	private GuiFeatureButtonResizable firePillarOverlayResize;
	private GuiFeatureButtonResizable fragRunTrackerResize;
	private GuiFeatureButtonResizable endermanPetTrackerResize;
	private GuiFeatureButtonResizable lowestHpSummonDisplayResize;
	private GuiFeatureButtonResizable mythologicalHpResize;
	private GuiFeatureButtonResizable scavengedStatsResize;
	private GuiFeatureButtonResizable xpRunTrackerResize;

	@Override
	public void initGui() {
		super.initGui();

		List<String> batFireworkText = new ArrayList<>();
		List<String> bobberTimerText = new ArrayList<>();
		List<String> bundleTrackerText = new ArrayList<>();
		List<String> fireFreezeText = new ArrayList<>();
		List<String> firePillarText = new ArrayList<>();
		List<String> fragRunText = new ArrayList<>();
		List<String> endermanPetText = new ArrayList<>();
		List<String> lowestSummonText = new ArrayList<>();
		List<String> mythologicalText = new ArrayList<>();
		List<String> scavengedStatsText = new ArrayList<>();
		List<String> xpRuns = new ArrayList<>();

		batFireworkText.add(EnumChatFormatting.RED + "11.31");

		bobberTimerText.add(EnumChatFormatting.RED  + Utils.getTimeBetween(0, 3));

		bundleTrackerText.add(EnumChatFormatting.YELLOW + "" + EnumChatFormatting.BOLD + "Crystal Loot Bundle\n" +
				EnumChatFormatting.GOLD + "Bob-omb :\n" +
				EnumChatFormatting.GOLD + "Pickonimbus 2000:\n" +
				EnumChatFormatting.GOLD + "Prehistoric Egg:\n" +
				EnumChatFormatting.GOLD + "Divan's Fragment:\n" +
				EnumChatFormatting.GOLD + "Recall Potion:\n" +
				EnumChatFormatting.GOLD + "Jaderald:\n" +
				EnumChatFormatting.GOLD + "Divan's Alloy:\n" +
				EnumChatFormatting.GOLD + "Fortune IV:\n" +
				EnumChatFormatting.GOLD + "Quick Claw:\n" +
				EnumChatFormatting.GOLD + "Gemstone Mixture:\n" +
				EnumChatFormatting.GOLD + "Bundle Runs:");
		bundleTrackerText.add("\n" +
				EnumChatFormatting.GOLD + "96\n" +
				EnumChatFormatting.GOLD + "4\n" +
				EnumChatFormatting.GOLD + "5\n" +
				EnumChatFormatting.GOLD + "2\n" +
				EnumChatFormatting.GOLD + "0\n" +
				EnumChatFormatting.GOLD + "1\n" +
				EnumChatFormatting.GOLD + "0\n" +
				EnumChatFormatting.GOLD + "1\n" +
				EnumChatFormatting.GOLD + "0\n" +
				EnumChatFormatting.GOLD + "0\n" +
				EnumChatFormatting.GOLD + "8");

		fireFreezeText.add(EnumChatFormatting.GRAY + "Fire Freeze\n" +
				EnumChatFormatting.GRAY + "in " + EnumChatFormatting.RED + "1.3");

		firePillarText.add(EnumChatFormatting.GOLD + "" + EnumChatFormatting.BOLD + "Inferno Demonlord\n" +
				"Pillar: " + EnumChatFormatting.RED + "6s");

    	fragRunText.add(EnumChatFormatting.GREEN + "" + EnumChatFormatting.BOLD +"Giants\n" +
				EnumChatFormatting.DARK_GREEN + "Diamante's:\n" +
				EnumChatFormatting.DARK_GREEN + "L.A.S.R.'s:\n" +
				EnumChatFormatting.DARK_GREEN + "Bigfoot's:\n" +
				EnumChatFormatting.DARK_GREEN + "Jolly Pink:\n" +
				EnumChatFormatting.GREEN + "" + EnumChatFormatting.BOLD + "Giants Drops\n" +
				EnumChatFormatting.DARK_GREEN + "Diamante's Handles:\n" +
				EnumChatFormatting.DARK_GREEN + "L.A.S.R.'s Eyes:\n" +
				EnumChatFormatting.DARK_GREEN + "Bigfoot's Lassos:\n" +
				EnumChatFormatting.DARK_GREEN + "Jolly Pink Rocks:");

		fragRunText.add(EnumChatFormatting.GREEN + "\n" +
				EnumChatFormatting.DARK_GREEN + "5\n" +
				EnumChatFormatting.DARK_GREEN + "3\n" +
				EnumChatFormatting.DARK_GREEN + "4\n" +
				EnumChatFormatting.DARK_GREEN + "2\n" +
				EnumChatFormatting.GREEN + "\n" +
				EnumChatFormatting.DARK_GREEN + "4\n" +
				EnumChatFormatting.DARK_GREEN + "2\n" +
				EnumChatFormatting.DARK_GREEN + "3\n" +
				EnumChatFormatting.DARK_GREEN + "2");
		fragRunText.add("\n" +
				EnumChatFormatting.YELLOW  + "35%\n" +
				EnumChatFormatting.YELLOW  + "21%\n" +
				EnumChatFormatting.YELLOW  + "28%\n" +
				EnumChatFormatting.YELLOW  + "14%\n" +
				"\n" +
				EnumChatFormatting.YELLOW  + "80%\n" +
				EnumChatFormatting.YELLOW  + "66%\n" +
				EnumChatFormatting.YELLOW  + "75%\n" +
				EnumChatFormatting.YELLOW  + "100%");

		endermanPetText.add(EnumChatFormatting.BLUE + "" + EnumChatFormatting.BOLD + "Rare\n" +
				EnumChatFormatting.BLUE+ "Enderman\n" +
				EnumChatFormatting.DARK_PURPLE + "" + EnumChatFormatting.BOLD + "Epic\n" +
				EnumChatFormatting.DARK_PURPLE + "Enderman:\n" +
				EnumChatFormatting.GOLD + "" + EnumChatFormatting.BOLD + "Legendary\n" +
				EnumChatFormatting.GOLD + "Enderman:");
        endermanPetText.add(EnumChatFormatting.BLUE + "\n" +
				EnumChatFormatting.BLUE + "14\n" +
				EnumChatFormatting.DARK_PURPLE + "\n" +
				EnumChatFormatting.DARK_PURPLE + "5\n" +
				EnumChatFormatting.GOLD + "\n" +
				EnumChatFormatting.GOLD + "1");

		lowestSummonText.add(EnumChatFormatting.GREEN + "Tank Zombie\n" +
				EnumChatFormatting.RED + "126k");

		mythologicalText.add(EnumChatFormatting.DARK_GRAY + "[" + EnumChatFormatting.GRAY + "Lv260" + EnumChatFormatting.DARK_GRAY + "]" + EnumChatFormatting.DARK_GREEN + " Exalted Gaia Construct" + EnumChatFormatting.YELLOW + " 876k" + EnumChatFormatting.WHITE + "/" + EnumChatFormatting.GREEN + "1.5M" + EnumChatFormatting.RED + "❤");

		scavengedStatsText.add(EnumChatFormatting.GOLD + "" + EnumChatFormatting.BOLD + "Scavenged Stats\n" +
				"Avg time per pair: 3m 40s");

		xpRuns.add(EnumChatFormatting.DARK_GRAY + "" + EnumChatFormatting.BOLD + "Xp Runs\n" +
				EnumChatFormatting.GRAY + "Current pet:\n" +
				EnumChatFormatting.GRAY + "Average xp/run:\n" +
				EnumChatFormatting.GRAY + "Runs needed to lvl max:");
		xpRuns.add(EnumChatFormatting.DARK_GRAY + "\n" +
				EnumChatFormatting.GRAY + "Ender Dragon\n" +
				EnumChatFormatting.GRAY + "169 456\n" +
				EnumChatFormatting.GRAY + "98");

		try {
			batFirework = new GuiFeatureButton(cinqdt1Mod.newModConfig.getInteger("batFirework", "position", "x"), cinqdt1Mod.newModConfig.getInteger("batFirework", "position", "y"), cinqdt1Mod.newModConfig.getFloat("batFirework", "position", "scale"), ItemUtils.getBatFireworkTexture(), 4, 3,batFireworkText, 0, FeatureButton.BAT_FIREWORK);
			bobberTimer = new GuiFeatureButton(cinqdt1Mod.newModConfig.getInteger("bobberTimer", "position", "x"), cinqdt1Mod.newModConfig.getInteger("bobberTimer", "position", "y"), cinqdt1Mod.newModConfig.getFloat("bobberTimer", "position", "scale"), new ItemStack(Items.fishing_rod), 4, 3,bobberTimerText, 20, FeatureButton.BOBBER_TIMER);
			bundleTracker = new GuiFeatureButton(cinqdt1Mod.newModConfig.getInteger("bundleTracker", "position", "x"), cinqdt1Mod.newModConfig.getInteger("bundleTracker", "position", "y"), cinqdt1Mod.newModConfig.getFloat("bundleTracker", "position", "scale"),null, 0, 0, bundleTrackerText, 0, FeatureButton.BUNDLE_TRACKER);
			fireFreezeOverlay = new GuiFeatureButton(cinqdt1Mod.newModConfig.getInteger("fireFreeze", "position", "x"), cinqdt1Mod.newModConfig.getInteger("fireFreeze", "position", "y"), cinqdt1Mod.newModConfig.getFloat("fireFreeze", "position", "scale"), ItemUtils.getFireFreezeTexture(), 4,3, fireFreezeText, 0, FeatureButton.FIRE_FREEZE_OVERLAY);
			firePillarOverlay = new GuiFeatureButton(cinqdt1Mod.newModConfig.getInteger("firePillar", "position", "x"), cinqdt1Mod.newModConfig.getInteger("firePillar", "position", "y"), cinqdt1Mod.newModConfig.getFloat("firePillar", "position", "scale"), ItemUtils.getFirePillarTexture(), 4, 3, firePillarText, 0, FeatureButton.FIRE_PILLAR_OVERLAY);
			fragRunTracker = new GuiFeatureButton(cinqdt1Mod.newModConfig.getInteger("fragRunTracker", "position", "x"), cinqdt1Mod.newModConfig.getInteger("fragRunTracker", "position", "y"), cinqdt1Mod.newModConfig.getFloat("fragRunTracker", "position", "scale"), null, 0, 0, fragRunText, 20, FeatureButton.FRAGRUN_TRACKER);
			endermanPetTracker = new GuiFeatureButton(cinqdt1Mod.newModConfig.getInteger("endermanPetTracker", "position", "x"), cinqdt1Mod.newModConfig.getInteger("endermanPetTracker", "position", "y"), cinqdt1Mod.newModConfig.getFloat("endermanPetTracker", "position", "scale"), null, 0, 0, endermanPetText, 20, FeatureButton.ENDERMAN_PET_TRACKER);
			lowestHpSummonDisplay = new GuiFeatureButton(cinqdt1Mod.newModConfig.getInteger("lowestHpSummon", "position", "x"), cinqdt1Mod.newModConfig.getInteger("lowestHpSummon", "position", "y"), cinqdt1Mod.newModConfig.getFloat("lowestHpSummon", "position", "scale"), ItemUtils.getLowestSummonTexture(), 4, 3, lowestSummonText, 0, FeatureButton.LOWEST_HP_SUMMON_DISPLAY);
			mythologicalHp = new GuiFeatureButton(cinqdt1Mod.newModConfig.getInteger("mythologicalHp", "position", "x"), cinqdt1Mod.newModConfig.getInteger("mythologicalHp", "position", "y"), cinqdt1Mod.newModConfig.getFloat("mythologicalHp", "position", "scale"), null, 0, 0, mythologicalText, 0, FeatureButton.MYTHOLOGICAL_HP);
			scavengedStats = new GuiFeatureButton(cinqdt1Mod.newModConfig.getInteger("scavengedStats", "position", "x"), cinqdt1Mod.newModConfig.getInteger("scavengedStats", "position", "y"), cinqdt1Mod.newModConfig.getFloat("scavengedStats", "position", "scale"), ItemUtils.getScavengedStatsTexture(), 4, 3, scavengedStatsText, 0, FeatureButton.SCAVENGED_STATS);
			xpRunTracker = new GuiFeatureButton(cinqdt1Mod.newModConfig.getInteger("xpRunTracker", "position", "x"), cinqdt1Mod.newModConfig.getInteger("xpRunTracker", "position", "y"), cinqdt1Mod.newModConfig.getFloat("xpRunTracker", "position", "scale"), null,0 , 0, xpRuns, 20, FeatureButton.XP_RUN_TRACKER);
		} catch (Exception e) {
			e.printStackTrace();
		}

		batFireworkResize = new GuiFeatureButtonResizable(batFirework, FeatureButton.BAT_FIREWORK);
		bobberTimerResize = new GuiFeatureButtonResizable(bobberTimer, FeatureButton.BOBBER_TIMER);
		bundleTrackerResize = new GuiFeatureButtonResizable(bundleTracker, FeatureButton.BUNDLE_TRACKER);
		fireFreezeOverlayResize = new GuiFeatureButtonResizable(fireFreezeOverlay, FeatureButton.FIRE_FREEZE_OVERLAY);
		firePillarOverlayResize = new GuiFeatureButtonResizable(firePillarOverlay, FeatureButton.FIRE_PILLAR_OVERLAY);
		fragRunTrackerResize = new GuiFeatureButtonResizable(fragRunTracker, FeatureButton.FRAGRUN_TRACKER);
		endermanPetTrackerResize = new GuiFeatureButtonResizable(endermanPetTracker, FeatureButton.ENDERMAN_PET_TRACKER);
		lowestHpSummonDisplayResize = new GuiFeatureButtonResizable(lowestHpSummonDisplay, FeatureButton.LOWEST_HP_SUMMON_DISPLAY);
		mythologicalHpResize = new GuiFeatureButtonResizable(mythologicalHp, FeatureButton.MYTHOLOGICAL_HP);
		scavengedStatsResize = new GuiFeatureButtonResizable(scavengedStats, FeatureButton.SCAVENGED_STATS);
		xpRunTrackerResize = new GuiFeatureButtonResizable(xpRunTracker, FeatureButton.XP_RUN_TRACKER);

		this.buttonList.add(batFirework);
        this.buttonList.add(bobberTimer);
		this.buttonList.add(bundleTracker);
		this.buttonList.add(fireFreezeOverlay);
		this.buttonList.add(firePillarOverlay);
        this.buttonList.add(fragRunTracker);
        this.buttonList.add(endermanPetTracker);
        this.buttonList.add(lowestHpSummonDisplay);
		this.buttonList.add(mythologicalHp);
		this.buttonList.add(scavengedStats);
        this.buttonList.add(xpRunTracker);

		this.buttonList.add(batFireworkResize);
		this.buttonList.add(bobberTimerResize);
		this.buttonList.add(bundleTrackerResize);
		this.buttonList.add(fireFreezeOverlayResize);
		this.buttonList.add(firePillarOverlayResize);
		this.buttonList.add(fragRunTrackerResize);
		this.buttonList.add(endermanPetTrackerResize);
		this.buttonList.add(lowestHpSummonDisplayResize);
		this.buttonList.add(mythologicalHpResize);
		this.buttonList.add(scavengedStatsResize);
		this.buttonList.add(xpRunTrackerResize);
	}

	public enum Action{
		MOVE,
		RESIZE,
		NONE
	}
	
	@Override
	public boolean doesGuiPauseGame() {
		return false;
	}
	
	private void mouseMoved(int mouseX, int mouseY) {
		try {
			double xMoved = RenderUtils.getUnscaledRatio((double)(mouseX - lastMouseX));
			double yMoved = RenderUtils.getUnscaledRatio((double)(mouseY - lastMouseY));
			if(buttonAction == Action.RESIZE) {
				switch (buttonPerformed) {
					case BAT_FIREWORK:
						float batFireworkScale = batFirework.getNewScaleWithX(xMoved);
						if (batFireworkScale > 0.5f && batFireworkScale < 6.0f)
							cinqdt1Mod.newModConfig.setFloat("bobberTimer", "position", "scale", batFireworkScale);
						cinqdt1Mod.newModConfig.saveConfig();
						break;
					case BOBBER_TIMER:
						float bobberTimerScale = bobberTimer.getNewScaleWithX(xMoved);
						if (bobberTimerScale > 0.5f && bobberTimerScale < 6.0f)
							cinqdt1Mod.newModConfig.setFloat("bobberTimer", "position", "scale", bobberTimerScale);
						cinqdt1Mod.newModConfig.saveConfig();
						break;
					case BUNDLE_TRACKER:
						float bundleTrackerScale = bundleTracker.getNewScaleWithX(xMoved);
						if (bundleTrackerScale > 0.5f && bundleTrackerScale < 6.0f)
							cinqdt1Mod.newModConfig.setFloat("bundleTracker", "position", "scale", bundleTrackerScale);
						cinqdt1Mod.newModConfig.saveConfig();
						break;
					case FIRE_FREEZE_OVERLAY:
						float fireFreezeScale = fireFreezeOverlay.getNewScaleWithX(xMoved);
						if (fireFreezeScale > 0.5f && fireFreezeScale < 6.0f)
							cinqdt1Mod.newModConfig.setFloat("fireFreeze", "position", "scale", fireFreezeScale);
						cinqdt1Mod.newModConfig.saveConfig();
						break;
					case FIRE_PILLAR_OVERLAY:
						float firePillarScale = firePillarOverlay.getNewScaleWithX(xMoved);
						if (firePillarScale > 0.5f && firePillarScale < 6.0f)
							cinqdt1Mod.newModConfig.setFloat("firePillar", "position", "scale", firePillarScale);
						cinqdt1Mod.newModConfig.saveConfig();
						break;
					case FRAGRUN_TRACKER:
						float fragRunScale = fragRunTracker.getNewScaleWithX(xMoved);
						if (fragRunScale > 0.5f && fragRunScale < 6.0f)
							cinqdt1Mod.newModConfig.setFloat("fragRunTracker", "position", "scale", fragRunScale);
						cinqdt1Mod.newModConfig.saveConfig();
						break;
					case ENDERMAN_PET_TRACKER:
						float endermanPetTrackerScale = endermanPetTracker.getNewScaleWithX(xMoved);
						if (endermanPetTrackerScale > 0.5f && endermanPetTrackerScale < 6.0f)
							cinqdt1Mod.newModConfig.setFloat("endermanPetTracker", "position", "scale", endermanPetTrackerScale);
						cinqdt1Mod.newModConfig.saveConfig();
						break;
					case LOWEST_HP_SUMMON_DISPLAY:
						float lowestHpSummonScale = lowestHpSummonDisplay.getNewScaleWithX(xMoved);
						if (lowestHpSummonScale > 0.5f && lowestHpSummonScale < 6.0f)
							cinqdt1Mod.newModConfig.setFloat("lowestHpSummon", "position", "scale", lowestHpSummonScale);
						cinqdt1Mod.newModConfig.saveConfig();
						break;
					case MYTHOLOGICAL_HP:
						float mythologicalHpScale = mythologicalHp.getNewScaleWithX(xMoved);
						if (mythologicalHpScale > 0.5f && mythologicalHpScale < 6.0f)
							cinqdt1Mod.newModConfig.setFloat("mythologicalHp", "position", "scale", mythologicalHpScale);
						cinqdt1Mod.newModConfig.saveConfig();
						break;
					case SCAVENGED_STATS:
						float scavengedStatsScale = scavengedStats.getNewScaleWithX(xMoved);
						if (scavengedStatsScale > 0.5f && scavengedStatsScale < 6.0f)
							cinqdt1Mod.newModConfig.setFloat("scavengedStats", "position", "scale", scavengedStatsScale);
						cinqdt1Mod.newModConfig.saveConfig();
						break;
					case XP_RUN_TRACKER:
						float xpRunScale = xpRunTracker.getNewScaleWithX(xMoved);
						if (xpRunScale > 0.5f && xpRunScale < 6.0f)
							cinqdt1Mod.newModConfig.setFloat("xpRunTracker", "position", "scale", xpRunScale);
						cinqdt1Mod.newModConfig.saveConfig();
						break;
				}
				this.buttonList.clear();
				initGui();
			}else if(buttonAction == Action.MOVE) {
				switch (buttonPerformed) {
					case BAT_FIREWORK:
						double xValueBatFirework = cinqdt1Mod.newModConfig.getDouble("batFirework", "position", "x");
						double yValueBatFirework = cinqdt1Mod.newModConfig.getDouble("batFirework", "position", "y");
						cinqdt1Mod.newModConfig.setDouble("batFirework", "position", "x", xValueBatFirework + xMoved);
						cinqdt1Mod.newModConfig.setDouble("batFirework", "position", "y", yValueBatFirework + yMoved);
						cinqdt1Mod.newModConfig.saveConfig();
						break;
					case BOBBER_TIMER:
						double xValueBobberTimer = cinqdt1Mod.newModConfig.getDouble("bobberTimer", "position", "x");
						double yValueBobberTimer = cinqdt1Mod.newModConfig.getDouble("bobberTimer", "position", "y");
						cinqdt1Mod.newModConfig.setDouble("bobberTimer", "position", "x", xValueBobberTimer + xMoved);
						cinqdt1Mod.newModConfig.setDouble("bobberTimer", "position", "y", yValueBobberTimer + yMoved);
						cinqdt1Mod.newModConfig.saveConfig();
						break;
					case BUNDLE_TRACKER:
						double xValueBundleTracker = cinqdt1Mod.newModConfig.getDouble("bundleTracker", "position", "x");
						double yValueBundleTracker = cinqdt1Mod.newModConfig.getDouble("bundleTracker", "position", "y");
						cinqdt1Mod.newModConfig.setDouble("bundleTracker", "position", "x", xValueBundleTracker + xMoved);
						cinqdt1Mod.newModConfig.setDouble("bundleTracker", "position", "y", yValueBundleTracker + yMoved);
						cinqdt1Mod.newModConfig.saveConfig();
						break;
					case FIRE_FREEZE_OVERLAY:
						double xValueFireFreeze = cinqdt1Mod.newModConfig.getDouble("fireFreeze", "position", "x");
						double yValueFireFreeze = cinqdt1Mod.newModConfig.getDouble("fireFreeze", "position", "y");
						cinqdt1Mod.newModConfig.setDouble("fireFreeze", "position", "x", xValueFireFreeze + xMoved);
						cinqdt1Mod.newModConfig.setDouble("fireFreeze", "position", "y", yValueFireFreeze + yMoved);
						cinqdt1Mod.newModConfig.saveConfig();
						break;
					case FIRE_PILLAR_OVERLAY:
						double xValueFirePillar = cinqdt1Mod.newModConfig.getDouble("firePillar", "position", "x");
						double yValueFirePillar = cinqdt1Mod.newModConfig.getDouble("firePillar", "position", "y");
						cinqdt1Mod.newModConfig.setDouble("firePillar", "position", "x", xValueFirePillar + xMoved);
						cinqdt1Mod.newModConfig.setDouble("firePillar", "position", "y", yValueFirePillar + yMoved);
						cinqdt1Mod.newModConfig.saveConfig();
						break;
					case FRAGRUN_TRACKER:
						double xValueFragRunTracker = cinqdt1Mod.newModConfig.getDouble("fragRunTracker", "position", "x");
						double yValueFragRunTracker = cinqdt1Mod.newModConfig.getDouble("fragRunTracker", "position", "y");
						cinqdt1Mod.newModConfig.setDouble("fragRunTracker", "position", "x", xValueFragRunTracker + xMoved);
						cinqdt1Mod.newModConfig.setDouble("fragRunTracker", "position", "y", yValueFragRunTracker + yMoved);
						cinqdt1Mod.newModConfig.saveConfig();
						break;
					case ENDERMAN_PET_TRACKER:
						double xValueEndermanPetTracker = cinqdt1Mod.newModConfig.getDouble("endermanPetTracker", "position", "x");
						double yValueEndermanPetTracker = cinqdt1Mod.newModConfig.getDouble("endermanPetTracker", "position", "y");
						cinqdt1Mod.newModConfig.setDouble("endermanPetTracker", "position", "x", xValueEndermanPetTracker + xMoved);
						cinqdt1Mod.newModConfig.setDouble("endermanPetTracker", "position", "y", yValueEndermanPetTracker + yMoved);
						cinqdt1Mod.newModConfig.saveConfig();
						break;
					case LOWEST_HP_SUMMON_DISPLAY:
						double xValueLowestHpSummon = cinqdt1Mod.newModConfig.getDouble("lowestHpSummon", "position", "x");
						double yValueLowestHpSummon = cinqdt1Mod.newModConfig.getDouble("lowestHpSummon", "position", "y");
						cinqdt1Mod.newModConfig.setDouble("lowestHpSummon", "position", "x", xValueLowestHpSummon + xMoved);
						cinqdt1Mod.newModConfig.setDouble("lowestHpSummon", "position", "y", yValueLowestHpSummon + yMoved);
						cinqdt1Mod.newModConfig.saveConfig();
						break;
					case MYTHOLOGICAL_HP:
						double xValueMythologicalHp = cinqdt1Mod.newModConfig.getDouble("mythologicalHp", "position", "x");
						double yValueMythologicalHp = cinqdt1Mod.newModConfig.getDouble("mythologicalHp", "position", "y");
						cinqdt1Mod.newModConfig.setDouble("mythologicalHp", "position", "x", xValueMythologicalHp + xMoved);
						cinqdt1Mod.newModConfig.setDouble("mythologicalHp", "position", "y", yValueMythologicalHp + yMoved);
						cinqdt1Mod.newModConfig.saveConfig();
						break;
					case SCAVENGED_STATS:
						double xValueScavengedStats = cinqdt1Mod.newModConfig.getDouble("scavengedStats", "position", "x");
						double yValueScavengedStats = cinqdt1Mod.newModConfig.getDouble("scavengedStats", "position", "y");
						cinqdt1Mod.newModConfig.setDouble("scavengedStats", "position", "x", xValueScavengedStats + xMoved);
						cinqdt1Mod.newModConfig.setDouble("scavengedStats", "position", "y", yValueScavengedStats + yMoved);
						cinqdt1Mod.newModConfig.saveConfig();
						break;
					case XP_RUN_TRACKER:
						double xValueXpRunTracker = cinqdt1Mod.newModConfig.getDouble("xpRunTracker", "position", "x");
						double yValueXpRunTracker = cinqdt1Mod.newModConfig.getDouble("xpRunTracker", "position", "y");
						cinqdt1Mod.newModConfig.setDouble("xpRunTracker", "position", "x", xValueXpRunTracker + xMoved);
						cinqdt1Mod.newModConfig.setDouble("xpRunTracker", "position", "y", yValueXpRunTracker + yMoved);
						cinqdt1Mod.newModConfig.saveConfig();
						break;
				}
				this.buttonList.clear();
				initGui();
			}
			lastMouseX = mouseX;
			lastMouseY = mouseY;
		}catch (Exception e){
			e.printStackTrace();
		}
	}
	@Override
	public void drawScreen(int mouseX, int mouseY, float partialTicks) {
		this.drawDefaultBackground();
		mouseMoved(mouseX, mouseY);
		super.drawScreen(mouseX, mouseY, partialTicks);

    }
	
	public enum FeatureButton{
		BAT_FIREWORK,
		BOBBER_TIMER,
		BUNDLE_TRACKER,
		FIRE_FREEZE_OVERLAY,
		FIRE_PILLAR_OVERLAY,
		FRAGRUN_TRACKER,
		ENDERMAN_PET_TRACKER,
		LOWEST_HP_SUMMON_DISPLAY,
		MYTHOLOGICAL_HP,
		SCAVENGED_STATS,
		XP_RUN_TRACKER,
		NONE
	}
	
	@Override
	public void actionPerformed(GuiButton button) {
		if(button instanceof GuiFeatureButton){
			buttonAction = Action.MOVE;
			buttonPerformed = ((GuiFeatureButton) button).feature;
		}else if(button instanceof GuiFeatureButtonResizable){
			buttonAction = Action.RESIZE;
			buttonPerformed = ((GuiFeatureButtonResizable) button).feature;
		}
	}
	
	@Override
	public void mouseReleased(int mouseX, int mouseY, int state) {
		super.mouseReleased(mouseX, mouseY, state);
		buttonAction = Action.NONE;
		buttonPerformed = FeatureButton.NONE;
	}
	
	@Override
	public void onGuiClosed()
    {
		EssentialAPI.getGuiUtil().openScreen(cinqdt1Mod.instance.config.gui());
    }
}
