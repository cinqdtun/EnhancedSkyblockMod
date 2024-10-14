package com.cinqdt1.Mod;

import com.cinqdt1.Mod.commands.MainCommand;
import com.cinqdt1.Mod.commands.NotCountRun;
import com.cinqdt1.Mod.config.ModConfig;
import com.cinqdt1.Mod.config.ModConfiguration;
import com.cinqdt1.Mod.events.Event;
import com.cinqdt1.Mod.features.*;
import com.cinqdt1.Mod.features.dojohelper.DisciplineHelper;
import com.cinqdt1.Mod.utils.DisplayTitle;
import gg.essential.vigilance.Vigilance;
import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.KeyBinding;
import net.minecraftforge.client.ClientCommandHandler;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.lwjgl.input.Keyboard;

import java.io.File;

@Mod(modid = cinqdt1Mod.MOD_ID, version = cinqdt1Mod.VERSION, clientSideOnly = true, acceptedMinecraftVersions = "[1.8.9]")
public class cinqdt1Mod {
    public static final cinqdt1Mod instance = new cinqdt1Mod();
    public static final ModConfig newModConfig = new ModConfig(new File("./config/cinqdtun.cfg"));
    public static final String MOD_ID = "enhancedskyblockmod";
    public static final String VERSION = "1.0.0";
    public static final boolean devMode = true;
    public static KeyBinding[] keyBindings = new KeyBinding[3];
    public ModConfiguration config = new ModConfiguration();

    private static boolean shouldShowTitle = false;
    private static String[] titleInfos = new String[2];

	@EventHandler
	public void preInit(FMLPreInitializationEvent event) {
		ClientCommandHandler.instance.registerCommand(new MainCommand());
		ClientCommandHandler.instance.registerCommand(new NotCountRun());
	}

    public static void displayTitle(String title, String subtitle){
        titleInfos[0] = title;
        titleInfos[1] = subtitle;
        shouldShowTitle = true;
    }

	@EventHandler
	public void PostInit(FMLPostInitializationEvent event) {

	}
    /*
	@SubscribeEvent(priority = EventPriority.HIGHEST)
	public void onTick(TickEvent.ClientTickEvent event) {
		if (event.phase != Phase.START) return;
	}
     */

	@EventHandler
	public void init(FMLInitializationEvent event) {
        Vigilance.initialize();
        config.preload();
        newModConfig.init();

        MinecraftForge.EVENT_BUS.register(this);
        MinecraftForge.EVENT_BUS.register(new Event());
        MinecraftForge.EVENT_BUS.register(new AshfangFeatures());
        MinecraftForge.EVENT_BUS.register(new DisplayTitle());
        MinecraftForge.EVENT_BUS.register(new BatFirework());
        MinecraftForge.EVENT_BUS.register(new BobberTimer());
        MinecraftForge.EVENT_BUS.register(new BundleTracker());
        MinecraftForge.EVENT_BUS.register(new CopyChatMessages());
        MinecraftForge.EVENT_BUS.register(new DisciplineHelper());
        MinecraftForge.EVENT_BUS.register(new EnhancedChat());
        MinecraftForge.EVENT_BUS.register(new EndermanPetTracker());
        MinecraftForge.EVENT_BUS.register(new FastOpenSettings());
        MinecraftForge.EVENT_BUS.register(new FireFreeze());
        MinecraftForge.EVENT_BUS.register(new FirePillar());
        MinecraftForge.EVENT_BUS.register(new FixSkillOverflow());
        MinecraftForge.EVENT_BUS.register(new XpRunTracker());
        MinecraftForge.EVENT_BUS.register(new FragRunTracker());
        MinecraftForge.EVENT_BUS.register(new HighlightChestDungeons());
        MinecraftForge.EVENT_BUS.register(new MythologicalFeatures());
        MinecraftForge.EVENT_BUS.register(new SummonsFeatures());
        MinecraftForge.EVENT_BUS.register(new ScavengedStats());
        MinecraftForge.EVENT_BUS.register(new TransferCooldownHelper());
        MinecraftForge.EVENT_BUS.register(new ScathaMining());

        if(devMode){
            MinecraftForge.EVENT_BUS.register(new DevFeatures());
        }

        keyBindings[0] = new KeyBinding("Open Settings", Keyboard.KEY_C, "5dt1's Mod");
        keyBindings[2] = new KeyBinding("Copy chat key", Keyboard.KEY_P, "5dt1's Mod");
        if (devMode){
            keyBindings[1] = new KeyBinding("Debug key", Keyboard.KEY_NONE, "5dt1's Mod");
        }

        for (KeyBinding keyBinding : keyBindings) {
            if(keyBinding == null) continue;
            ClientRegistry.registerKeyBinding(keyBinding);
        }
	}

    @SubscribeEvent
    public void onRenderGameOverlay(RenderGameOverlayEvent.Post event) {
        if (event.type == RenderGameOverlayEvent.ElementType.ALL) {
            // Check conditions for showing the title
            if (shouldShowTitle) {
                shouldShowTitle = false;
                Minecraft.getMinecraft().ingameGUI.displayTitle(titleInfos[0], titleInfos[1], 5, 50,5);
            }
        }
    }
}
