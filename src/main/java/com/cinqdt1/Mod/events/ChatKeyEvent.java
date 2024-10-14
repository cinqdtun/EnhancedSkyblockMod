package com.cinqdt1.Mod.events;

import net.minecraftforge.fml.common.eventhandler.Cancelable;
import net.minecraftforge.fml.common.eventhandler.Event;
public class ChatKeyEvent extends Event {
    public char typedChar;
    public int keyCode;
    public ChatKeyEvent(char typedChar, int keyCode){
        this.typedChar = typedChar;
        this.keyCode = keyCode;
    }

    @Cancelable
    public static class Press extends ChatKeyEvent{
        public Press(char typedChar, int keyCode){
            super(typedChar, keyCode);
        }
    }
}
