package cinqdt1.Mod.config;

import cinqdt1.Mod.cinqdt1Mod;
import gg.essential.vigilance.Vigilant;
import gg.essential.vigilance.data.Category;
import gg.essential.vigilance.data.JVMAnnotationPropertyCollector;
import gg.essential.vigilance.data.Property;
import gg.essential.vigilance.data.PropertyType;
import gg.essential.vigilance.data.SortingBehavior;
import kotlin.comparisons.ComparisonsKt;
import kotlin.jvm.internal.Intrinsics;
import net.minecraft.client.Minecraft;
import cinqdt1.Mod.gui.EditLocations;

import java.io.File;
import java.util.Comparator;

import org.jetbrains.annotations.NotNull;

public class ModConfiguration extends Vigilant {
	@Property(
			type = PropertyType.BUTTON,
			name = "Edit GUI locations",
			description = "Allow you to edit gui locations",
			placeholder = "Edit",
			category = "General",
			subcategory = "GUI"
	)
	public void openLocationGui() {
		
		 Minecraft.getMinecraft().displayGuiScreen(new EditLocations());
	}
	@Property(
			type = PropertyType.TEXT,
			name = "Api Key",
			description = "Put your apikey for using some features of the mod",
			placeholder = "Edit",
			category = "General",
			subcategory = "Api"
	)
	public static String apiKey = "";
	@Property(
			type = PropertyType.SWITCH,
			name = "Bobber Timer",
			description = "Show the bobber timer when you fishing in Crimson Isle",
			category = "Fishing",
			subcategory = "Display"
	)
	public static boolean bobberTimerOnCrimsonIsle = false;

	@Property(
			type = PropertyType.SWITCH,
			name = "Frag Run Tracker",
			description = "Track all loot you gain and count each version of giant you get",
			category = "Dungeon",
			subcategory = "FragRun"
	)
	public static boolean fragRunTrackerForF7 = false;
	@Property(
			type = PropertyType.SWITCH,
			name = "Show advanced stats",
			description = "Show advanced stats for drops of giants and versions of giants",
			category = "Dungeon",
			subcategory = "FragRun"
	)
	public static boolean advancedStatsForFragRunTrackerForF7 = false;

	@Property(
			type = PropertyType.BUTTON,
			name = "Reset Fragrun Tracker",
			description = "Reset the counter of Fragrun Tracker",
			placeholder = "Reset",
			category = "Dungeon",
			subcategory = "FragRun"
	)
	public void resetFragRunTracker() {
		try {
			cinqdt1Mod.newModConfig.setInteger("fragRunTracker", "giant", "diamanteGiant", 0);
			cinqdt1Mod.newModConfig.setInteger("fragRunTracker", "giant", "LASRGiant", 0);
			cinqdt1Mod.newModConfig.setInteger("fragRunTracker", "giant", "bigFootGiant", 0);
			cinqdt1Mod.newModConfig.setInteger("fragRunTracker", "giant", "jollyPinkGiant", 0);
			cinqdt1Mod.newModConfig.setInteger("fragRunTracker", "loot", "diamanteItem", 0);
			cinqdt1Mod.newModConfig.setInteger("fragRunTracker", "loot", "LASRItem", 0);
			cinqdt1Mod.newModConfig.setInteger("fragRunTracker", "loot", "bigFootItem", 0);
			cinqdt1Mod.newModConfig.setInteger("fragRunTracker", "loot", "jollyPinkItem", 0);
			cinqdt1Mod.newModConfig.saveConfig();
		}catch (Exception e){
			e.printStackTrace();
		}
	}
	
	@Property(
			type = PropertyType.SWITCH,
			name = "Enderman Pet Tracker",
			description = "Track enderman pet drop",
			category = "End",
			subcategory = "Enderman"
	)
	public static boolean showEndermanTrackerWhileInEnd = false;
	@Property(
			type = PropertyType.BUTTON,
			name = "Reset Enderman Pet",
			description = "Reset the counter of enderman pet",
			placeholder = "Reset",
			category = "End",
			subcategory = "Enderman"
	)
	public void resetEndermanPetTracker() {
		try {
			cinqdt1Mod.newModConfig.setInteger("endermanPetTracker", "loot", "rareEndermanPet", 0);
			cinqdt1Mod.newModConfig.setInteger("endermanPetTracker", "loot", "epicEndermanPet", 0);
			cinqdt1Mod.newModConfig.setInteger("endermanPetTracker", "loot", "legendaryEndermanPet", 0);
			cinqdt1Mod.newModConfig.saveConfig();
		}catch (Exception e){
			e.printStackTrace();
		}
	}
	@Property(
			type = PropertyType.SWITCH,
			name = "F6 Xp run tracker",
			description = "Enable Xp run tracker",
			category = "Dungeon",
			subcategory = "Xp runs"
	)
	public static boolean xpRunF6 = false;
	
	@Property(
			type = PropertyType.BUTTON,
			name = "Reset F6 xp run ",
			description = "Reset the counter of F6 xp run",
			placeholder = "Reset",
			category = "Dungeon",
			subcategory = "Xp runs"
	)
	public void resetF6xpRun() {
		try {
			cinqdt1Mod.newModConfig.setInteger("xpRunTracker", "stats", "runs", 0);
			cinqdt1Mod.newModConfig.setInteger("xpRunTracker", "stats", "totalXp", 0);
		}catch (Exception e){
			e.printStackTrace();
		}
	}
	@Property(
			type = PropertyType.SWITCH,
			name = "Hide Summons",
			description = "Enable Xp run tracker",
			category = "QOL",
			subcategory = "Summons"
	)
	public static boolean hideSummons = false;
	
	@Property(
			type = PropertyType.SWITCH,
			name = "Click Through Summons",
			description = "Click through summons",
			category = "QOL",
			subcategory = "Summons"
	)
	public static boolean clickThroughSummons = false;
	
	@Property(
			type = PropertyType.SWITCH,
			name = "Lowest Summon",
			description = "Show lowest summon as overlay",
			category = "QOL",
			subcategory = "Summons"
	)
	public static boolean showLowestSummon = false;
	
	@Property(
			type = PropertyType.SWITCH ,
			name = "Terminals Bot",
			description = "Do terminals automatically",
			category = "Dungeon",
			subcategory = "Terminals"
	)
	public static boolean f7BotState = false;
	
	@Property(
			type = PropertyType.NUMBER,
			name = "Terminals Click Delay",
			description = "Delay beetween each click of bot",
			category = "Dungeon",
			subcategory = "Terminals",
			min = 100,
            max = 1000,
            increment = 25
	)
	public static int terminalsBotDelayClick = 100;

	@Property(
			type = PropertyType.SWITCH,
			name = "Show overlay",
			description = "Show overlay with additional information in NPC",
			category = "Dungeon",
			subcategory = "Dungeon Chest Deliver"
	)
	public static boolean unopenedChestsOverlay = false;

	@Property(
			type = PropertyType.SWITCH,
			name = "Highlight unopened chests",
			description = "Highlight unopened chests in the NPC",
			category = "Dungeon",
			subcategory = "Dungeon Chest Deliver"
	)
	public static boolean unopenedChestsHighlight = false;

	@Property(
			type = PropertyType.SWITCH,
			name = "Show true overflow",
			description = "Show the correct amount of overflow xp on the skill menu",
			category = "Misc",
			subcategory = "Skill"
	)
	public static boolean trueOverflow = false;

	@Property(
			type = PropertyType.SWITCH,
			name = "Bundle tracker",
			description = "Track all loot you get from crystal loot bundle",
			category = "Crystal Hollow",
			subcategory = "Bundle"
	)
	public static boolean bundleTrackerState = false;
	@Property(
			type = PropertyType.BUTTON,
			name = "Reset Bundle tracker",
			description = "Reset crystal loot bundle tracker",
			category = "Crystal Hollow",
			subcategory = "Bundle",
			placeholder = "Reset"
	)
	public void resetBundleStats(){
		try{
			cinqdt1Mod.newModConfig.setInteger("bundleTracker", "loot", "bobomb", 0);
			cinqdt1Mod.newModConfig.setInteger("bundleTracker", "loot", "pickonimbus2000", 0);
			cinqdt1Mod.newModConfig.setInteger("bundleTracker", "loot", "prehistoricEgg", 0);
			cinqdt1Mod.newModConfig.setInteger("bundleTracker", "loot", "divanFragment", 0);
			cinqdt1Mod.newModConfig.setInteger("bundleTracker", "loot", "recallPotion", 0);
			cinqdt1Mod.newModConfig.setInteger("bundleTracker", "loot", "jaderald", 0);
			cinqdt1Mod.newModConfig.setInteger("bundleTracker", "loot", "divanAlloy", 0);
			cinqdt1Mod.newModConfig.setInteger("bundleTracker", "loot", "fortuneIV", 0);
			cinqdt1Mod.newModConfig.setInteger("bundleTracker", "loot", "quickClaw", 0);
			cinqdt1Mod.newModConfig.setInteger("bundleTracker", "loot", "gemstoneMixture", 0);
			cinqdt1Mod.newModConfig.setInteger("bundleTracker", "runs", "bundleNumber", 0);
		}catch (Exception e){
			e.printStackTrace();
		}
	}

	@Property(
			type = PropertyType.SWITCH,
			name = "Scavenged Stats",
			description = "Scavenged misc infos overlay",
			category = "Crystal Hollow",
			subcategory = "Divan"
	)
	public static boolean scavengedStatsState = false;

	@Property(
			type = PropertyType.SWITCH,
			name = "Start / Stop tracking",
			description = "Scavenged tracking stats",
			category = "Crystal Hollow",
			subcategory = "Divan"
	)
	public static boolean scavengedStatsTrackState = false;

	@Property(
			type = PropertyType.BUTTON,
			name = "Reset scavenged stats",
			description = "Reset scavenged stats",
			category = "Crystal Hollow",
			subcategory = "Divan",
			placeholder = "Reset"
	)
	public void resetScavengedStats(){
		try {
			cinqdt1Mod.newModConfig.setInteger("scavengedStats", "loot", "piece", 0);
			cinqdt1Mod.newModConfig.setInteger("scavengedStats", "stats", "time", 0);
		}catch (Exception e){
			e.printStackTrace();
		}
	}
	@Property(
			type = PropertyType.SWITCH,
			name = "Fire Freeze Countdown",
			description = "Show Fire Freeze Staff Countdown overlay",
			category = "Dungeon",
			subcategory = "F3 / M3"
	)
	public static boolean fireFreezeOverlayState = false;
	@Property(
			type = PropertyType.SELECTOR,
			name = "Action of debug key",
			description = "Bind debug key to an action",
			category = "Dev",
			subcategory = "Key Action",
			options = {"Get head texture in hand"},
			hidden = !cinqdt1Mod.devMode
	)
	public static int debugKeyAction = 0;

	@Property(
			type = PropertyType.SWITCH,
			name = "Timing solver",
			description = "Timing Solver",
			category = "Dev",
			subcategory = "Terms"
	)
	public static boolean timingSolverState = false;
	@Property(
			type = PropertyType.SWITCH,
			name = "Fire Pillar Overlay",
			description = "Show an overlay who show time left for pillar when boss summon a pillar(enhanced detection than others mods because he show only your pillar)",
			category = "Slayer",
			subcategory = "Inferno Demonlord"
	)
	public static boolean firePillarOverlayState = false;
	@Property(
			type = PropertyType.SWITCH,
			name = "Hide player in arachne",
			description = "Hide players",
			category = "Dev",
			subcategory = "Spider's"
	)
	public static boolean arachneHidePlayerState = false;

	public ModConfiguration() {
        super(new File("./config/5dt1's Mod.toml"), "5dt1's Mod", new JVMAnnotationPropertyCollector(), ConfigSorting.INSTANCE );
        try {
        	initialize();
        }catch (Exception ex) {
			ex.printStackTrace();
		}
    }
    private static final class ConfigSorting extends SortingBehavior {
        @NotNull
        public static final ConfigSorting INSTANCE = new ConfigSorting();

        @NotNull
		@Override
        public Comparator<? super Category> getCategoryComparator() {

            return ConfigSorting::getCategoryComparator;
        }

        private static int getCategoryComparator(Category o1, Category o2) {
			if (Intrinsics.areEqual(o1.getName(), "Dev")) return -1;
			if (Intrinsics.areEqual(o2.getName(), "Dev")) return 1;
            if (Intrinsics.areEqual(o1.getName(), "General") && !Intrinsics.areEqual(o2.getName(), "Dev")) return -1;
            if (Intrinsics.areEqual(o2.getName(), "General") && !Intrinsics.areEqual(o1.getName(), "Dev")) return 1;
            return ComparisonsKt.compareValues(o1.getName(), o2.getName());
        }
    }
}