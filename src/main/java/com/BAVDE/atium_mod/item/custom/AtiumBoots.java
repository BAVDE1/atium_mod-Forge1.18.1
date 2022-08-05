package com.BAVDE.atium_mod.item.custom;

import com.BAVDE.atium_mod.block.ModBlocks;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.LiquidBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;

import javax.annotation.Nullable;
import java.util.List;

public class AtiumBoots extends ArmorItem {
    public AtiumBoots(ArmorMaterial pMaterial, EquipmentSlot pSlot, Properties pProperties) {
        super(pMaterial, pSlot, pProperties);
    }

    @Override
    public void onArmorTick(ItemStack stack, Level level, Player player) {
        if (stack.getTag().contains("atium_mod.metal")) {
            int currentMetal = stack.getTag().getInt("atium_mod.metal");
            switch (currentMetal) { //1=iron, 2=steel, 3=tin, 4=pewter, 5=brass, 6=zinc, 7=copper, 8=bronze, 9=gold
                case 1 -> iron(2, 10, stack, level, player); //gives player slow fall if falling and holds crouch
                case 2 -> steel(stack); //simply ticks down dash timer; and removes the tag when it reaches 0
                case 5 -> brass(2.5, level, player); //walk on water & lava; immunity to magma block damage
            }
        }
    }

    //gives player slow fall if falling and holds crouch
    private static void iron(int slowFallSecs, int cooldownSecs, ItemStack itemStack, Level level, Player player) {
        if (player.isCrouching() && player.fallDistance > 3.5 && !player.isOnGround() && !player.hasEffect(MobEffects.SLOW_FALLING)) {
            if (!player.getCooldowns().isOnCooldown(itemStack.getItem())) {
                if (!itemStack.getTag().contains("iron_boots_lock")) {
                    Vec3 vec3 = player.getDeltaMovement();
                    player.push(vec3.x, 0.7, vec3.z);
                    player.addEffect(new MobEffectInstance(MobEffects.SLOW_FALLING, slowFallSecs * 20, 2, false, false, true));
                    level.playLocalSound(player.getX(), player.getY(), player.getZ(), SoundEvents.ARMOR_EQUIP_ELYTRA, SoundSource.PLAYERS, 2, 1, false);
                    itemStack.getTag().putBoolean("atium_mod.iron_boots_use", true);
                    player.fallDistance = 0;
                }
            }
        }

        if (itemStack.getTag().contains("atium_mod.iron_boots_use")) {
            if (player.hasEffect(MobEffects.SLOW_FALLING)) {
                if (!player.isCrouching() || player.isOnGround()) {
                    endSlowFall(cooldownSecs, player, itemStack);
                }
            } else {
                endSlowFall(cooldownSecs, player, itemStack);
            }
        }
    }

    private static void endSlowFall(int cooldownSecs, Player player, ItemStack itemStack) {
        if (player.hasEffect(MobEffects.SLOW_FALLING)) {
            player.removeEffect(MobEffects.SLOW_FALLING);
        }
        itemStack.getTag().remove("atium_mod.iron_boots_use");
        player.getCooldowns().addCooldown(itemStack.getItem(), cooldownSecs * 20);
    }

    //simply ticks down dash timer; and removes the tag when it reaches 0
    private static void steel(ItemStack itemStack) {
        //left dash
        if (itemStack.getTag().contains("atium_mod.left_dash_ready")) {
            int leftDashTimer = itemStack.getTag().getInt("atium_mod.left_dash_ready");
            //if dash timer is 0 remove tag; else tick down the timer
            if (leftDashTimer <= 0) {
                itemStack.getTag().remove("atium_mod.left_dash_ready");
            } else {
                itemStack.getTag().putInt("atium_mod.left_dash_ready", --leftDashTimer);
            }
        }

        //right dash
        if (itemStack.getTag().contains("atium_mod.right_dash_ready")) {
            int rightDashTimer = itemStack.getTag().getInt("atium_mod.right_dash_ready");
            //if dash timer is 0 remove tag; else tick down the timer
            if (rightDashTimer <= 0) {
                itemStack.getTag().remove("atium_mod.right_dash_ready");
            } else {
                itemStack.getTag().putInt("atium_mod.right_dash_ready", --rightDashTimer);
            }
        }
    }

    //walk on water & lava; immunity to magma block damage
    private static void brass(double range, Level level, Player player) {
        if (player.isOnGround() && !player.level.isClientSide) {
            BlockState iceBlockstate = Blocks.FROSTED_ICE.defaultBlockState();
            BlockState magmaBlockstate = ModBlocks.FRACTURED_MAGMA_BLOCK.get().defaultBlockState();
            //path width from player (e.g. 3 = 3 blocks on either side)
            BlockPos.MutableBlockPos blockpos$mutableblockpos = new BlockPos.MutableBlockPos();
            BlockPos pos = player.blockPosition();

            //gets all blocks at feet within range
            for (BlockPos blockpos : BlockPos.betweenClosed(pos.offset(-range, -1.0D, -range), pos.offset(range, -1.0D, range))) {
                if (blockpos.closerToCenterThan(player.position(), range)) {
                    blockpos$mutableblockpos.set(blockpos.getX(), blockpos.getY() + 1, blockpos.getZ());
                    BlockState blockstate1 = level.getBlockState(blockpos$mutableblockpos);
                    if (blockstate1.isAir()) {
                        BlockState blockstate2 = level.getBlockState(blockpos);

                        boolean isFullWaterBlock = blockstate2.getBlock() == Blocks.WATER && blockstate2.getValue(LiquidBlock.LEVEL) == 0;
                        boolean isFullLavaBlock = blockstate2.getBlock() == Blocks.LAVA && blockstate2.getValue(LiquidBlock.LEVEL) == 0;

                        //if water block place ice else if lava block place magma
                        if (blockstate2.getMaterial() == Material.WATER && isFullWaterBlock && iceBlockstate.canSurvive(level, blockpos) && level.isUnobstructed(iceBlockstate, blockpos, CollisionContext.empty()) && !net.minecraftforge.event.ForgeEventFactory.onBlockPlace(player, net.minecraftforge.common.util.BlockSnapshot.create(level.dimension(), level, blockpos), net.minecraft.core.Direction.UP)) {
                            level.setBlockAndUpdate(blockpos, iceBlockstate);
                            level.scheduleTick(blockpos, Blocks.FROSTED_ICE, Mth.nextInt(player.getRandom(), 60, 120));
                        } //lava
                        else if (blockstate2.getMaterial() == Material.LAVA && isFullLavaBlock && magmaBlockstate.canSurvive(level, blockpos) && level.isUnobstructed(magmaBlockstate, blockpos, CollisionContext.empty()) && !net.minecraftforge.event.ForgeEventFactory.onBlockPlace(player, net.minecraftforge.common.util.BlockSnapshot.create(level.dimension(), level, blockpos), net.minecraft.core.Direction.UP)) {
                            level.setBlockAndUpdate(blockpos, magmaBlockstate);
                            level.scheduleTick(blockpos, ModBlocks.FRACTURED_MAGMA_BLOCK.get(), Mth.nextInt(player.getRandom(), 60, 120));
                        }
                    }
                }
            }
        }
    }

    //changes items' name colour when infused
    @Override
    public Rarity getRarity(ItemStack pStack) {
        if (pStack.getTag().contains("atium_mod.metal")) {
            return Rarity.UNCOMMON;
        } else {
            return super.getRarity(pStack);
        }
    }

    //changes armour model texture
    @Nullable
    @Override
    public String getArmorTexture(ItemStack itemStack, Entity entity, EquipmentSlot slot, String type) {
        int copper = 0;
        //checks if item has copper cloud
        if (itemStack.getTag().contains("atium_mod.copper_cloud")) {
            copper = itemStack.getTag().getInt("atium_mod.copper_cloud");
        }
        if (itemStack.getTag().contains("atium_mod.metal") && copper == 0) {
            int currentMetal = itemStack.getTag().getInt("atium_mod.metal");
            switch (currentMetal) { //1=iron, 2=steel, 3=tin, 4=pewter, 5=brass, 6=zinc, 7=copper, 8=bronze, 9=gold
                case 1: return "atium_mod:textures/models/armor/atium_iron_layer_1.png";
                case 2: return null;
                case 3: return null;
                case 4: return null;
                case 5: return null;
                case 6: return null;
                case 9: return "atium_mod:textures/models/armor/atium_gold_layer_1.png";
            }
        }
        return super.getArmorTexture(itemStack, entity, slot, type);
    }

    @Override
    public void appendHoverText(ItemStack pStack, @Nullable Level pLevel, List<Component> pTooltipComponents, TooltipFlag pIsAdvanced) {
        if (pStack.getTag().contains("atium_mod.metal")) {
            int currentMetal = pStack.getTag().getInt("atium_mod.metal");
            if (Screen.hasControlDown()) {
                switch (currentMetal) { //1=iron, 2=steel, 3=tin, 4=pewter, 5=brass, 6=zinc, 7=copper, 8=bronze, 9=gold
                    case 1 -> pTooltipComponents.add(new TranslatableComponent("tooltip.atium_mod.atium_boots.tooltip.iron.ctrl"));
                    case 2 -> pTooltipComponents.add(new TranslatableComponent("tooltip.atium_mod.atium_boots.tooltip.steel.ctrl"));
                    case 3 -> pTooltipComponents.add(new TranslatableComponent("tooltip.atium_mod.atium_boots.tooltip.tin.ctrl"));
                    case 4 -> pTooltipComponents.add(new TranslatableComponent("tooltip.atium_mod.atium_boots.tooltip.pewter.ctrl"));
                    case 5 -> pTooltipComponents.add(new TranslatableComponent("tooltip.atium_mod.atium_boots.tooltip.brass.ctrl"));
                    case 6 -> pTooltipComponents.add(new TranslatableComponent("tooltip.atium_mod.atium_boots.tooltip.zinc.ctrl"));
                    case 9 -> pTooltipComponents.add(new TranslatableComponent("tooltip.atium_mod.atium_boots.tooltip.gold.ctrl"));
                }
            } else {
                switch (currentMetal) { //1=iron, 2=steel, 3=tin, 4=pewter, 5=brass, 6=zinc, 7=copper, 8=bronze, 9=gold
                    case 1 -> pTooltipComponents.add(new TranslatableComponent("tooltip.atium_mod.tooltip.iron"));
                    case 2 -> pTooltipComponents.add(new TranslatableComponent("tooltip.atium_mod.tooltip.steel"));
                    case 3 -> pTooltipComponents.add(new TranslatableComponent("tooltip.atium_mod.tooltip.tin"));
                    case 4 -> pTooltipComponents.add(new TranslatableComponent("tooltip.atium_mod.tooltip.pewter"));
                    case 5 -> pTooltipComponents.add(new TranslatableComponent("tooltip.atium_mod.tooltip.brass"));
                    case 6 -> pTooltipComponents.add(new TranslatableComponent("tooltip.atium_mod.tooltip.zinc"));
                    case 9 -> pTooltipComponents.add(new TranslatableComponent("tooltip.atium_mod.tooltip.gold"));
                }
            }
        }
        if (pStack.getTag().contains("atium_mod.copper_cloud")) {
            if (pStack.getTag().getInt("atium_mod.copper_cloud") == 1) {
                pTooltipComponents.add(new TranslatableComponent("tooltip.atium_mod.has_copper_cloud"));
            }
        }
        super.appendHoverText(pStack, pLevel, pTooltipComponents, pIsAdvanced);
    }
}
