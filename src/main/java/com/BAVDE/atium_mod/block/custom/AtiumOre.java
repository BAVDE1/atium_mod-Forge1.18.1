package com.BAVDE.atium_mod.block.custom;

import com.BAVDE.atium_mod.block.ModBlocks;
import com.BAVDE.atium_mod.particle.ModParticles;
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
    public final Random random = new Random();

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
        float chance = 0.3f;
        if (chance < pRandom.nextFloat()) {
            for (int i = 0; i < 16; ++i) {
                double d0 = (double) (this.random.nextFloat() * 2.0F - 1.0F);
                double d1 = (double) (this.random.nextFloat() * 2.0F - 1.0F);
                double d2 = (double) (this.random.nextFloat() * 2.0F - 1.0F);
                if (!(d0 * d0 + d1 * d1 + d2 * d2 > 1.0D)) {
                    double d3 = BlockPos.getX((long) (d0 / 4.0D));
                    double d4 = BlockPos.getY((long) (0.5D + d1 / 4.0D));
                    double d5 = BlockPos.getZ((long) (d2 / 4.0D));
                    pLevel.addParticle(ModParticles.SNOWFLAKE_PARTICLES.get(), d3, d4, d5, d0, d1 + 0.2D, d2);
                    //this.level.addParticle(this.particleType, false, d3, d4, d5, d0, d1 + 0.2D, d2);
                }
            }
            pLevel.addParticle(ModParticles.SNOWFLAKE_PARTICLES.get(), pPos.getX(), pPos.getY(), pPos.getZ(), 1f, 1f, 1f);

        }
    }
}
