package com.cinqdt1.Mod.features;

import com.cinqdt1.Mod.config.ModConfiguration;
import com.cinqdt1.Mod.events.ChatEvent;
import net.minecraft.event.ClickEvent;
import net.minecraft.event.HoverEvent;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.StringUtils;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.awt.*;
import java.awt.datatransfer.StringSelection;

public class CopyChatMessages {

    @SubscribeEvent(priority = EventPriority.LOW)
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
    }

}
