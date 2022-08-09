package com.BAVDE.atium_mod.item.custom;

import net.minecraft.client.gui.screens.Screen;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;

import javax.annotation.Nullable;
import java.util.List;

public class AtiumChestplate extends ArmorItem {
    public AtiumChestplate(ArmorMaterial pMaterial, EquipmentSlot pSlot, Properties pProperties) {
        super(pMaterial, pSlot, pProperties);
    }

    @Override
    public void onArmorTick(ItemStack stack, Level level, Player player) {
        if (stack.getTag().contains("atium_mod.metal")) {
            int currentMetal = stack.getTag().getInt("atium_mod.metal");
            switch (currentMetal) { //1=iron, 2=steel, 3=tin, 4=pewter, 5=brass, 6=zinc, 7=copper, 8=bronze, 9=gold
                case 1 -> iron(8.0D, 80, level, player); //pulls nearby items towards
                //2 steel is in ModEvents
                case 3 -> tin(6, player); //gives resistance if under 7hp
                //4 pewter is in ModEvents
                //5 brass is in ModEvents
                //6 zinc is in ModEvents
                case 9 -> gold(15, stack, level, player); //heals 1/2 heart every 15 sec
            }
        }
    }

    /**** INFUSION FUNCTIONALITIES ****/

    //pulls nearby items towards
    private void iron(double range, int pullingSpeedDivision, Level level, Player player) {
        if (player.isCrouching()) {
            //code explained in iron method, atium sword class
            AABB aabb = player.getBoundingBox().inflate(range, range, range);
            List<ItemEntity> itemEntityList = level.getEntitiesOfClass(ItemEntity.class, aabb);
            for (ItemEntity itemEntity : itemEntityList) {
                double pX = player.getX() - itemEntity.getX();
                double pZ;
                for (pZ = player.getZ() - itemEntity.getZ(); pX * pX + pZ * pZ < 1.0E-4D; pZ = (Math.random() - Math.random()) * 0.01D) {
                    pX = (Math.random() - Math.random()) * 0.01D;
                }
                itemEntity.push((pX / pullingSpeedDivision), 0, (pZ / pullingSpeedDivision));
            }
        }
    }

    //gives resistance if under 7hp
    private void tin(int healthTarget, Player player) {
        if (player.getHealth() <= healthTarget && !player.hasEffect(MobEffects.DAMAGE_RESISTANCE)) {
            player.addEffect(new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, 15));
        }
    }

    //heals 1/2 heart every 15 sec
    private void gold(int cooldownSecs, ItemStack stack, Level level, Player player) {
        if (player.getHealth() < player.getMaxHealth() && !player.getCooldowns().isOnCooldown(stack.getItem())) {
            //15 seconds
            player.getCooldowns().addCooldown(stack.getItem(), cooldownSecs * 20);
            player.heal(1f);
            player.playSound(SoundEvents.EXPERIENCE_ORB_PICKUP, 0.3f, 1.5F + level.random.nextFloat() * 2.0F);
            for (int i = 0; i < 8; i++) {
                level.addParticle(ParticleTypes.HAPPY_VILLAGER, player.getRandomX(1.0D), player.getRandomY() + 0.5D, player.getRandomZ(1.0D), 0.0D, 0.0D, 0.0D);
            }
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

    //hover text
    @Override
    public void appendHoverText(ItemStack itemStack, @Nullable Level pLevel, List<Component> pTooltipComponents, TooltipFlag pIsAdvanced) {
        if (itemStack.getTag() != null && itemStack.getTag().contains("atium_mod.metal")) {
            int currentMetal = itemStack.getTag().getInt("atium_mod.metal");
            if (Screen.hasControlDown()) {
                switch (currentMetal) { //1=iron, 2=steel, 3=tin, 4=pewter, 5=brass, 6=zinc, 7=copper, 8=bronze, 9=gold
                    case 1 -> pTooltipComponents.add(new TranslatableComponent("tooltip.atium_mod.atium_chestplate.tooltip.iron.ctrl"));
                    case 2 -> pTooltipComponents.add(new TranslatableComponent("tooltip.atium_mod.atium_chestplate.tooltip.steel.ctrl"));
                    case 3 -> pTooltipComponents.add(new TranslatableComponent("tooltip.atium_mod.atium_chestplate.tooltip.tin.ctrl"));
                    case 4 -> pTooltipComponents.add(new TranslatableComponent("tooltip.atium_mod.atium_chestplate.tooltip.pewter.ctrl"));
                    case 5 -> pTooltipComponents.add(new TranslatableComponent("tooltip.atium_mod.atium_chestplate.tooltip.brass.ctrl"));
                    case 6 -> pTooltipComponents.add(new TranslatableComponent("tooltip.atium_mod.atium_chestplate.tooltip.zinc.ctrl"));
                    case 9 -> pTooltipComponents.add(new TranslatableComponent("tooltip.atium_mod.atium_chestplate.tooltip.gold.ctrl"));
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
