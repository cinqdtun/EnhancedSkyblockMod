package com.cinqdt1.Mod.features.dojohelper;

import com.cinqdt1.Mod.config.ModConfiguration;
import com.cinqdt1.Mod.utils.Utils;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityArmorStand;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.StringUtils;
import net.minecraftforge.client.event.RenderLivingEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

import java.util.ArrayList;

public class DisciplineHelper {
    private final Minecraft mc = Minecraft.getMinecraft();
    private final ArrayList<DisciplineMob> disciplineMobs = new ArrayList<>();

    @SubscribeEvent
    public void onTick(TickEvent.ClientTickEvent event) {
        if (event.phase != TickEvent.Phase.START) return;
        if (!Utils.inSkyblock) return;
        if (!Utils.inCrimsonIsle) return;
        if (!Utils.isInScoreboard("Challenge: Discipline")) return;
        ArrayList<DisciplineMob> tempDisciplineMobs = new ArrayList<>();
        for (Entity entity : mc.theWorld.getLoadedEntityList()){
            if (entity instanceof EntityArmorStand){
                String armorStandNameTag = StringUtils.stripControlCodes(entity.getCustomNameTag());
                if (armorStandNameTag.matches("^(Wood|Iron|Gold|Diamond)$")){
                    tempDisciplineMobs.add(new DisciplineMob(entity));
                }
            }
        }
        for (DisciplineMob tempDisciplineMob : tempDisciplineMobs) {
            boolean isDetected = false;
            for (DisciplineMob disciplineMob : disciplineMobs) {
                if (disciplineMob.isSameEntity(tempDisciplineMob.entity)) {
                    isDetected = true;
                    break;
                }
            }
            if(!isDetected) disciplineMobs.add(tempDisciplineMob);
        }
        for (DisciplineMob disciplineMob : disciplineMobs) {
            if (disciplineMob.isDead()) {
                disciplineMobs.remove(disciplineMob);
                break;
            }
        }
    }

    @SubscribeEvent
    public void onBeforeRenderEntity(RenderLivingEvent.Pre<EntityLivingBase> event) {
        if(!ModConfiguration.dojoDisciplineMobTimerState) return;
        Entity entity = event.entity;
        for(DisciplineMob disciplineMob : disciplineMobs) {
            if (disciplineMob.isSameEntity(entity)) {
                double mobTime = disciplineMob.getTimeElapsed() / 1000.0;
                double roundedMobTime = Utils.roundToHalfDown(2, mobTime);
                String mobNameTag = (mobTime < 5 ?
                        EnumChatFormatting.GREEN : EnumChatFormatting.RED)
                        + String.format("%.2f", roundedMobTime)
                        + "s";
                entity.setCustomNameTag(mobNameTag);
                break;
            }
        }
    }

    private class DisciplineMob{
        private final Entity entity;
        private final long spawned_timestamp;

        private DisciplineMob(Entity entity) {
            this.entity = entity;
            this.spawned_timestamp = System.currentTimeMillis();
        }

        public long getTimeElapsed(){
            return System.currentTimeMillis() - spawned_timestamp;
        }

        public boolean isSameEntity(Entity entity){
            return this.entity.getEntityId() == entity.getEntityId();
        }

        public boolean isDead(){
            return entity.isDead;
        }
    }
}
