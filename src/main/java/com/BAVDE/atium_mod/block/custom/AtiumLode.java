package com.BAVDE.atium_mod.block.custom;

import com.BAVDE.atium_mod.block.ModBlocks;
import com.sun.jna.platform.win32.WinDef;
import net.minecraft.client.renderer.FaceInfo;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.ScoreComponent;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import oshi.util.Constants;

import java.util.Random;

public class AtiumLode extends Block {
    public static final IntegerProperty CHARGE = IntegerProperty.create("charge", 0, 101);
    public static final BooleanProperty SPAWN_ORE = BooleanProperty.create("spawn_ore");

    public AtiumLode(Properties p_49795_) {
        super(p_49795_);
        this.registerDefaultState(this.defaultBlockState().setValue(CHARGE, 0));
        this.registerDefaultState(this.defaultBlockState().setValue(SPAWN_ORE, false));
    }

    @Override //needs this for block state (CHARGE) to work
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder) {
        super.createBlockStateDefinition(pBuilder.add(CHARGE, SPAWN_ORE));
    }

    //code to charge the atium lode
    @Override
    public InteractionResult use(BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer, InteractionHand pHand, BlockHitResult pHit) {
        ItemStack itemstack = pPlayer.getItemInHand(pHand); //gets what the player is holding
        int currentState = pState.getValue(CHARGE); //sets a variable to hold the charge

        BlockPos pos = new BlockPos(pPlayer.position());// RayTraceUtil.getTargetBlockPos(player, world, 25);
        Block targetBlock = ModBlocks.ATIUM_ORE.get();

        //different charge for different items
        if (!itemstack.is(Items.COPPER_INGOT)) {
            if (!itemstack.is(Items.GOLD_INGOT)) {
                if (!itemstack.is(Items.IRON_INGOT)) {
                    if (!itemstack.is(Items.EMERALD)) {
                        if (!itemstack.is(Items.DIAMOND)) {
                            if (!itemstack.is(Items.NETHERITE_INGOT)) {
                                pPlayer.sendMessage(new TextComponent("charge: " + (currentState)), pPlayer.getUUID()); //print it in chat TEMPORARY
                                return InteractionResult.PASS; //do nothing
                            } else { //NETHERITE
                                if (!pLevel.isClientSide) {
                                    itemstack.shrink(1); //shrinks the stack by 1
                                }
                            }
                        } else { //DIAMOND
                            if (!pLevel.isClientSide) {
                                pLevel.setBlock(pPos, pState.setValue(CHARGE, (currentState + 50)), 3); //adds 50 charge to block state
                                itemstack.shrink(1); //shrinks the stack by 1
                            }
                        }
                    } else { //EMERALD
                        if (!pLevel.isClientSide) {
                            pLevel.setBlock(pPos, pState.setValue(CHARGE, (currentState + 5)), 3); //adds 5 charge to block state
                            itemstack.shrink(1); //shrinks the stack by 1
                        }
                    }
                } else { //IRON
                    if (!pLevel.isClientSide) {
                        pLevel.setBlock(pPos, pState.setValue(CHARGE, (currentState + 5)), 3); //adds 5 charge to block state
                        itemstack.shrink(1); //shrinks the stack by 1
                    }
                }
            } else { //GOLD
                if (!pLevel.isClientSide) {
                    pLevel.setBlock(pPos, pState.setValue(CHARGE, (currentState + 2)), 3); //adds 2 charge to block state
                    itemstack.shrink(1); //shrinks the stack by 1
                }
            }
        } else { //COPPER
            if (!pLevel.isClientSide) {
                pLevel.setBlock(pPos, pState.setValue(CHARGE, (currentState + 1)), 3); //adds 1 charge to block state
                itemstack.shrink(1); //shrinks the stack by 1
            }
        }
        return InteractionResult.SUCCESS; //end
    }
}