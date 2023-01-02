package cinqdt1.Mod.utils;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class ItemUtils {
    private static final ItemStack blazeHatItem = Utils.createTexturedAndSetId("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYjc4ZWYyZTRjZjJjNDFhMmQxNGJmZGU5Y2FmZjEwMjE5ZjViMWJmNWIzNWE0OWViNTFjNjQ2Nzg4MmNiNWYwIn19fQ==", "BLAZE", "00000000-0000-0000-0000-000000000001");
    private static final ItemStack fireFreezeItem = Utils.setIdItem(new ItemStack(Item.getItemFromBlock(Blocks.yellow_flower)), "FIRE_FREEZE_STAFF");
    private static final ItemStack questionMark = Utils.createTexturedAndSetId("e3RleHR1cmVzOntTS0lOOnt1cmw6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZjMwZmQ1ZTc4N2UyMzU5NGNlODFhYTczNDJmM2FlM2E5MGU4MTA1MWE2ZDk2ZGIxMmYzMWVlNGEzMjg0MjM2MiJ9fX0=", "QUESTION_MARK", "00000000-0000-0000-0000-000000000002");
    private static final ItemStack summoningRingItem = Utils.createTexturedAndSetId("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMTc3YzljNjM4YmYzZGNkYTM0OGVkZWE0NGU5YTNkYjRhYmMxZTIzOTU1ODY2MTYxMWY4MGMxMTA0NzJhZCJ9fX0", "SUMMONING_RING", "00000000-0000-0000-0000-000000000003");
    private static final ItemStack scavengedGoldenHammerItem = Utils.setIdItem(new ItemStack(Items.golden_axe), "DWARVEN_GOLD_HAMMER");
    private static final ItemStack weight = Utils.createTexturedAndSetId("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMmYyY2ZhYWQ2ZjhiNjJjOTkyYzNiNTVjZTMxNjlmZmE0MDA1YWFlZTJkYjM1YTYzZjRiMzc5MGU0YTU5NmMzNSJ9fX0K", "NULL", "00000000-0000-0000-0000-000000000004");

    public static ItemStack getFireFreezeTexture(){
        return fireFreezeItem;
    }

    public static ItemStack getFirePillarTexture(){
        return blazeHatItem;
    }

    public static ItemStack getLowestSummonTexture(){
        return summoningRingItem;
    }
    public static ItemStack getQuestionMark(){
        return questionMark;
    }
    public static ItemStack getScavengedStatsTexture(){
        return scavengedGoldenHammerItem;
    }
    public static ItemStack getWeight(){
        return weight;
    }
}
