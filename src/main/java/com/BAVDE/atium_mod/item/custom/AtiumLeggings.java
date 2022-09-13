package com.BAVDE.atium_mod.item.custom;

import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.level.Level;

import javax.annotation.Nullable;
import java.util.List;

public class AtiumLeggings extends ArmorItem {
    //used for zinc infusion to allow continued slow fall while crouch is held but item is on cooldown
    static boolean slowFallLock = false;

    public AtiumLeggings(ArmorMaterial pMaterial, EquipmentSlot pSlot, Properties pProperties) {
        super(pMaterial, pSlot, pProperties);
    }

    @Override
    public void onArmorTick(ItemStack itemStack, Level level, Player player) {
        if (itemStack.getTag() != null && itemStack.getTag().contains("atium_mod.metal")) {
            int currentMetal = itemStack.getTag().getInt("atium_mod.metal");
            switch (currentMetal) { //1=iron, 2=steel, 3=tin, 4=pewter, 5=brass, 6=zinc, 7=copper, 8=bronze, 9=gold
                case 5 -> brass(player, itemStack); //fire res for 6 secs when on fire
                //case 6 -> zinc(0.07, 8, stack, player);
            }
        }
    }

    //fire res for 6 secs when on fire
    private static void brass(Player player, ItemStack itemStack) {
        int seconds = 6;
        if (!player.isOnFire()) {
            if (player.getCooldowns().isOnCooldown(itemStack.getItem())) {
                player.getCooldowns().removeCooldown(itemStack.getItem());
            }
            player.addEffect(new MobEffectInstance(MobEffects.FIRE_RESISTANCE, seconds * 20, 0, false, false));
            removeGreenTag(itemStack);
        } else if (player.hasEffect(MobEffects.FIRE_RESISTANCE)){
            //adds cooldown to display how much water breathing is left
            if (!player.getCooldowns().isOnCooldown(itemStack.getItem())) {
                player.getCooldowns().addCooldown(itemStack.getItem(), seconds * 20);
            }
            addGreenTick(itemStack);
        } else if (!player.hasEffect(MobEffects.FIRE_RESISTANCE)) {
            removeGreenTag(itemStack);
        }
    }

    //slows players falling speed but doesn't reduce fall damage
    private static void oldZinc(double push, int cooldownSecs, ItemStack itemStack, Player player) {
        if (!player.isOnGround()) {
            if (player.isCrouching()) {
                if (slowFallLock) {
                    player.push(0, push, 0);
                } else if (!player.getCooldowns().isOnCooldown(itemStack.getItem()) && !slowFallLock) {
                    player.push(0, push, 0);
                    player.getCooldowns().addCooldown(itemStack.getItem(), cooldownSecs * 20);
                    slowFallLock = true;
                }
            }
        }
        if (!player.isCrouching()) {
            slowFallLock = false;
        }
        if (player.isOnGround()) {
            slowFallLock = false;
        }
    }

    //for hud elements
    private static void addGreenTick(ItemStack itemStack) {
        if (itemStack.getTag() != null && !itemStack.getTag().contains("atium_mod.green_tick")) {
            itemStack.getTag().putBoolean("atium_mod.green_tick", true);
        }
    }

    //for hud elements
    private static void removeGreenTag(ItemStack itemStack) {
        if (itemStack.getTag() != null && itemStack.getTag().contains("atium_mod.green_tick")) {
            itemStack.getTag().remove("atium_mod.green_tick");
        }
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
                    return "atium_mod:textures/models/armor/atium_iron_layer_2.png";
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
                    return "atium_mod:textures/models/armor/atium_gold_layer_2.png";
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
                    case 1 -> pTooltipComponents.add(new TranslatableComponent("tooltip.atium_mod.atium_leggings.tooltip.iron.ctrl"));
                    case 2 -> pTooltipComponents.add(new TranslatableComponent("tooltip.atium_mod.atium_leggings.tooltip.steel.ctrl"));
                    case 3 -> pTooltipComponents.add(new TranslatableComponent("tooltip.atium_mod.atium_leggings.tooltip.tin.ctrl"));
                    case 4 -> pTooltipComponents.add(new TranslatableComponent("tooltip.atium_mod.atium_leggings.tooltip.pewter.ctrl"));
                    case 5 -> pTooltipComponents.add(new TranslatableComponent("tooltip.atium_mod.atium_leggings.tooltip.brass.ctrl"));
                    case 6 -> pTooltipComponents.add(new TranslatableComponent("tooltip.atium_mod.atium_leggings.tooltip.zinc.ctrl"));
                    case 9 -> pTooltipComponents.add(new TranslatableComponent("tooltip.atium_mod.atium_leggings.tooltip.gold.ctrl"));
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
