package com.BAVDE.atium_mod.item.custom;

import com.BAVDE.atium_mod.particle.ModParticles;
import com.BAVDE.atium_mod.util.ModTags;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.phys.AABB;

import javax.annotation.Nullable;
import java.util.List;

public class AtiumHelmet extends ArmorItem {
    public AtiumHelmet(ArmorMaterial pMaterial, EquipmentSlot pSlot, Properties pProperties) {
        super(pMaterial, pSlot, pProperties);
    }

    @Override
    public void onArmorTick(ItemStack itemStack, Level level, Player player) {
        if (itemStack.getTag() != null && itemStack.getTag().contains("atium_mod.metal")) {
            int currentMetal = itemStack.getTag().getInt("atium_mod.metal");
            switch (currentMetal) { //1=iron, 2=steel, 3=tin, 4=pewter, 5=brass, 6=zinc, 7=copper, 8=bronze, 9=gold
                case 1 -> ironAndSteel(8, level, player); //detects nearby metal ores (weak)
                case 2 -> ironAndSteel(16, level, player); //detects nearby ores (strong)
                case 3 -> tin(player); //gives night vision when crouching
                case 4 -> pewter(20, player, level); //fast eating & more saturation
                case 5 -> brass(6, player); //water breathing for 6 secs in water
                case 6 -> zinc(12.0D, level, player); //detects nearby mobs
                case 9 -> gold(60, itemStack, player, level); //mends other armour equipped by 1
            }
        }
    }

    //detects nearby metal ores
    private static void ironAndSteel(int range, Level level, LivingEntity player) {
        if (player.isCrouching()) {
            for (BlockPos pos : BlockPos.betweenClosed(player.getBlockX() - range, player.getBlockY() - range, player.getBlockZ() - range, player.getBlockX() + range, player.getBlockY() + range, player.getBlockZ() + range)) {
                Block block = level.getBlockState(pos.immutable()).getBlock();
                if (checkForMetalOre(block)) {
                    if (player.level.isClientSide && Math.random() < 0.3) {
                        player.level.addParticle(ModParticles.ORE_DETECTION_PARTICLES.get(), true, pos.getX() + Math.random(), pos.getY() + Math.random(), pos.getZ() + Math.random(), 0, 0, 0);
                    }
                }
            }
            consumeHunger((Player) player);
        }
    }

    private static boolean checkForMetalOre(Block block) {
        return block.defaultBlockState().is(ModTags.Blocks.METAL_ORES);
    }

    //gives night vision when crouching
    private static void tin(LivingEntity player) {
        if (player.isCrouching()) {
            if (!player.hasEffect(MobEffects.NIGHT_VISION)) {
                player.addEffect(new MobEffectInstance(MobEffects.NIGHT_VISION, 99999, 0, false, false, false));
            } else {
                consumeHunger((Player) player);
            }
        } else if (player.hasEffect(MobEffects.NIGHT_VISION)) {
            //ModEvents for if helmet breaks when player is crouching
            player.removeEffect(MobEffects.NIGHT_VISION);
        }
    }

    //fast eating & more saturation
    private static void pewter(int eatingTicksFaster, Player player, Level level) {
        if (!player.level.isClientSide) {
            if (player.isUsingItem()) {
                ItemStack itemStackUse = player.getUseItem();
                Item item = itemStackUse.getItem();

                if (item.isEdible() && player.getUseItemRemainingTicks() == eatingTicksFaster) {
                    itemStackUse.finishUsingItem(level, player);
                    player.getFoodData().setSaturation(player.getFoodData().getSaturationLevel() + 1);
                    player.stopUsingItem();
                }
            }
        }
    }

    //water breathing for 6 secs in water
    private static void brass(int seconds, LivingEntity player) {
        if (!player.isEyeInFluid(FluidTags.WATER)) {
            player.addEffect(new MobEffectInstance(MobEffects.WATER_BREATHING, seconds * 20, 0, false, false));
        }
    }

    //detects nearby mobs
    private static void zinc(double range, Level level, LivingEntity player) {
        if (player.isCrouching()) {
            AABB aabb = player.getBoundingBox().inflate(range, range, range);
            List<LivingEntity> entityList = level.getNearbyEntities(LivingEntity.class, TargetingConditions.DEFAULT, player, aabb);
            for (LivingEntity entity : entityList) {
                double pX = player.getX() - entity.getX();
                double pZ;
                for (pZ = player.getZ() - entity.getZ(); pX * pX + pZ * pZ < 1.0E-4D; pZ = (Math.random() - Math.random()) * 0.01D) {
                    pX = (Math.random() - Math.random()) * 0.01D;
                }
                if (player.level.isClientSide && Math.random() < 0.6) {
                    player.level.addParticle(ModParticles.MOB_DETECTION_PARTICLES.get(), true, entity.getRandomX(1), entity.getRandomY(), entity.getRandomZ(1), 0, 0, 0);
                }
            }
            consumeHunger((Player) player);
        }
    }

    //mends other armour equipped by 1
    private static void gold(int cooldownSeconds, ItemStack itemStack, Player player, Level level) {
        if (!player.level.isClientSide) {
            if (!player.getCooldowns().isOnCooldown(itemStack.getItem())) {
                ItemStack chestplateItem = player.getItemBySlot(EquipmentSlot.CHEST);
                ItemStack leggingsItem = player.getItemBySlot(EquipmentSlot.LEGS);
                ItemStack bootsItem = player.getItemBySlot(EquipmentSlot.FEET);

                //every 60 (1200 ticks) seconds heal random atium_mod armour piece by 1
                int cooldown = cooldownSeconds * 20;

                int chance = level.random.nextInt(4);
                switch (chance) {
                    case 0 -> {
                        if ((itemStack.getDamageValue() + itemStack.getMaxDamage()) != itemStack.getMaxDamage()) {
                            itemStack.setDamageValue(itemStack.getDamageValue() - 1);
                        }
                        player.getCooldowns().addCooldown(itemStack.getItem(), cooldown);
                    }
                    case 1 -> {
                        if ((chestplateItem.getDamageValue() + chestplateItem.getMaxDamage()) != chestplateItem.getMaxDamage()) {
                            chestplateItem.setDamageValue(chestplateItem.getDamageValue() - 1);
                        }
                        player.getCooldowns().addCooldown(itemStack.getItem(), cooldown);
                    }
                    case 2 -> {
                        if ((leggingsItem.getDamageValue() + leggingsItem.getMaxDamage()) != leggingsItem.getMaxDamage()) {
                            leggingsItem.setDamageValue(leggingsItem.getDamageValue() - 1);
                        }
                        player.getCooldowns().addCooldown(itemStack.getItem(), cooldown);
                    }
                    case 3 -> {
                        if ((bootsItem.getDamageValue() + bootsItem.getMaxDamage()) != bootsItem.getMaxDamage()) {
                            bootsItem.setDamageValue(bootsItem.getDamageValue() - 1);
                        }
                        player.getCooldowns().addCooldown(itemStack.getItem(), cooldown);
                    }
                    default -> player.getCooldowns().addCooldown(itemStack.getItem(), cooldown);
                }
            }
        }
    }

    //used in iron, steel, tin & zinc to slowly consume players hunger while using
    private static void consumeHunger(Player player) {
        player.getFoodData().addExhaustion(0.5F);
    }

    //changes items' name colour when infused
    @Override
    public Rarity getRarity(ItemStack itemStack) {
        if (itemStack.getTag() != null && itemStack.getTag().contains("atium_mod.metal")) {
            return Rarity.UNCOMMON;
        } else {
            return super.getRarity(itemStack);
        }
    }

    //changes armour model texture
    @Nullable
    @Override
    public String getArmorTexture(ItemStack itemStack, Entity entity, EquipmentSlot slot, String type) {
        int copper = 0;
        //checks if item has copper cloud
        if (itemStack.getTag() != null && itemStack.getTag().contains("atium_mod.copper_cloud")) {
            copper = itemStack.getTag().getInt("atium_mod.copper_cloud");
        }
        if (itemStack.getTag().contains("atium_mod.metal") && copper == 0) {
            int currentMetal = itemStack.getTag().getInt("atium_mod.metal");
            switch (currentMetal) { //1=iron, 2=steel, 3=tin, 4=pewter, 5=brass, 6=zinc, 7=copper, 8=bronze, 9=gold
                case 1:
                    return "atium_mod:textures/models/armor/atium_iron_layer_1.png";
                case 2:
                    return null;
                case 3:
                    return null;
                case 4:
                    return null;
                case 5:
                    return null;
                case 6:
                    return null;
                case 9:
                    return "atium_mod:textures/models/armor/atium_gold_layer_1.png";
            }
        }
        return super.getArmorTexture(itemStack, entity, slot, type);
    }

    @Override
    public void appendHoverText(ItemStack itemStack, @Nullable Level pLevel, List<Component> pTooltipComponents, TooltipFlag pIsAdvanced) {
        if (itemStack.getTag() != null && itemStack.getTag().contains("atium_mod.metal")) {
            int currentMetal = itemStack.getTag().getInt("atium_mod.metal");
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
                    case 9 -> pTooltipComponents.add(new TranslatableComponent("tooltip.atium_mod.tooltip.gold"));
                }
            }
        }
        if (itemStack.getTag().contains("atium_mod.copper_cloud")) {
            if (itemStack.getTag().getInt("atium_mod.copper_cloud") == 1) {
                pTooltipComponents.add(new TranslatableComponent("tooltip.atium_mod.has_copper_cloud"));
            }
        }
        super.appendHoverText(itemStack, pLevel, pTooltipComponents, pIsAdvanced);
    }
}
