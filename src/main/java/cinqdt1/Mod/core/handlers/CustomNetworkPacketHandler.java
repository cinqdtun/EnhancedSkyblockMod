package cinqdt1.Mod.core.handlers;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
public class CustomNetworkPacketHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelActive(ChannelHandlerContext ctx) {
        System.out.println("Channel Active");
        try {
            ctx.writeAndFlush("Hello Server");
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    @Override
    public void channelInactive(ChannelHandlerContext ctx) {
        System.out.println("Channel Inactive");
        try {
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        System.out.println("client received: " + msg);
        //ctx.writeAndFlush(msg);
    }


    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) {
        //ctx.flush();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        cause.printStackTrace();
        ctx.close();
    }
}
