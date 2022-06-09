package com.BAVDE.atium_mod.block.custom;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.IntegerProperty;

import java.util.Random;

public class FracturedMagmaBlock extends Block {
    public static final int MAX_AGE = 3;
    public static final IntegerProperty AGE = BlockStateProperties.AGE_3;
    private static final int NEIGHBORS_TO_AGE = 4;
    private static final int NEIGHBORS_TO_MELT = 2;

    public FracturedMagmaBlock(Properties p_49795_) {
        super(p_49795_);
        this.registerDefaultState(this.stateDefinition.any().setValue(AGE, 0));
    }

    @Override
    public boolean isRandomlyTicking(BlockState pState) {
        return super.isRandomlyTicking(pState);
    }

    @Override
    public void stepOn(Level pLevel, BlockPos pPos, BlockState pState, Entity pEntity) {
        if (!pEntity.fireImmune() && pEntity instanceof LivingEntity && !EnchantmentHelper.hasFrostWalker((LivingEntity)pEntity)) {
            pEntity.hurt(DamageSource.HOT_FLOOR, 1.5F);
        }
        super.stepOn(pLevel, pPos, pState, pEntity);
    }

    @Override
    public void tick(BlockState pState, ServerLevel pLevel, BlockPos pPos, Random pRandom) {
        if ((pRandom.nextInt(3) == 0 || this.fewerNeighboursThan(pLevel, pPos, 4)) && pLevel.getMaxLocalRawBrightness(pPos) > 11 - pState.getValue(AGE) - pState.getLightBlock(pLevel, pPos) && this.slightlyMelt(pState, pLevel, pPos)) {
            BlockPos.MutableBlockPos blockpos$mutableblockpos = new BlockPos.MutableBlockPos();

            for(Direction direction : Direction.values()) {
                blockpos$mutableblockpos.setWithOffset(pPos, direction);
                BlockState blockstate = pLevel.getBlockState(blockpos$mutableblockpos);
                if (blockstate.is(this) && !this.slightlyMelt(blockstate, pLevel, blockpos$mutableblockpos)) {
                    pLevel.scheduleTick(blockpos$mutableblockpos, this, Mth.nextInt(pRandom, 20, 40));
                }
            }
        } else {
            pLevel.scheduleTick(pPos, this, Mth.nextInt(pRandom, 20, 40));
        }
    }

    private boolean slightlyMelt(BlockState pState, Level pLevel, BlockPos pPos) {
        int i = pState.getValue(AGE);
        if (i < 3) {
            pLevel.setBlock(pPos, pState.setValue(AGE, i + 1), 2);
            return false;
        } else {
            this.melt(pState, pLevel, pPos);
            return true;
        }
    }

    @Override
    public void onNeighborChange(BlockState state, LevelReader level, BlockPos pos, BlockPos neighbor) {
        if (state.getBlock().defaultBlockState().is(this) && this.fewerNeighboursThan(level, pos, 2)) {
            this.melt(state, (Level)level, pos);
        }
        super.onNeighborChange(state, level, pos, neighbor);
    }

    private boolean fewerNeighboursThan(BlockGetter pLevel, BlockPos pPos, int pNeighborsRequired) {
        int i = 0;
        BlockPos.MutableBlockPos blockpos$mutableblockpos = new BlockPos.MutableBlockPos();

        for(Direction direction : Direction.values()) {
            blockpos$mutableblockpos.setWithOffset(pPos, direction);
            if (pLevel.getBlockState(blockpos$mutableblockpos).is(this)) {
                ++i;
                if (i >= pNeighborsRequired) {
                    return false;
                }
            }
        }
        return true;
    }

    protected void melt(BlockState pState, Level pLevel, BlockPos pPos) {
        if (pLevel.dimensionType().ultraWarm()) {
            pLevel.removeBlock(pPos, false);
        } else {
            pLevel.setBlockAndUpdate(pPos, Blocks.LAVA.defaultBlockState());
            pLevel.neighborChanged(pPos, Blocks.LAVA, pPos);
        }
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder) {
        pBuilder.add(AGE);
    }
}
