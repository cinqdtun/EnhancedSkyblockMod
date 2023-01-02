package cinqdt1.Mod.core.renderer;

import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.entity.RenderZombie;
import net.minecraft.entity.monster.EntityZombie;


public class ZombieNoLayersRender extends RenderZombie{
    public ZombieNoLayersRender(RenderManager renderManager) {
        super(renderManager);
    }

    @Override
    protected void renderLayers(EntityZombie entitylivingbaseIn, float p_177093_2_, float p_177093_3_, float partialTicks, float p_177093_5_, float p_177093_6_, float p_177093_7_, float p_177093_8_){
        ;
    }
}
