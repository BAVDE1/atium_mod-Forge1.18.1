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

import java.util.Random;

public class CrystallineLeaves extends LeavesBlock {
    public static final BooleanProperty GROWN = BooleanProperty.create("grown");

    public CrystallineLeaves(Properties p_54422_) {
        super(p_54422_);
        this.registerDefaultState(this.defaultBlockState().setValue(GROWN, Boolean.valueOf(false)));
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
        if (!pState.getValue(PERSISTENT)) {
            if (!pState.getValue(GROWN)) {
                var chance = Math.round(Math.random() * 101);
                if (chance == 5 || chance == 4 || chance == 3 || chance == 2 || chance == 1) {
                    return true;
                } else {
                    return false;
                }
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    @Override
    public void randomTick(BlockState pState, ServerLevel pLevel, BlockPos pPos, Random pRandom) {
        if (!pState.getValue(GROWN)) { //if the block is ticking (5% chance)
            pState.setValue(GROWN, true);
        }
    }

    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder) {
        pBuilder.add(GROWN, DISTANCE, PERSISTENT);
    }
}
