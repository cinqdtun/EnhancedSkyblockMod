package cinqdt1.Mod.core.renderer;

import cinqdt1.Mod.cinqdt1Mod;
import cinqdt1.Mod.core.Color;
import cinqdt1.Mod.events.CustomRenderLivingEntity;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.ArrayList;
import java.util.List;

public class RenderManager {
    private List<RenderData<? extends EntityLivingBase>> renderEntitiesQueue = new ArrayList<>();
    private boolean isRendering = false;

    @SubscribeEvent
    public void onRenderWorldLast(RenderWorldLastEvent event){
        for(int i = 0; i < renderEntitiesQueue.size(); i++){
            RenderData<? extends EntityLivingBase> renderData = renderEntitiesQueue.get(i);
            Entity renderViewEntity = cinqdt1Mod.mc.getRenderViewEntity();
            double xViewEntity = renderViewEntity.lastTickPosX + (renderViewEntity.posX - renderViewEntity.lastTickPosX) * (double)event.partialTicks;
            double yViewEntity = renderViewEntity.lastTickPosY + (renderViewEntity.posY - renderViewEntity.lastTickPosY) * (double)event.partialTicks;
            double zViewEntity = renderViewEntity.lastTickPosZ + (renderViewEntity.posZ - renderViewEntity.lastTickPosZ) * (double)event.partialTicks;
            double xEntity = renderData.entity.lastTickPosX + (renderData.entity.posX - renderData.entity.lastTickPosX) * (double)event.partialTicks;
            double yEntity = renderData.entity.lastTickPosY + (renderData.entity.posY - renderData.entity.lastTickPosY) * (double)event.partialTicks;
            double zEntity = renderData.entity.lastTickPosZ + (renderData.entity.posZ - renderData.entity.lastTickPosZ) * (double)event.partialTicks;
            double x = xEntity - xViewEntity;
            double y = yEntity - yViewEntity;
            double z = zEntity - zViewEntity;
            if (MinecraftForge.EVENT_BUS.post(new CustomRenderLivingEntity.Pre(renderData.entity, renderData.renderer, x, y, z))) return;
            if(!this.preRender(renderData)) continue;
            this.isRendering = true;
            renderData.renderer.doRender(renderData.entity, x, y, z, renderData.entity.rotationYaw, event.partialTicks);
            this.isRendering = false;
            this.postRender(renderData);
            MinecraftForge.EVENT_BUS.post(new CustomRenderLivingEntity.Post(renderData.entity, renderData.renderer, x, y, z));
        }
        renderEntitiesQueue.clear();
    }
    private boolean preRender(RenderData data){
        if(data.drawIfNotVisible){
            if(cinqdt1Mod.mc.thePlayer.canEntityBeSeen(data.entity)){
                return false;
            }
        }
        if(data.drawOnTop) GlStateManager.disableDepth();
        if(!data.renderTexture) GlStateManager.disableTexture2D();
        GlStateManager.color((float)data.entityColor.getRed() / 255f, (float)data.entityColor.getGreen() / 255f, (float) data.entityColor.getBlue() / 255f, data.entityColor.getAlpha());
        return true;
    }

    private void postRender(RenderData data){
        if(data.drawOnTop) GlStateManager.enableDepth();
        if(!data.renderTexture) GlStateManager.enableTexture2D();
        GlStateManager.color(1.0f,1.0f,1.0f,1.0f);
    }


    public void addEntity(RenderData<? extends EntityLivingBase> renderData){
        if(renderData != null){
            renderEntitiesQueue.add(renderData);
        }
    }

    public boolean isModRenderEvent(){
        return this.isRendering;
    }

    public static class RenderData<T extends EntityLiving>{
        public final EntityLivingBase entity;
        public final Render renderer;
        public final boolean drawOnTop;
        public final Color entityColor;
        public final boolean drawIfNotVisible;
        public final boolean renderTexture;
        public RenderData(T entity, Render renderer, boolean drawOnTop, Color entityColor, boolean drawIfNotVisible, boolean renderTexture){
            this.entity = entity;
            this.renderer = renderer;
            this.drawOnTop = drawOnTop;
            this.entityColor = entityColor;
            this.drawIfNotVisible = drawIfNotVisible;
            this.renderTexture = renderTexture;
        }
        public RenderData(T entity, Render renderer, boolean drawOnTop, boolean drawIfNotVisible, boolean renderTexture){
            this(entity, renderer, drawOnTop, new Color((byte) 255, (byte) 255, (byte) 255), drawIfNotVisible, renderTexture);
        }
    }

}
