package cinqdt1.Mod.events;

import net.minecraft.client.renderer.entity.Render;
import net.minecraft.entity.EntityLivingBase;
import net.minecraftforge.fml.common.eventhandler.Cancelable;

public class RenderLivingEvent<T extends EntityLivingBase> extends Event{
    public final EntityLivingBase entity;
    public final Render<T> render;
    public final double x;
    public final double y;
    public final double z;

    public RenderLivingEvent(EntityLivingBase entity, Render<T> render, double x, double y, double z){
        this.entity = entity;
        this.render = render;
        this.x = x;
        this.y = y;
        this.z = z;
    }
    @Cancelable
    public static class Pre<T extends EntityLivingBase> extends CustomRenderLivingEntity<T>
    {
        public Pre(EntityLivingBase entity, Render<T> render, double x, double y, double z)
        {
            super(entity, render, x, y, z);
        }
    }
    public static class Post<T extends EntityLivingBase> extends CustomRenderLivingEntity<T>
    {
        public Post(EntityLivingBase entity, Render<T> render, double x, double y, double z)
        {
            super(entity, render, x, y, z);
        }
    }
}
