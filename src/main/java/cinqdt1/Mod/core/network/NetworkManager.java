package cinqdt1.Mod.core.network;

import cinqdt1.Mod.core.handlers.CustomNetworkPacketHandler;
import cinqdt1.Mod.core.network.codecs.PacketDecoder;
import cinqdt1.Mod.core.network.codecs.PacketEncoder;
import cinqdt1.Mod.core.network.packets.BasePacket;
import cinqdt1.Mod.core.network.packets.KeepConnectedPacket;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

public class NetworkManager {
    final String HOST = "127.0.0.1";
    final int PORT = 1567;
    private Channel channel;
    private State connexionState = State.DISCONNECTED;
    private int tick = 0;

    public void initConnexion(){
        NioEventLoopGroup group = new NioEventLoopGroup();
        try {
            Bootstrap bootstrap = new Bootstrap();
            bootstrap.group(group);
            bootstrap.channel(NioSocketChannel.class);
            bootstrap.handler(new ChannelInitializer<SocketChannel>() {
                @Override
                public void initChannel(SocketChannel ch) {
                    ch.pipeline().addLast(new PacketDecoder(), new PacketEncoder(), new CustomNetworkPacketHandler());
                }
            });
            this.channel = bootstrap.connect(HOST, PORT).sync().channel();
            this.connexionState = State.CONNECTED;
            this.channel.closeFuture().sync();
            this.connexionState = State.DISCONNECTED;
            this.tick = 0;
        }catch (Exception ignored){
        }finally {
            group.shutdownGracefully();
            this.connexionState = State.DISCONNECTED;
            this.tick = 0;
        }
    }

    @SubscribeEvent
    public void onTick(TickEvent.ClientTickEvent event){
        if(event.phase != TickEvent.Phase.START) return;
        if(this.connexionState != State.CONNECTED) return;
        this.tick++;
        if(this.tick % 200 == 0){
            sendPacket(new KeepConnectedPacket());
        }
    }

    public void sendPacket(BasePacket packet){
        this.channel.writeAndFlush(packet);
    }
    public State getConnexionState(){
        return this.connexionState;
    }

    public enum State{
        CONNECTED,
        DISCONNECTED
    }
}
