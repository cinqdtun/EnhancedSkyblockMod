package com.cinqdt1.Mod.features;

import com.cinqdt1.Mod.config.ModConfiguration;
import com.cinqdt1.Mod.utils.Utils;
import net.minecraft.client.Minecraft;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ContainerChest;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.List;
import java.util.Objects;
import java.util.regex.Pattern;

public class FixSkillOverflow {
    public final double xpToSubSkill50 = 4000000;
    public final double xpToSubSkill60 = 7000000;
    private final Pattern[] skillPattern50 = {Pattern.compile("Foraging L"), Pattern.compile("Fishing L"), Pattern.compile("Alchemy L"), Pattern.compile("Taming L"), Pattern.compile("Carpentry L")};
    private final Pattern[] skillPattern60 = {Pattern.compile("Farming LX"), Pattern.compile("Mining LX"), Pattern.compile("Combat LX"), Pattern.compile("Enchanting LX")};
    private final Pattern maxSkillPattern = Pattern.compile("Max Skill level reached");
    private final Pattern xpPattern = Pattern.compile(" ([\\d,.]+) XP");

    @SubscribeEvent
    public void onToolTip(ItemTooltipEvent event){
        if(!ModConfiguration.trueOverflow) return;
        Container container = Minecraft.getMinecraft().thePlayer.openContainer;
        if(container instanceof ContainerChest){
            ContainerChest chest = (ContainerChest)container;
            IInventory inv = chest.getLowerChestInventory();
            if(Objects.equals(inv.getName(), "Your Skills")){
                ItemStack stack = event.itemStack;
                if (stack.getItem() == Item.getItemFromBlock(Blocks.glass_pane)) return;
                if(Utils.containsPattern(event.toolTip, maxSkillPattern)){
                    Skill skillType = getSkillType(event.toolTip);
                    if(skillType == Skill.NONE) return;
                    int lineIndex = Utils.getIndexFirstMatchLineArray(event.toolTip, xpPattern);
                    double xp;
                    switch (skillType){
                        case FIFTY:
                            xp = getFixXpFromTooltip(event.toolTip, - xpToSubSkill50);
                            break;
                        case SIXTY:
                            xp = getFixXpFromTooltip(event.toolTip, - xpToSubSkill60);
                            break;
                        default:
                            return;
                    }
                    if(xp < 0) return;
                    event.toolTip.set(lineIndex, getDisplayOverflowXp(xp));
                }
            }
        }
    }

    private Skill getSkillType(List<String> toolTip){
        for(Pattern pattern : skillPattern50){
            if(Utils.containsPattern(toolTip, pattern)){
                return Skill.FIFTY;
            }
        }
        for(Pattern pattern : skillPattern60){
            if(Utils.containsPattern(toolTip, pattern)){
                return Skill.SIXTY;
            }
        }
        return Skill.NONE;
    }

    private double getFixXpFromTooltip(List<String> toolTip, double addValue){
        String rawXp = Utils.getFirstGroupMatchArray(toolTip, xpPattern);
        if(rawXp == null) return -1;
        return Utils.convertFormattedNumber(rawXp) + addValue;
    }

    private String getDisplayOverflowXp(double amount){
        String convertedAmountXp = Utils.formatStringNumber(Utils.convertDoubleToString(amount));
        return "§5§o§e§l§m §l§m §l§m §l§m §l§m §l§m §l§m §l§m §l§m §l§m §l§m §l§m §l§m §l§m §l§m §l§m §l§m §l§m §l§m §l§m §r §6" + convertedAmountXp + " XP";
    }

    private enum Skill {
        NONE, FIFTY, SIXTY
    }
}
