package cinqdt1.Mod.events;

import net.minecraftforge.fml.common.eventhandler.Cancelable;
import net.minecraftforge.fml.common.eventhandler.Event;

public class ChatEvent extends Event {
    public String message;
    public ChatEvent(String message){
        this.message = message;
    }

    @Cancelable
    public static class Sent extends ChatEvent{
        public Sent(String message){
            super(message);
        }
    }
}
