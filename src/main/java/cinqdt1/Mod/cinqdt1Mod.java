package cinqdt1.Mod;

import java.io.File;

import cinqdt1.Mod.config.ModConfig;
import org.lwjgl.input.Keyboard;

import cinqdt1.Mod.commands.*;
import cinqdt1.Mod.config.ModConfiguration;
import cinqdt1.Mod.events.*;
import cinqdt1.Mod.features.*;
import cinqdt1.Mod.utils.DisplayTitle;
import gg.essential.vigilance.Vigilance;
import net.minecraft.client.settings.KeyBinding;
import net.minecraftforge.client.ClientCommandHandler;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.Phase;
import scala.collection.parallel.ParIterableLike;

@Mod(modid = cinqdt1Mod.MOD_ID, version = cinqdt1Mod.VERSION, clientSideOnly = true, acceptedMinecraftVersions = "[1.8.9]")
public class cinqdt1Mod {
    public static final cinqdt1Mod instance = new cinqdt1Mod();
    public ModConfiguration config = new ModConfiguration();
    public static final ModConfig newModConfig = new ModConfig(new File("./config/cinqdtun.cfg"));
    public static KeyBinding[] keyBindings = new KeyBinding[2];
    public static final String MOD_ID = "cinqdtunmod";
    public static final String VERSION = "1.0.0";
    public static final boolean devMode = true;

	@EventHandler
	public void preInit(FMLPreInitializationEvent event) {
		ClientCommandHandler.instance.registerCommand(new MainCommand());
		ClientCommandHandler.instance.registerCommand(new NotCountRun());
	}

	@EventHandler
	public void init(FMLInitializationEvent event) {
        Vigilance.initialize();
        config.preload();
        newModConfig.init();

        MinecraftForge.EVENT_BUS.register(this);
        MinecraftForge.EVENT_BUS.register(new Event());
        MinecraftForge.EVENT_BUS.register(new DisplayTitle());
        MinecraftForge.EVENT_BUS.register(new BobberTimer());
        MinecraftForge.EVENT_BUS.register(new BundleTracker());
        MinecraftForge.EVENT_BUS.register(new EndermanPetTracker());
        MinecraftForge.EVENT_BUS.register(new FastOpenSettings());
        MinecraftForge.EVENT_BUS.register(new FireFreeze());
        MinecraftForge.EVENT_BUS.register(new FirePillar());
        MinecraftForge.EVENT_BUS.register(new FixSkillOverflow());
        MinecraftForge.EVENT_BUS.register(new XpRunTracker());
        MinecraftForge.EVENT_BUS.register(new FragRunTracker());
        MinecraftForge.EVENT_BUS.register(new HighlightChestDungeons());
        MinecraftForge.EVENT_BUS.register(new SummonsFeatures());
        MinecraftForge.EVENT_BUS.register(new ScavengedStats());
        if(devMode){
            MinecraftForge.EVENT_BUS.register(new DevFeatures());
        }

        keyBindings[0] = new KeyBinding("Open Settings", Keyboard.KEY_C, "5dt1's Mod");
        if (devMode){
            keyBindings[1] = new KeyBinding("Debug key", Keyboard.KEY_NONE, "5dt1's Mod");
        }
        
        for (KeyBinding keyBinding : keyBindings) {
            if(keyBinding == null) continue;
            ClientRegistry.registerKeyBinding(keyBinding);
        }
	}

	@EventHandler
	public void PostInit(FMLPostInitializationEvent event) {

	}
	@SubscribeEvent(priority = EventPriority.HIGHEST)
	public void onTick(TickEvent.ClientTickEvent event) {
		if (event.phase != Phase.START) return;
	}
}
