package com.cinqdt1.Mod.features;

import com.cinqdt1.Mod.cinqdt1Mod;
import com.cinqdt1.Mod.config.ModConfiguration;
import com.cinqdt1.Mod.events.InitEvent;
import com.cinqdt1.Mod.events.RenderOverlay;
import com.cinqdt1.Mod.gui.GuiFeature;
import com.cinqdt1.Mod.utils.ItemUtils;
import com.cinqdt1.Mod.utils.RenderUtils;
import com.cinqdt1.Mod.utils.Utils;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityArmorStand;
import net.minecraft.entity.monster.EntityBlaze;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.StringUtils;
import net.minecraftforge.client.event.RenderLivingEvent;
import net.minecraftforge.event.entity.player.AttackEntityEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FirePillar {
    private final Minecraft mc = Minecraft.getMinecraft();
    //Idk why there have emoji in boss string but this work
    // Halloween output : Inferno Demonl⚽ord II
    private final Pattern boosLevelPatternSpe = Pattern.compile("Inferno Demonl⚽ord ([IV]+)");
    private final Pattern bossLevelPattern = Pattern.compile("Inferno Demonl\uD83D\uDC79ord ([IV]+)");
    private final Pattern pillarPattern = Pattern.compile("7s \\d hits");
    private final Pattern pillarTimeLeftPattern = Pattern.compile("^(\\d)s \\d hits$");
    private boolean isBossFounded = false;
    private boolean isLastParticleFounded = false;
    private EntityArmorStand lastPillarParticle = null;
    private UUID bossID = null;
    private UUID pillarID = null;
    private int timeOutPillar = 0;
    private int hits = 0;
    @SubscribeEvent
    public void onInit(InitEvent event)
    {
        List<String> guiText = new ArrayList<>();
        guiText.add(EnumChatFormatting.GOLD + "" + EnumChatFormatting.BOLD + "Inferno Demonlord\n" +
                "Pillar: " + EnumChatFormatting.RED + "6s");
        GuiFeature guiInfo = new GuiFeature(cinqdt1Mod.guiEdit.getId(), 0, 0, 20, "firePillar", guiText, ItemUtils.getFirePillarTexture());
        cinqdt1Mod.guiEdit.registerFeature(guiInfo);
    }
    @SubscribeEvent
    public void onTick(TickEvent.ClientTickEvent event){
        if(event.phase != TickEvent.Phase.START) return;
        if(!Utils.inCrimsonIsle) return;
        if(!Utils.isInScoreboard("Inferno Demonlord") || !Utils.isInScoreboard("Slay the boss!")){
            isBossFounded = false;
            bossID = null;
            hits = 0;
            return;
        }
        if(!isBossFounded) return;
        if(timeOutPillar > 59){
            timeOutPillar = 0;
            lastPillarParticle = null;
            isLastParticleFounded = false;
            return;
        }else if(!isLastParticleFounded && lastPillarParticle != null){
            List<Entity> nearbyEntities = lastPillarParticle.getEntityWorld().getEntitiesInAABBexcluding(lastPillarParticle, lastPillarParticle.getEntityBoundingBox().expand(3, 3, 3), null);
            for(Entity nearbyEntity : nearbyEntities) {
                if(!(nearbyEntity instanceof EntityArmorStand)) continue;
                String unformattedName = StringUtils.stripControlCodes(nearbyEntity.getName());
                if(!pillarPattern.matcher(unformattedName).find()) continue;
                pillarID = nearbyEntity.getUniqueID();
                lastPillarParticle = null;
                return;
            }
            timeOutPillar += 1;
            return;

        }else if(pillarID != null){
            for(Entity entity : mc.theWorld.getLoadedEntityList()){
                if(pillarID == entity.getUniqueID()) return;
            }
            pillarID = null;
            return;
        }
        if(lastPillarParticle == null) checkForPillarParticles();
        if(isLastParticleFounded) checkForNextPositionPillarParticle();
    }

    @SubscribeEvent
    public void onAttack(AttackEntityEvent event){
        if(!Utils.inCrimsonIsle) return;
        if(isBossFounded && event.target.getUniqueID() == bossID) return;
        if(event.entity != Minecraft.getMinecraft().thePlayer) return;
        if(!Utils.isInScoreboard("Inferno Demonlord")) return;
        if(!(event.target instanceof EntityBlaze)) return;
        String bossScoreboard = StringUtils.stripControlCodes(Utils.getMatchInScoreboard("Inferno Demonlord"));
        if(bossScoreboard == null) return;
        Matcher bossLevelMatch = bossLevelPattern.matcher(bossScoreboard);
        if(!bossLevelMatch.find()) return;
        int bossLevel = Utils.convertRomanToDecimal(bossLevelMatch.group(1));
        if(bossLevel < 2) return;

        Entity entity = event.target;
        List<Entity> nearbyEntities = entity.getEntityWorld().getEntitiesInAABBexcluding(entity, entity.getEntityBoundingBox().expand(0.2, 3, 0.2), null);
        for(Entity nearbyEntity : nearbyEntities){
            if(!(nearbyEntity instanceof EntityArmorStand)) continue;
            String unformattedName = StringUtils.stripControlCodes(nearbyEntity.getName());
            if(unformattedName.contains("Inferno Demonlord")) {
                if(bossID != entity.getUniqueID()){
                    bossID = entity.getUniqueID();
                    hits = 0;
                }
                hits += 1;
                if(hits > 4) isBossFounded = true;
                return;
            }
        }
    }

    @SubscribeEvent
    public void onRenderEntity(RenderLivingEvent.Pre<EntityBlaze> event){
        if(!Utils.inCrimsonIsle) return;
        if(!isBossFounded) return;
        if(event.entity.getUniqueID() != bossID) return;
        event.renderer.setRenderOutlines(true);
    }

    @SubscribeEvent
    public void renderPlayerInfo(RenderOverlay event){
        if(!Utils.inSkyblock) return;
        if(!ModConfiguration.firePillarOverlayState) return;
        if(!isBossFounded) return;
        List<String> firePillarText = new ArrayList<>();
        firePillarText.add(EnumChatFormatting.GOLD + "" + EnumChatFormatting.BOLD + "Inferno Demonlord\n" +
                "Pillar: " + EnumChatFormatting.RED + getPillarTimeLeft());
        try {
            RenderUtils.renderOverlay(cinqdt1Mod.newModConfig.getInteger("firePillar", "position", "x"), cinqdt1Mod.newModConfig.getInteger("firePillar", "position", "y"), cinqdt1Mod.newModConfig.getFloat("firePillar", "position", "scale"), ItemUtils.getFirePillarTexture(), 4, 3, firePillarText, 0);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void checkForPillarParticles() {
        for (Entity entity : mc.theWorld.getLoadedEntityList()) {
            if (entity.getUniqueID() != bossID) continue;
            EntityBlaze boss = (EntityBlaze) entity;
            List<Entity> nearbyEntities = boss.getEntityWorld().getEntitiesInAABBexcluding(boss, boss.getEntityBoundingBox().expand(0.2, 20, 0.2), null);
            for (Entity nearbyEntity : nearbyEntities) {
                if (!(nearbyEntity instanceof EntityArmorStand)) continue;
                EntityArmorStand armorStand = (EntityArmorStand) nearbyEntity;
                if (armorStand.getHeldItem() == null) continue;
                ItemStack hand = armorStand.getHeldItem();
                String headTexture = Utils.getHeadTexture(hand);
                if (headTexture == null) continue;
                if (headTexture.equals("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvOWMyZTlkODM5NWNhY2Q5OTIyODY5YzE1MzczY2Y3Y2IxNmRhMGE1Y2U1ZjNjNjMyYjE5Y2ViMzkyOWM5YTExIn19fQ==")) {
                    lastPillarParticle = armorStand;
                    isLastParticleFounded = true;
                    return;
                }
            }
        }
    }
    private void checkForNextPositionPillarParticle(){
        for(Entity entity : mc.theWorld.getLoadedEntityList()){
            if(entity.getUniqueID() == lastPillarParticle.getUniqueID()){
                lastPillarParticle = (EntityArmorStand) entity;
                isLastParticleFounded = true;
                return;
            }
        }
        isLastParticleFounded = false;
    }

    private String getPillarTimeLeft(){
        if(pillarID == null) return "None";
        for(Entity entity : mc.theWorld.getLoadedEntityList()){
            if(entity.getUniqueID() == pillarID){
                String unformattedName = StringUtils.stripControlCodes(entity.getName());
                Matcher pillarTimerMatcher = pillarTimeLeftPattern.matcher(unformattedName);
                if(pillarTimerMatcher.find()) return pillarTimerMatcher.group(1) + 's';
            }
        }
        return "None";
    }
}
