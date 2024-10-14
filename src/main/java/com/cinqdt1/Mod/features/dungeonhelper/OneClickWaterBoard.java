package com.cinqdt1.Mod.features.dungeonhelper;

import com.cinqdt1.Mod.utils.Utils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockPos;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

public class OneClickWaterBoard {
    private final boolean nope = false;
    private final Minecraft mc = Minecraft.getMinecraft();
    private long tickCounter = 0;
    private boolean wasInWaterRoom = false;
    private boolean isSolutionFound = false;
    private Vec3 pistonLookVec = null;

    @SubscribeEvent
    public void onTick(TickEvent.ClientTickEvent event) {
        if (event.phase != TickEvent.Phase.START) return;
        tickCounter++;
        if(tickCounter % 20 != 0) return;
        if(!Utils.inDungeon) return;
        EntityPlayerSP player = mc.thePlayer;
        World world = mc.theWorld;
        if(player == null || world == null) return;
        new Thread(() -> {
            boolean isInWaterRoom = isInWaterRoom(player, world);

            if(wasInWaterRoom || isSolutionFound) return;
            wasInWaterRoom = isInWaterRoom;
        }).start();
    }

    /*
     * Thanks to Danker Skyblock Mod for the detection from WaterSolver.java
     * https://github.com/bowser0000/SkyblockMod
     */
    private boolean isInWaterRoom(EntityPlayerSP player, World world) {
        boolean foundFirstEvidence = false;
        for (int x = (int) (player.posX - 13); x <= player.posX + 13; x++) {
            for (int z = (int) (player.posZ - 13); z <= player.posZ + 13; z++) {
                BlockPos blockPos = new BlockPos(x, 54, z);
                if (world.getBlockState(blockPos).getBlock() == Blocks.sticky_piston) {
                    foundFirstEvidence = true;
                    break;
                }
            }
        }
        if (!foundFirstEvidence) return false;
        for (int x = (int) (player.posX - 25); x <= player.posX + 25; x++) {
            for (int z = (int) (player.posZ - 25); z <= player.posZ + 25; z++) {
                BlockPos blockPos = new BlockPos(x, 82, z);
                if (world.getBlockState(blockPos).getBlock() == Blocks.piston_head) {
                    BlockPos pos1 = new BlockPos(x + 1, 82, z + 1);
                    BlockPos pos2 = new BlockPos(x - 1, 82, z - 1);
                    Iterable<BlockPos> blocks = BlockPos.getAllInBox(pos1, pos2);
                    for (BlockPos block : blocks) {
                        if (world.getBlockState(block).getBlock() == Blocks.sticky_piston){
                            pistonLookVec = new Vec3(x - block.getX(), 0, z - block.getZ());
                            return true;
                        }
                    }
                    //Incoherence in the room
                }
            }
        }
        return false;
    }

    private class LeversPos {
        public final BlockPos GOLD;
        public final BlockPos CLAY;
        public final BlockPos EMERALD;
        public final BlockPos QUARTZ;
        public final BlockPos DIAMOND;
        public final BlockPos COAL;

        public LeversPos(BlockPos gold, BlockPos clay, BlockPos emerald, BlockPos quartz, BlockPos diamond, BlockPos coal) {
            GOLD = gold;
            CLAY = clay;
            EMERALD = emerald;
            QUARTZ = quartz;
            DIAMOND = diamond;
            COAL = coal;
        }

        public BlockPos getGOLD() {
            return GOLD;
        }

        public BlockPos getCLAY() {
            return CLAY;
        }

        public BlockPos getEMERALD() {
            return EMERALD;
        }

        public BlockPos getQUARTZ() {
            return QUARTZ;
        }

        public BlockPos getDIAMOND() {
            return DIAMOND;
        }

        public BlockPos getCOAL() {
            return COAL;
        }
    }
}
