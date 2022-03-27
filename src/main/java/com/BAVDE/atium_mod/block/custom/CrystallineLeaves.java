package com.BAVDE.atium_mod.block.custom;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.LeavesBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;

import java.util.Random;

public class CrystallineLeaves extends LeavesBlock {
    public static final BooleanProperty GROWING = BooleanProperty.create("growing");
    public static final BooleanProperty GROWN = BooleanProperty.create("grown");

    public CrystallineLeaves(Properties p_54422_) {
        super(p_54422_);
        var willGrow = Math.random() < 0.05;
        this.registerDefaultState(this.defaultBlockState().setValue(GROWN, false));
        this.registerDefaultState(this.defaultBlockState().setValue(GROWING, willGrow));
    }

    @Override
    public boolean isFlammable(BlockState state, BlockGetter world, BlockPos pos, Direction face) {
        return true;
    }
    @Override
    public int getFlammability(BlockState state, BlockGetter world, BlockPos pos, Direction face) {
        return 60;
    }
    @Override
    public int getFireSpreadSpeed(BlockState state, BlockGetter world, BlockPos pos, Direction face) {
        return 30;
    }

    @Override
    public boolean isRandomlyTicking(BlockState pState) {
        //if close to wood and not player placed
        return pState.getValue(DISTANCE) == 7 && !pState.getValue(PERSISTENT)
                //if growing and not grown
                || pState.getValue(GROWING) && !pState.getValue(GROWN);
    }

    @Override
    public void randomTick(BlockState pState, ServerLevel pLevel, BlockPos pPos, Random pRandom) {
        //if growing
        if (pState.getValue(GROWING)) {
            pState.setValue(GROWN, true); //grow
            pState.setValue(GROWING, false); //no longer growing
        }
        //if not close to wood and not player placed
        if (pState.getValue(DISTANCE) == 7 && !pState.getValue(PERSISTENT)) {
            dropResources(pState, pLevel, pPos); //drop items
            pLevel.removeBlock(pPos, false); //remove block
        }
    }

    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder) {
        pBuilder.add(GROWING, GROWN, DISTANCE, PERSISTENT);
    }
}
