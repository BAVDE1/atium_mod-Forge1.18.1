package com.BAVDE.atium_mod.block.custom;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.material.FluidState;

import java.util.Random;

public class AtiumOreOvergrown extends Block {
    public static final BooleanProperty RECHARGING = AtiumOreOvergrownRecharging.RECHARGING;

    public AtiumOreOvergrown(BlockBehaviour.Properties p_49795_) {
        super(p_49795_);
        this.registerDefaultState(this.defaultBlockState().setValue(RECHARGING, false));
    }

    @Override
    public boolean onDestroyedByPlayer(BlockState state, Level world, BlockPos pos, Player player, boolean willHarvest, FluidState fluid) {
        if (!state.getValue(RECHARGING)) {
        world.setBlock(pos, state.setValue(RECHARGING, Boolean.valueOf(true)), 3);
        }
        return super.onDestroyedByPlayer(state, world, pos, player, willHarvest, fluid);
    }

    @Override
    public boolean isRandomlyTicking(BlockState pState) {
        return pState.getValue(RECHARGING);
    }

    @Override
    public void randomTick(BlockState pState, ServerLevel pLevel, BlockPos pPos, Random pRandom) {
        if (pState.getValue(RECHARGING)) {
            pLevel.setBlock(pPos, pState.setValue(RECHARGING, Boolean.valueOf(false)), 3);
        }
    }






    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder) {
        pBuilder.add(RECHARGING);
    }
}
