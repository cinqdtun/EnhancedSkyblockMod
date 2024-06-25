package com.cinqdt1.Mod.features;

import com.cinqdt1.Mod.cinqdt1Mod;
import com.cinqdt1.Mod.config.ModConfiguration;
import com.cinqdt1.Mod.events.RenderOverlay;
import com.cinqdt1.Mod.utils.RenderUtils;
import com.cinqdt1.Mod.utils.Utils;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.util.StringUtils;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.ArrayList;
import java.util.List;

public class MythologicalHp {

    public MythologicalMob digMob = MythologicalMob.EMPTY;

    @SubscribeEvent
    public void onRenderOverlay(RenderOverlay event){
        if(!Utils.inSkyblock) return;
        if(!ModConfiguration.hpMythologicalState) return;
        if(Minecraft.getMinecraft().theWorld.loadedEntityList == null) return;
        for(Entity entity : Minecraft.getMinecraft().theWorld.loadedEntityList){
            if(entity.getCustomNameTag() == null || entity.getCustomNameTag().isEmpty()) continue;
            String unformattedName = StringUtils.stripControlCodes(entity.getCustomNameTag());
            List<String> mythologicalHpText = new ArrayList<>();
            if(unformattedName.contains("Minos Hunter") && digMob == MythologicalMob.HUNTER){
                mythologicalHpText.add(entity.getCustomNameTag());
                try{
                    RenderUtils.renderOverlay(cinqdt1Mod.newModConfig.getInteger("mythologicalHp", "position", "x"), cinqdt1Mod.newModConfig.getInteger("mythologicalHp", "position", "y"), cinqdt1Mod.newModConfig.getFloat("mythologicalHp", "position", "scale"), null, 0,0, mythologicalHpText, 0);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return;
            }else if(unformattedName.contains("Bagheera") && digMob == MythologicalMob.LYNXES){
                mythologicalHpText.add(entity.getCustomNameTag());
                try{
                    RenderUtils.renderOverlay(cinqdt1Mod.newModConfig.getInteger("mythologicalHp", "position", "x"), cinqdt1Mod.newModConfig.getInteger("mythologicalHp", "position", "y"), cinqdt1Mod.newModConfig.getFloat("mythologicalHp", "position", "scale"), null, 0,0, mythologicalHpText, 0);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return;
            }else if(unformattedName.contains("Minotaur") && digMob == MythologicalMob.MINOTAUR){
                mythologicalHpText.add(entity.getCustomNameTag());
                try{
                    RenderUtils.renderOverlay(cinqdt1Mod.newModConfig.getInteger("mythologicalHp", "position", "x"), cinqdt1Mod.newModConfig.getInteger("mythologicalHp", "position", "y"), cinqdt1Mod.newModConfig.getFloat("mythologicalHp", "position", "scale"), null, 0,0, mythologicalHpText, 0);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return;
            }else if(unformattedName.contains("Gaia Construct") && digMob == MythologicalMob.GAIA){
                mythologicalHpText.add(entity.getCustomNameTag());
                try{
                    RenderUtils.renderOverlay(cinqdt1Mod.newModConfig.getInteger("mythologicalHp", "position", "x"), cinqdt1Mod.newModConfig.getInteger("mythologicalHp", "position", "y"), cinqdt1Mod.newModConfig.getFloat("mythologicalHp", "position", "scale"), null, 0,0, mythologicalHpText, 0);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return;
            }else if(unformattedName.contains("Minos Champion") && digMob == MythologicalMob.CHAMPION){
                mythologicalHpText.add(entity.getCustomNameTag());
                try{
                    RenderUtils.renderOverlay(cinqdt1Mod.newModConfig.getInteger("mythologicalHp", "position", "x"), cinqdt1Mod.newModConfig.getInteger("mythologicalHp", "position", "y"), cinqdt1Mod.newModConfig.getFloat("mythologicalHp", "position", "scale"), null, 0,0, mythologicalHpText, 0);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return;
            }else if(unformattedName.contains("Minos Inquisitor") && digMob == MythologicalMob.INQUISITOR){
                mythologicalHpText.add(entity.getCustomNameTag());
                try{
                    RenderUtils.renderOverlay(cinqdt1Mod.newModConfig.getInteger("mythologicalHp", "position", "x"), cinqdt1Mod.newModConfig.getInteger("mythologicalHp", "position", "y"), cinqdt1Mod.newModConfig.getFloat("mythologicalHp", "position", "scale"), null, 0,0, mythologicalHpText, 0);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return;
            }
        }
    }

    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public void onChat(ClientChatReceivedEvent event) {
        String message = StringUtils.stripControlCodes(event.message.getUnformattedText());
        if (!Utils.inSkyblock) return;
        if (event.type == 2) return;
        if (message.contains(":")) return;

        if (message.contains("a Minos Hunter!")) {
            digMob = MythologicalMob.HUNTER;
        } else if (message.contains("Siamese Lynxes!")) {
            digMob = MythologicalMob.LYNXES;
        } else if (message.contains("a Minotaur!")) {
            digMob = MythologicalMob.MINOTAUR;
        } else if (message.contains("a Gaia Construct!")) {
            digMob = MythologicalMob.GAIA;
        } else if (message.contains("a Minos Champion!")) {
            Minecraft mc = Minecraft.getMinecraft();
            List<Entity> listWorldEntity = mc.theWorld.getLoadedEntityList();
            for (Entity entity : listWorldEntity) {
                if (entity.getName().contains("Minos Champion")) {
                    digMob = MythologicalMob.CHAMPION;
                } else if (entity.getName().contains("Minos Inquisitor")) {
                    digMob = MythologicalMob.INQUISITOR;
                }
            }
        } else if (message.contains("a Minos Inquisitor!")) {
            digMob = MythologicalMob.INQUISITOR;
        }
    }
    enum MythologicalMob{
        HUNTER,
        LYNXES,
        MINOTAUR,
        GAIA,
        CHAMPION,
        INQUISITOR,
        EMPTY
    }
}
