package cinqdt1.Mod.core.network.codecs;

import cinqdt1.Mod.core.network.packets.BasePacket;
import cinqdt1.Mod.core.network.packets.PacketRegistry;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.List;

public class PacketDecoder extends ByteToMessageDecoder {
    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception{
        if(!in.isReadable(1)) return;
        byte id = in.readByte();
        if(!PacketRegistry.isValidPacketId(id)) return;
        Class<? extends BasePacket> packetClass = PacketRegistry.getPacketClassById(id);
        BasePacket packet = packetClass.newInstance();
        packet.readPacket(in);
        out.add(packet);
    }
}
