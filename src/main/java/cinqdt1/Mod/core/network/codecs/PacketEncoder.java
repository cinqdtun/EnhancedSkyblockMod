package cinqdt1.Mod.core.network.codecs;

import cinqdt1.Mod.core.network.packets.BasePacket;
import cinqdt1.Mod.core.network.packets.PacketRegistry;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

public class PacketEncoder extends MessageToByteEncoder<BasePacket> {

    @Override
    protected void encode(ChannelHandlerContext ctx, BasePacket packet, ByteBuf out){
        byte packetId = PacketRegistry.getPacketIdByClass(packet.getClass());
        out.writeByte(packetId);
        packet.writePacket(out);
    }
}
