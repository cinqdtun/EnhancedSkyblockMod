package cinqdt1.Mod.features;

import cinqdt1.Mod.cinqdt1Mod;
import cinqdt1.Mod.config.ModConfiguration;
import cinqdt1.Mod.core.handlers.CustomNetworkPacketHandler;
import cinqdt1.Mod.core.network.NetworkManager;
import cinqdt1.Mod.core.network.packets.KeepConnectedPacket;
import cinqdt1.Mod.gui.PartyGUI;
import cinqdt1.Mod.utils.Utils;
import io.netty.channel.ChannelHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.init.Items;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent;

import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;

public class DevFeatures {
    private final Minecraft mc = Minecraft.getMinecraft();
    NetworkManager networkManager;
    public DevFeatures(){
        networkManager = new NetworkManager();
    }
    @SubscribeEvent
    public void onKey(InputEvent.KeyInputEvent event){
        if(cinqdt1Mod.keyBindings[1].isPressed()) {
            Minecraft.getMinecraft().displayGuiScreen(new PartyGUI());
           // if(cinqdt1Mod.networkManager.getConnexionState() != NetworkManager.State.CONNECTED) return;
            //cinqdt1Mod.networkManager.sendPacket(new KeepConnectedPacket());
            switch(ModConfiguration.debugKeyAction){
                case 0:
                    if(mc.thePlayer == null) return;
                    if(mc.thePlayer.getHeldItem() == null) return;
                    if(mc.thePlayer.getHeldItem().getItem() != Items.skull) return;
                    StringSelection content = new StringSelection(Utils.getHeadTexture(mc.thePlayer.getHeldItem()));
                    Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
                    clipboard.setContents(content, content);
            }
        }
    }
    public void onTooltip(ItemTooltipEvent event){
        if(event.itemStack.getItem() != Items.skull) return;
        if(!cinqdt1Mod.keyBindings[1].isPressed()) return;
        if(ModConfiguration.debugKeyAction > 0) return;
        String texture = Utils.getHeadTexture(event.itemStack);
        if(texture == null)return;
        StringSelection content = new StringSelection(texture);
        Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
        clipboard.setContents(content, content);
    }
}
