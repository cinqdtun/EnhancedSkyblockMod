package cinqdt1.Mod.handlers;

import cinqdt1.Mod.events.PacketEvent;
import io.netty.channel.ChannelDuplexHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelPromise;
import net.minecraft.network.Packet;
import net.minecraftforge.common.MinecraftForge;

public class PacketHandler extends ChannelDuplexHandler
{
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        if (msg instanceof Packet) {
            if (MinecraftForge.EVENT_BUS.post(new PacketEvent.Received((Packet) msg))) return;
        }

        super.channelRead(ctx, msg);
    }
    @Override
    public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {
        if (msg instanceof Packet) {
            //if (MinecraftForge.EVENT_BUS.post(new PacketWriteEvent((Packet) msg))) return;
        }

        super.write(ctx, msg, promise);
    }
}
