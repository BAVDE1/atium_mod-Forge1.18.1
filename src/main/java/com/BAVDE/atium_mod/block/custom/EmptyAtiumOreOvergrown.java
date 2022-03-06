package com.BAVDE.atium_mod.block.custom;

import com.BAVDE.atium_mod.block.ModBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;

import javax.annotation.Nullable;
import java.util.Random;

public class EmptyAtiumOreOvergrown extends Block {
    public static final BooleanProperty TICKING = BooleanProperty.create("ticking");
    public static final IntegerProperty DELAY = IntegerProperty.create("delay", 0, 20);

    public EmptyAtiumOreOvergrown(Properties p_49795_) {
        super(p_49795_);
        this.registerDefaultState(this.defaultBlockState().setValue(TICKING, Boolean.valueOf(true)));
        this.registerDefaultState(this.defaultBlockState().setValue(DELAY, Integer.valueOf(0)));
    }

    @Override
    public boolean isRandomlyTicking(BlockState pState) {
        return pState.getValue(TICKING); //makes the block tick
    }

    @Override
    public void randomTick(BlockState pState, ServerLevel pLevel, BlockPos pPos, Random pRandom) {
        if (pState.getValue(TICKING)) { //if the block is ticking (should be always)
            int currentDelay = pState.getValue(DELAY); //sets a variable that is that same as delay state
            //takes about 20 - 30 mins to regenerate ore
            if (currentDelay == 20) {
                pLevel.setBlock(pPos, ModBlocks.ATIUM_ORE_OVERGROWN.get().defaultBlockState(), 3);
            } else if (currentDelay < 20){
                pLevel.setBlock(pPos, pState.setValue(DELAY, (currentDelay + 1)), 3);
            }
        }
    }

    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder) {
        pBuilder.add(TICKING, DELAY);
    }
}
