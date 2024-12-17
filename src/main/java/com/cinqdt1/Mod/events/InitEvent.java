package com.cinqdt1.Mod.events;

import net.minecraftforge.fml.common.eventhandler.Event;

public class InitEvent extends Event {
    public InitEvent() {}

    public static class Pre extends InitEvent {
        public Pre() {
            super();
        }
    }
}
