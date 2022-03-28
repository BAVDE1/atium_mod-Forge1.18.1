package com.BAVDE.atium_mod.block.custom;

import net.minecraft.client.resources.sounds.Sound;
import net.minecraft.client.resources.sounds.SoundInstance;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.LeavesBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraftforge.client.event.sound.SoundEvent;
import org.lwjgl.system.CallbackI;

import java.util.Random;

public class BuddingCrystallineLeaves extends LeavesBlock {
    public static final IntegerProperty GROWTH = IntegerProperty.create("growth", 0, 10);

    public BuddingCrystallineLeaves(Properties p_54422_) {
        super(p_54422_);
        this.registerDefaultState(this.defaultBlockState().setValue(GROWTH, 0));
    }

    @Override
    public boolean isFlammable(BlockState state, BlockGetter world, BlockPos pos, Direction face) {return true;}
    @Override
    public int getFlammability(BlockState state, BlockGetter world, BlockPos pos, Direction face) {return 60;}
    @Override
    public int getFireSpreadSpeed(BlockState state, BlockGetter world, BlockPos pos, Direction face) {return 30;}

    @Override
    public boolean isRandomlyTicking(BlockState pState) {
        return true;
    }

    @Override
    public void randomTick(BlockState pState, ServerLevel pLevel, BlockPos pPos, Random pRandom) {
        //leaf decay
        if (!pState.getValue(PERSISTENT) && pState.getValue(DISTANCE) == 7) {
            dropResources(pState, pLevel, pPos);
            pLevel.removeBlock(pPos, false);
        }

        int growth = pState.getValue(GROWTH);

        if (growth < 10) { //if growth less than 10
            pLevel.setBlock(pPos, pState.setValue(GROWTH, (growth + 1)), 3); //add 1 to growth state
        }
    }

    @Override
    public InteractionResult use(BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer, InteractionHand pHand, BlockHitResult pHit) {
        if(!pLevel.isClientSide() && pHand == InteractionHand.MAIN_HAND) {
            if (pState.getValue(GROWTH) == 10) {
                pLevel.setBlock(pPos, pState.setValue(GROWTH, 0), 3);
                //pLevel.playSound(pPlayer, pPos, SoundEvent.);
                return InteractionResult.SUCCESS;
            } else {
                return InteractionResult.PASS;
            }
        } else {
            return InteractionResult.PASS;
        }
    }

    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder) {
        pBuilder.add(GROWTH, DISTANCE, PERSISTENT);
    }
}