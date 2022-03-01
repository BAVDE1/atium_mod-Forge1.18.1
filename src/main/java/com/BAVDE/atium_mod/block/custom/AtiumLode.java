package com.BAVDE.atium_mod.block.custom;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;

import java.util.Random;

public class AtiumLode extends Block {
    private Object level;

    public AtiumLode(Properties p_49795_) {
        super(p_49795_);
    }

    /*protected InteractionResult mobInteract(Player pPlayer, InteractionHand pHand) {
        ItemStack itemstack = pPlayer.getItemInHand(pHand);
        if (!itemstack.is(Items.IRON_INGOT)) {
            return InteractionResult.PASS;
        } else {
            pPlayer.sendMessage(new TranslatableComponent("block.atium_mod.atium_lode.message_recieved"),
                    pPlayer.getUUID());
        }
        return InteractionResult.SUCCESS;
    }*/

    @Override
    public InteractionResult use(BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer, InteractionHand pHand, BlockHitResult pHit) {
        /*ItemStack itemstack = pPlayer.getItemInHand(pHand);
        if (!itemstack.is(Items.IRON_INGOT)) {
            return InteractionResult.PASS;
        } else {*/
            pPlayer.sendMessage(new TranslatableComponent("block.atium_mod.atium_lode.message_received"),
                    pPlayer.getUUID());
        //}
        return InteractionResult.SUCCESS;
    }
}
