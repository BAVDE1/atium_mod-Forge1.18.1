package com.BAVDE.atium_mod.block.custom;

import com.BAVDE.atium_mod.block.ModBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;

import javax.annotation.Nullable;
import java.util.Random;

public class EmptyAtiumOreOvergrown extends Block {
    public static final BooleanProperty TICKING = BooleanProperty.create("ticking");

    public EmptyAtiumOreOvergrown(Properties p_49795_) {
        super(p_49795_);
        this.registerDefaultState(this.defaultBlockState().setValue(TICKING, Boolean.valueOf(true)));
    }

    @Override
    public boolean isRandomlyTicking(BlockState pState) {
        return pState.getValue(TICKING);
    }

    @Override
    public void randomTick(BlockState pState, ServerLevel pLevel, BlockPos pPos, Random pRandom) {
        if (pState.getValue(TICKING)) {
            pLevel.setBlock(pPos, ModBlocks.ATIUM_ORE_OVERGROWN.get().defaultBlockState(), 3);
        }
    }

    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder) {
        pBuilder.add(TICKING);
    }
}
