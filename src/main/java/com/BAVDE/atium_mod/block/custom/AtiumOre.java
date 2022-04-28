package com.BAVDE.atium_mod.block.custom;

import com.BAVDE.atium_mod.block.ModBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

import java.util.Random;

public class AtiumOre extends Block {
    public AtiumOre(Properties p_49795_) {
        super(p_49795_);
    }

    @Override
    public void playerDestroy(Level pLevel, Player pPlayer, BlockPos pPos, BlockState pState, @Nullable BlockEntity pBlockEntity, ItemStack pTool) {
        super.playerDestroy(pLevel, pPlayer, pPos, pState, pBlockEntity, pTool);
        //when block is destroyed replace with empty atium ore
        pLevel.setBlock(pPos, ModBlocks.EMPTY_ATIUM_ORE.get().defaultBlockState(), 3);
    }

    @Override
    public void animateTick(BlockState pState, Level pLevel, BlockPos pPos, Random pRandom) {
        super.animateTick(pState, pLevel, pPos, pRandom);
        float chance = 0.5f;
        if (chance < pRandom.nextFloat()) {
            pLevel.addParticle(ParticleTypes.EXPLOSION_EMITTER, pPos.getX(), pPos.getY(), pPos.getZ(), 1.0D, 0.0D, 0.0D);
            pLevel.addParticle(ParticleTypes.EXPLOSION, pPos.getX(), pPos.getY(), pPos.getZ(), 1.0D, 0.0D, 0.0D);

            /*for (int i = 0; i < 10; i++) {
                pLevel.addParticle(ParticleTypes.ENCHANTED_HIT, pPos.getX(), pPos.getY(), pPos.getZ(),
                        1d, 0d + pRandom.nextDouble(0.05d), 1d);
            }*/
        }
    }
}
