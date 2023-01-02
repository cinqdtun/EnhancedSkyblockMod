package cinqdt1.Mod.core;

import cinqdt1.Mod.core.renderer.ZombieNoLayersRender;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraftforge.fml.client.registry.RenderingRegistry;

public class ClientProxy {
    public void init() {
        RenderingRegistry.registerEntityRenderingHandler(EntityZombie.class, ZombieNoLayersRender::new);
    }
}
