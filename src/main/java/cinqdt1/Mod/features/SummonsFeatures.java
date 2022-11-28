package cinqdt1.Mod.features;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import cinqdt1.Mod.utils.ItemUtils;
import cinqdt1.Mod.utils.RenderUtils;

import cinqdt1.Mod.cinqdt1Mod;
import cinqdt1.Mod.config.ModConfiguration;
import cinqdt1.Mod.events.RenderOverlay;
import cinqdt1.Mod.utils.Utils;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityArmorStand;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.monster.EntitySkeleton;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.StringUtils;
import net.minecraft.util.Vec3;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.client.event.RenderLivingEvent;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.player.AttackEntityEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.Phase;

public class SummonsFeatures {
	private  int lowestSummonHp = -1;
	private Entity lowestEntity = null;
	private boolean isSummonsSpawn = false;
	private final Pattern bossDespawnSummonPattern = Pattern.compile("^The Seraph recalled your \\d summoned allies!$");
	private final Pattern entityNamePattern = Pattern.compile("'s (.*) \\w*❤$");
	private final Pattern hpPattern = Pattern.compile("\\s(\\w*)❤$");
    @SubscribeEvent
    public void onTick(TickEvent.ClientTickEvent event) {
    	if (event.phase != Phase.START) return;
    	if (!Utils.inSkyblock || Utils.inDungeon) return;
    	Minecraft mc = Minecraft.getMinecraft();
    	if(mc.theWorld != null && isSummonsSpawn) {
	        List<Entity> listWorldEntity = mc.theWorld.getLoadedEntityList();
	        for(Entity entity : listWorldEntity) {
	        	if(entity instanceof EntityArmorStand) {
	        		String entityName = StringUtils.stripControlCodes(entity.getDisplayName().getUnformattedText());
		        	if(entityName.startsWith(Minecraft.getMinecraft().thePlayer.getName())) {
		        		Pattern hpSummonsPattern = Pattern.compile("\\s(\\w*)❤$");
		        		Matcher matchHp = hpSummonsPattern.matcher(entityName);
		        		if(matchHp.find()) {
		        			int hp = Utils.getUnformattedHp(matchHp.group(1));
		        			if(hp < lowestSummonHp || lowestSummonHp == -1) {
		        				lowestSummonHp = hp;
		        				lowestEntity = entity;
		        			}
		        		}
		        	}
	        	}
	        }
    	}
    }
    @SubscribeEvent
    public void onChat(ClientChatReceivedEvent event) {
    	String message = StringUtils.stripControlCodes(event.message.getUnformattedText());

        if (!Utils.inSkyblock || Utils.inDungeon) return;
        if (event.type == 2) return;
        if (message.contains(":")) return;
        
        if(isSummonsSpawn) {
        	if(message.equals("You have despawned your monster!") || message.equals("You have despawned your monsters!") || bossDespawnSummonPattern.matcher(message).find()) {
        		isSummonsSpawn = false;
	        	lowestSummonHp = -1;
	        	lowestEntity = null;
        	}
        }else {
        	Pattern spawnSummonsPattern = Pattern.compile("^You have spawned your .* soul!");
        	Matcher matchMessage = spawnSummonsPattern.matcher(message);
        	if(matchMessage.find()) {
        		isSummonsSpawn = true;
        	}
        }
    }
    
    @SubscribeEvent
    public void onWorldJoin(EntityJoinWorldEvent event) {
    	Entity entity = event.entity;
	    if (entity == Minecraft.getMinecraft().thePlayer) {
	    	isSummonsSpawn = false;
	    	lowestSummonHp = -1;
        	lowestEntity = null;
    	}
    }
    
    @SubscribeEvent
	public void renderPlayerInfo(RenderOverlay event) {
    	if(!ModConfiguration.showLowestSummon) return;
		if(!isSummonsSpawn) return;
		if(lowestEntity == null) return;
		String entityFullName = StringUtils.stripControlCodes(lowestEntity.getDisplayName().getUnformattedText());
		Matcher entityNameMatcher = entityNamePattern.matcher(entityFullName);
		Matcher matchHp = hpPattern.matcher(entityFullName);
		if(!entityNameMatcher.find() || !matchHp.find()) return;
		List<String> lowestHpSummonText = new ArrayList<>();
		lowestHpSummonText.add(EnumChatFormatting.GREEN + entityNameMatcher.group(1) + "\n" +
				EnumChatFormatting.RED + matchHp.group(1));
		try{
			RenderUtils.renderOverlay(cinqdt1Mod.newModConfig.getInteger("lowestHpSummon", "position", "x"), cinqdt1Mod.newModConfig.getInteger("lowestHpSummon", "position", "y"), cinqdt1Mod.newModConfig.getFloat("lowestHpSummon", "position", "scale"), ItemUtils.getLowestSummonTexture(), 4, 3, lowestHpSummonText, 0);
		} catch (Exception e) {
			e.printStackTrace();
		}
    }
}
