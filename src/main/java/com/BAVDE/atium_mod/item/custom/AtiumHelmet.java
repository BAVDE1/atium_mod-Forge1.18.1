package com.BAVDE.atium_mod.item.custom;

import com.BAVDE.atium_mod.block.ModBlocks;
import com.BAVDE.atium_mod.item.ModItems;
import com.BAVDE.atium_mod.particle.ModParticles;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.phys.AABB;

import javax.annotation.Nullable;
import java.util.List;

public class AtiumHelmet extends ArmorItem {
    public AtiumHelmet(ArmorMaterial pMaterial, EquipmentSlot pSlot, Properties pProperties) {
        super(pMaterial, pSlot, pProperties);
    }

    @Override
    public void onArmorTick(ItemStack stack, Level level, Player player) {
        if (stack.getTag().contains("atium_mod.metal")) {
            int currentMetal = stack.getTag().getInt("atium_mod.metal");
            switch (currentMetal) { //1=iron, 2=steel, 3=tin, 4=pewter, 5=brass, 6=zinc, 7=copper, 8=bronze, 9=gold
                case 1 -> iron(level, player);
                case 2 -> steel(level, player);
                case 3 -> tin(player);
                case 5 -> brass(player);
                case 6 -> zinc(level, player);
                case 9 -> gold(stack, player, level);
            }
        }
    }

    private static void iron(Level level, LivingEntity player) {
        if (player.isCrouching()) {
            int range = 8;

            for (BlockPos pos : BlockPos.betweenClosed(player.getBlockX() - range, player.getBlockY() - range, player.getBlockZ() - range, player.getBlockX() + range, player.getBlockY() + range, player.getBlockZ() + range)) {
                Block block = level.getBlockState(pos.immutable()).getBlock();
                if (checkForMetalOre(block)) {
                    if (player.level.isClientSide && Math.random() < 0.3) {
                        player.level.addParticle(ModParticles.ORE_DETECTION_PARTICLES.get(), true, pos.getX() + Math.random(), pos.getY() + Math.random(), pos.getZ() + Math.random(), 0, 0, 0);
                    }
                }
            }
        }
    }

    private static void steel(Level level, LivingEntity player) {
        if (player.isCrouching()) {
            int range = 16;

            for (BlockPos pos : BlockPos.betweenClosed(player.getBlockX() - range, player.getBlockY() - range, player.getBlockZ() - range, player.getBlockX() + range, player.getBlockY() + range, player.getBlockZ() + range)) {
                Block block = level.getBlockState(pos.immutable()).getBlock();
                if (checkForMetalOre(block)) {
                    if (player.level.isClientSide && Math.random() < 0.3) {
                        player.level.addParticle(ModParticles.ORE_DETECTION_PARTICLES.get(), true, pos.getX() + Math.random(), pos.getY() + Math.random(), pos.getZ() + Math.random(), 0, 0, 0);
                    }
                }
            }
        }
    }

    private static boolean checkForMetalOre(Block block) {
        return block == Blocks.GOLD_ORE || block == Blocks.RAW_GOLD_BLOCK || block == Blocks.DEEPSLATE_GOLD_ORE || block == Blocks.NETHER_GOLD_ORE
                || block == Blocks.IRON_ORE || block == Blocks.RAW_IRON_BLOCK || block == Blocks.DEEPSLATE_IRON_ORE
                || block == Blocks.COPPER_ORE || block == Blocks.RAW_COPPER_BLOCK || block == Blocks.DEEPSLATE_COPPER_ORE
                || block == ModBlocks.ATIUM_ORE.get();
    }

    private static void tin(LivingEntity player) {
        if (player.isCrouching() && !player.hasEffect(MobEffects.NIGHT_VISION)) {
            player.addEffect(new MobEffectInstance(MobEffects.NIGHT_VISION, 99999, 0, false, false, false));
        } else if (!player.isCrouching() && player.hasEffect(MobEffects.NIGHT_VISION)) {
            //ModEvents for if helmet breaks when player is crouching
            player.removeEffect(MobEffects.NIGHT_VISION);
        }
    }

    private static void brass(LivingEntity player) {
        if (!player.isEyeInFluid(FluidTags.WATER)) {
            //duration 6 seconds
            player.addEffect(new MobEffectInstance(MobEffects.WATER_BREATHING, 120, 0, false, false));
        }
    }

    private static void zinc(Level level, LivingEntity player) {
        if (player.isCrouching()) {
            var range = 12.0D;
            AABB aabb = player.getBoundingBox().inflate(range, range, range);
            List<LivingEntity> entityList = level.getNearbyEntities(LivingEntity.class, TargetingConditions.DEFAULT, player, aabb);
            for (LivingEntity entity : entityList) {
                double pX = player.getX() - entity.getX();
                double pZ;
                for (pZ = player.getZ() - entity.getZ(); pX * pX + pZ * pZ < 1.0E-4D; pZ = (Math.random() - Math.random()) * 0.01D) {
                    pX = (Math.random() - Math.random()) * 0.01D;
                }
                if (player.level.isClientSide) {
                    player.level.addParticle(ModParticles.MOB_DETECTION_PARTICLES.get(), true, entity.getRandomX(1), entity.getRandomY(), entity.getRandomZ(1), 0, 0, 0);
                }
                //entity.addEffect(new MobEffectInstance(MobEffects.GLOWING, 2, 0, false, false, false), player);
            }
        }
    }

    private static void gold(ItemStack itemStack, Player player, Level level) {
        if (!player.level.isClientSide) {
            if (!player.getCooldowns().isOnCooldown(itemStack.getItem())) {
                ItemStack chestplateItem = player.getItemBySlot(EquipmentSlot.CHEST);
                ItemStack leggingsItem = player.getItemBySlot(EquipmentSlot.LEGS);
                ItemStack bootsItem = player.getItemBySlot(EquipmentSlot.FEET);

                //every 60 (1200 ticks) seconds heal random atium_mod armour piece by 1
                int cooldown = 1200;

                int chance = level.random.nextInt(4);
                switch (chance) {
                    case 0 -> {
                        if ((itemStack.getDamageValue() + itemStack.getMaxDamage()) != itemStack.getMaxDamage()) {
                            itemStack.setDamageValue(itemStack.getDamageValue() - 1);
                        }
                        player.getCooldowns().addCooldown(itemStack.getItem(), cooldown);
                    }
                    case 1 -> {
                        if (chestplateItem.getItem() == ModItems.ATIUM_CHESTPLATE.get() && (chestplateItem.getDamageValue() + chestplateItem.getMaxDamage()) != chestplateItem.getMaxDamage()) {
                            chestplateItem.setDamageValue(chestplateItem.getDamageValue() - 1);
                        }
                        player.getCooldowns().addCooldown(itemStack.getItem(), cooldown);
                    }
                    case 2 -> {
                        if (leggingsItem.getItem() == ModItems.ATIUM_LEGGINGS.get() && (leggingsItem.getDamageValue() + leggingsItem.getMaxDamage()) != leggingsItem.getMaxDamage()) {
                            leggingsItem.setDamageValue(leggingsItem.getDamageValue() - 1);
                        }
                        player.getCooldowns().addCooldown(itemStack.getItem(), cooldown);
                    }
                    case 3 -> {
                        if (bootsItem.getItem() == ModItems.ATIUM_BOOTS.get() && (bootsItem.getDamageValue() + bootsItem.getMaxDamage()) != bootsItem.getMaxDamage()) {
                            bootsItem.setDamageValue(bootsItem.getDamageValue() - 1);
                        }
                        player.getCooldowns().addCooldown(itemStack.getItem(), cooldown);
                    }
                    default -> player.getCooldowns().addCooldown(itemStack.getItem(), cooldown);
                }
            }
        }
    }

    @Override
    public void appendHoverText(ItemStack pStack, @Nullable Level pLevel, List<Component> pTooltipComponents, TooltipFlag pIsAdvanced) {
        if (pStack.getTag().contains("atium_mod.metal")) {
            int currentMetal = pStack.getTag().getInt("atium_mod.metal");
            if (Screen.hasControlDown()) {
                switch (currentMetal) { //1=iron, 2=steel, 3=tin, 4=pewter, 5=brass, 6=zinc, 7=copper, 8=bronze, 9=gold
                    case 1 -> pTooltipComponents.add(new TranslatableComponent("tooltip.atium_mod.atium_helmet.tooltip.iron.ctrl"));
                    case 2 -> pTooltipComponents.add(new TranslatableComponent("tooltip.atium_mod.atium_helmet.tooltip.steel.ctrl"));
                    case 3 -> pTooltipComponents.add(new TranslatableComponent("tooltip.atium_mod.atium_helmet.tooltip.tin.ctrl"));
                    case 4 -> pTooltipComponents.add(new TranslatableComponent("tooltip.atium_mod.atium_helmet.tooltip.pewter.ctrl"));
                    case 5 -> pTooltipComponents.add(new TranslatableComponent("tooltip.atium_mod.atium_helmet.tooltip.brass.ctrl"));
                    case 6 -> pTooltipComponents.add(new TranslatableComponent("tooltip.atium_mod.atium_helmet.tooltip.zinc.ctrl"));
                    case 9 -> pTooltipComponents.add(new TranslatableComponent("tooltip.atium_mod.atium_helmet.tooltip.gold.ctrl"));
                }
            } else {
                switch (currentMetal) { //1=iron, 2=steel, 3=tin, 4=pewter, 5=brass, 6=zinc, 7=copper, 8=bronze, 9=gold
                    case 1 -> pTooltipComponents.add(new TranslatableComponent("tooltip.atium_mod.tooltip.iron"));
                    case 2 -> pTooltipComponents.add(new TranslatableComponent("tooltip.atium_mod.tooltip.steel"));
                    case 3 -> pTooltipComponents.add(new TranslatableComponent("tooltip.atium_mod.tooltip.tin"));
                    case 4 -> pTooltipComponents.add(new TranslatableComponent("tooltip.atium_mod.tooltip.pewter"));
                    case 5 -> pTooltipComponents.add(new TranslatableComponent("tooltip.atium_mod.tooltip.brass"));
                    case 6 -> pTooltipComponents.add(new TranslatableComponent("tooltip.atium_mod.tooltip.zinc"));
                    case 7 -> pTooltipComponents.add(new TranslatableComponent("tooltip.atium_mod.tooltip.copper"));
                    case 8 -> pTooltipComponents.add(new TranslatableComponent("tooltip.atium_mod.tooltip.bronze"));
                    case 9 -> pTooltipComponents.add(new TranslatableComponent("tooltip.atium_mod.tooltip.gold"));
                }
            }
        }
        super.appendHoverText(pStack, pLevel, pTooltipComponents, pIsAdvanced);
    }
}
