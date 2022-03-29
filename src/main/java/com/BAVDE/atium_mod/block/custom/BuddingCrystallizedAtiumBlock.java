package com.BAVDE.atium_mod.block.custom;

import com.BAVDE.atium_mod.block.ModBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.block.AmethystClusterBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.level.material.PushReaction;
import net.minecraftforge.registries.RegistryObject;

import java.util.Random;

public class BuddingCrystallizedAtiumBlock extends CrystallizedAtiumBlock {
    public static final int GROWTH_CHANCE = 5;
    private static final Direction[] DIRECTIONS = Direction.values();

    public BuddingCrystallizedAtiumBlock(Properties p_49795_) {
        super(p_49795_);
    }

    public PushReaction getPistonPushReaction(BlockState pState) {
        return PushReaction.DESTROY;
    }

    public void randomTick(BlockState pState, ServerLevel pLevel, BlockPos pPos, Random pRandom) {
        if (pRandom.nextInt(5) == 0) {
            Direction direction = DIRECTIONS[pRandom.nextInt(DIRECTIONS.length)];
            BlockPos blockpos = pPos.relative(direction);
            BlockState blockstate = pLevel.getBlockState(blockpos);
            RegistryObject<Block> block = null;
            if (canClusterGrowAtState(blockstate)) {
                block = ModBlocks.SMALL_CRYSTALLIZED_ATIUM_BUD;
            } else if (blockstate.is(ModBlocks.SMALL_CRYSTALLIZED_ATIUM_BUD.get()) && blockstate.getValue(CrystallizedAtiumCluster.FACING) == direction) {
                block = ModBlocks.MEDIUM_CRYSTALLIZED_ATIUM_BUD;
            } else if (blockstate.is(ModBlocks.MEDIUM_CRYSTALLIZED_ATIUM_BUD.get()) && blockstate.getValue(CrystallizedAtiumCluster.FACING) == direction) {
                block = ModBlocks.LARGE_CRYSTALLIZED_ATIUM_BUD;
            } else if (blockstate.is(ModBlocks.LARGE_CRYSTALLIZED_ATIUM_BUD.get()) && blockstate.getValue(CrystallizedAtiumCluster.FACING) == direction) {
                block = ModBlocks.CRYSTALLIZED_ATIUM_CLUSTER;
            }

            if (block != null) {
                BlockState blockstate1 = block.get().defaultBlockState().setValue(CrystallizedAtiumCluster.FACING, direction).setValue(CrystallizedAtiumCluster.WATERLOGGED, Boolean.valueOf(blockstate.getFluidState().getType() == Fluids.WATER));
                pLevel.setBlockAndUpdate(blockpos, blockstate1);
            }

        }
    }

    public static boolean canClusterGrowAtState(BlockState pState) {
        return pState.isAir() || pState.is(Blocks.WATER) && pState.getFluidState().getAmount() == 8;
    }
}
