package cinqdt1.Mod.events;

import net.minecraft.network.Packet;
import net.minecraftforge.fml.common.eventhandler.Cancelable;
import net.minecraftforge.fml.common.eventhandler.Event;

public class PacketEvent extends Event {
    public Packet packet;

    public PacketEvent(Packet packet)
    {
        this.packet = packet;
    }
    /**
     * Fired when a packet is received from server
     */
    @Cancelable
    public static class Received extends PacketEvent
    {
        public Received(Packet packet)
        {
            super(packet);
        }
    }
    /**
     * Fired when a packet is sent from client
     */
    @Cancelable
    public static class Sent extends PacketEvent
    {
        public Sent(Packet packet)
        {
            super(packet);
        }
    }
}
