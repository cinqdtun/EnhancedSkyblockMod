package cinqdt1.Mod.core.network.packets;

import io.netty.buffer.ByteBuf;

public interface BasePacket{
    void readPacket(ByteBuf buf);
    void writePacket(ByteBuf buf);
}
