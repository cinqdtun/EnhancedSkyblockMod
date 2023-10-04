package cinqdt1.Mod.features;

import cinqdt1.Mod.config.ModConfiguration;
import cinqdt1.Mod.utils.Utils;
import net.minecraft.network.play.server.S45PacketTitle;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.IChatComponent;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.ReflectionHelper;

import java.lang.reflect.Field;

public class EnhancedChat {

    static Field messageField = ReflectionHelper.findField(S45PacketTitle.class, "message", "field_179810_b", "b");

    // https://github.com/Moulberry/Hychat/blob/master/src/main/java/io/github/moulberry/hychat/util/TextProcessing.java#L23
    private static IChatComponent appendColors(IChatComponent component) {
        IChatComponent newComponent;
        ChatComponentText text = (ChatComponentText) component;

        newComponent = new ChatComponentText(text.getUnformattedTextForChat().replaceAll("&([0-9a-fk-or])", "§$1"));
        newComponent.setChatStyle(text.getChatStyle().createShallowCopy());

        for (IChatComponent sibling : text.getSiblings()) {
            newComponent.appendSibling(appendColors(sibling));
        }

        return newComponent;
    }

    @SubscribeEvent
    public void onChat(ClientChatReceivedEvent event) {
        if (!Utils.inSkyblock || !ModConfiguration.enhancedChatColor) return;
        if (event.type == 2) return;

        String text = event.message.getFormattedText();
        event.message = appendColors(event.message);
    }

    private enum ColorCode{

    }
}
