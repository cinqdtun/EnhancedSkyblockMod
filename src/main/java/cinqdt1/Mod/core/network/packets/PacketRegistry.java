package cinqdt1.Mod.core.network.packets;

public class PacketRegistry {
    public static byte getPacketIdByClass(Class<? extends BasePacket> packetClass){
        if(packetClass == KeepConnectedPacket.class) return 0x01;
        return (byte) 0xFF;
    }
    public static boolean isValidPacketId(byte id){
        return true;
    }
    public static Class<? extends BasePacket> getPacketClassById(byte id){
        //if(id == KeepConnectedPacket.class) return 0x01;
        return (KeepConnectedPacket.class);
    }
}
