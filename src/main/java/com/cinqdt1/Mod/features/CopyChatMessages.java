package com.cinqdt1.Mod.features;

import com.cinqdt1.Mod.cinqdt1Mod;
import com.cinqdt1.Mod.config.ModConfiguration;
import com.cinqdt1.Mod.events.ChatKeyEvent;
import com.cinqdt1.Mod.utils.Utils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.IChatComponent;
import net.minecraft.util.StringUtils;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.lwjgl.input.Mouse;

import java.awt.*;
import java.awt.datatransfer.StringSelection;

public class CopyChatMessages {

    /*@SubscribeEvent(priority = EventPriority.LOW)
    public void onReceivedMessage(ClientChatReceivedEvent event){
        if (event.type == 2) return;
        if(!ModConfiguration.copyMessageState) return;
        HoverEvent hoverMessage = event.message.getChatStyle().getChatHoverEvent();
        if(hoverMessage != null && !ModConfiguration.neuOverridePvPlayerMessagesState){
            return;
        }else if(hoverMessage != null){
            if(!event.message.getChatStyle().getChatClickEvent().getValue().startsWith("/pv") || event.message.getChatStyle().getChatClickEvent().getAction() != ClickEvent.Action.RUN_COMMAND) return;
        }
        String unformattedMessage = StringUtils.stripControlCodes(event.message.getUnformattedText());
        event.message.getChatStyle().setChatHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ChatComponentText(EnumChatFormatting.GRAY + "Click to copy message to clipboard")));
        event.message.getChatStyle().setChatClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND,"/cinqdtuncopy " + unformattedMessage));
    }

    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public void onSendMessage(ChatEvent.Sent event){
        if(!(event.message.startsWith("/cinqdtuncopy"))) return;
        event.setCanceled(true);
        if(event.message.length() < 14) return;
        StringSelection clipContent = new StringSelection(event.message.substring(14));
        Toolkit.getDefaultToolkit().getSystemClipboard().setContents(clipContent, null);
    }*/

    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public void onChatKeyPress(ChatKeyEvent event) {
        Minecraft mc = Minecraft.getMinecraft();
        ScaledResolution scaledresolution = new ScaledResolution(mc);
        int i = scaledresolution.getScaleFactor();
        int x = Mouse.getEventX() / i;
        int y = (mc.displayHeight - Mouse.getEventY()) / i;
        if (ModConfiguration.copyMessageState && event.keyCode == cinqdt1Mod.keyBindings[2].getKeyCode()) {

            IChatComponent message_component = mc.ingameGUI.getChatGUI().getChatComponent(
                    x,
                    y
            );
            if (message_component == null)
            {
                Utils.sendModMessage(EnumChatFormatting.RED + "No message found at cursor at " + x + ", " + y);
                return;
            }
            String cleaned_message = StringUtils.stripControlCodes(message_component.getUnformattedText());
            StringSelection clipContent = new StringSelection(cleaned_message);
            //Utils.sendModMessage(EnumChatFormatting.GRAY + "Copied message to clipboard");
            Toolkit.getDefaultToolkit().getSystemClipboard().setContents(clipContent, null);
            event.setCanceled(true);
        }
    }

}
