package com.BAVDE.atium_mod.block.custom;

import com.BAVDE.atium_mod.block.ModBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;

import java.util.Random;

public class EmptyAtiumOre extends Block {
    public static final IntegerProperty GROWTH = IntegerProperty.create("growth", 0, 40);

    public EmptyAtiumOre(Properties p_49795_) {
        super(p_49795_);
        this.registerDefaultState(this.defaultBlockState().setValue(GROWTH, 0));
    }

    @Override
    public boolean isRandomlyTicking(BlockState pState) {
        return true; //makes the block tick
    }

    @Override
    public void randomTick(BlockState pState, ServerLevel pLevel, BlockPos pPos, Random pRandom) {
        int currentGrowth = pState.getValue(GROWTH); //sets a variable that is that same as growth state
        //takes about 50 - 60 mins to regenerate ore
        if (currentGrowth == 40) {
            pLevel.setBlock(pPos, ModBlocks.ATIUM_ORE.get().defaultBlockState(), 3); //grows the atium geode
        } else if (currentGrowth < 40){
            pLevel.setBlock(pPos, pState.setValue(GROWTH, (currentGrowth + 1)), 3); //adds 1 on to the growth
        }
    }

    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder) {
        pBuilder.add(GROWTH);
    }
}
