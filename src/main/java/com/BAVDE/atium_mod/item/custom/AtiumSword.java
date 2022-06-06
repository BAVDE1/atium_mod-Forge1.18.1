package com.BAVDE.atium_mod.item.custom;

import com.BAVDE.atium_mod.effect.ModMobEffects;
import com.BAVDE.atium_mod.particle.ModParticles;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.AreaEffectCloud;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.item.alchemy.Potions;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;

import javax.annotation.Nullable;
import java.util.List;

public class AtiumSword extends SwordItem {
    public Level level;
    public Minecraft minecraft;

    public AtiumSword(Tier pTier, int pAttackDamageModifier, float pAttackSpeedModifier, Properties pProperties) {
        super(pTier, pAttackDamageModifier, pAttackSpeedModifier, pProperties);
        this.minecraft = Minecraft.getInstance();
    }

    @Override
    public boolean hurtEnemy(ItemStack pStack, LivingEntity pTarget, LivingEntity pAttacker) {
        if (pStack.getTag().contains("atium_mod.metal")) {
            int currentMetal = pStack.getTag().getInt("atium_mod.metal");
            switch (currentMetal) { //1=iron, 2=steel, 3=tin, 4=pewter, 5=brass, 6=zinc, 7=copper, 8=bronze, 9=gold
                case 2 -> steel(pTarget, pAttacker);
                case 3 -> tin(pTarget, pAttacker);
                case 4 -> pewter(pTarget, pAttacker);
                case 5 -> brass(pTarget, pAttacker);
                case 6 -> zinc(pTarget, pAttacker);
                case 9 -> gold(pTarget, pAttacker);
            }
        }
        return super.hurtEnemy(pStack, pTarget, pAttacker);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level pLevel, Player pPlayer, InteractionHand pUsedHand) {
        ItemStack itemstack = pPlayer.getItemInHand(pUsedHand);
        //if iron infused
        if (pPlayer.getMainHandItem().getTag().contains("atium_mod.metal") && pPlayer.getMainHandItem().getTag().getInt("atium_mod.metal") == 1) {
            pPlayer.startUsingItem(pUsedHand);
            return InteractionResultHolder.consume(itemstack);
        } else {
            return super.use(pLevel, pPlayer, pUsedHand);
        }
    }

    @Override
    public void onUseTick(Level pLevel, LivingEntity pPlayer, ItemStack pStack, int pRemainingUseDuration) {
        if (pStack.getTag().contains("atium_mod.metal") && pStack.getTag().getInt("atium_mod.metal") == 1) {
            iron(pLevel, pPlayer);
        }
        super.onUseTick(pLevel, pPlayer, pStack, pRemainingUseDuration);
    }

    @Override
    public UseAnim getUseAnimation(ItemStack pStack) {
        return UseAnim.BOW;
    }

    @Override
    public int getUseDuration(ItemStack pStack) {
        return 72000;
    }

    /**** INFUSION FUNCTIONALITIES ****/

    private void iron(Level pLevel, LivingEntity pPlayer) {
        //sets range entities are detected in (5x5x5)
        var range = 5.0D;
        AABB aabb = pPlayer.getBoundingBox().inflate(range, range, range);
        //stores all nearby living entities in list
        List<LivingEntity> entityList = pLevel.getNearbyEntities(LivingEntity.class, TargetingConditions.DEFAULT, pPlayer, aabb);
        //loops through every entity in the list above
        for (LivingEntity entity : entityList) {
            //grabs each individual entity each loop
            //push direction code
            double pX = pPlayer.getX() - entity.getX();
            double pZ;
            for (pZ = pPlayer.getZ() - entity.getZ(); pX * pX + pZ * pZ < 1.0E-4D; pZ = (Math.random() - Math.random()) * 0.01D) {
                pX = (Math.random() - Math.random()) * 0.01D;
            }
            //used to divide push amount to slow it down
            int modifier = 70;
            entity.push((pX / modifier), 0, (pZ / modifier));
            //particle
            var chance = Math.random();
            if (chance < 0.025) {
                if (entity.isOnGround()) {
                    this.minecraft.particleEngine.createParticle(ModParticles.FALLING_SMOKE_PARTICLES.get(), entity.getRandomX(1), entity.getY(), entity.getRandomZ(1), 0, 0, 0);
                }
            }
        }
    }

    private void steel(LivingEntity pTarget, LivingEntity pAttacker) {
        if (Math.random() < 0.1) { //10%
            double pX = pAttacker.getX() - pTarget.getX();
            double pZ;
            for (pZ = pAttacker.getZ() - pTarget.getZ(); pX * pX + pZ * pZ < 1.0E-4D; pZ = (Math.random() - Math.random()) * 0.01D) {
                pX = (Math.random() - Math.random()) * 0.01D;
            }
            pTarget.knockback(2.5F, pX, pZ);
            pTarget.playSound(SoundEvents.PLAYER_ATTACK_KNOCKBACK, 4.0f, 1.0F);
            for (int i = 0; i < 16; i++) {
                this.minecraft.particleEngine.createParticle(ModParticles.FALLING_SMOKE_PARTICLES.get(), pTarget.getRandomX(1), pTarget.getRandomY(), pTarget.getRandomZ(1), 0, 0, 0);
            }
        }
    }

    private void tin(LivingEntity pTarget, LivingEntity pAttacker) {
        if (Math.random() < 0.1) { //10%
            if (!pTarget.hasEffect(MobEffects.BLINDNESS)) {
                pTarget.addEffect(new MobEffectInstance(MobEffects.BLINDNESS, 80, 100, false, false), pAttacker);
            }
            if (!pTarget.hasEffect(ModMobEffects.DISORIENTED.get())) {
                pTarget.addEffect(new MobEffectInstance(ModMobEffects.DISORIENTED.get(), 100, 0, false, false), pAttacker);
            }
            pTarget.playSound(SoundEvents.ZOMBIE_INFECT, 6.0F, 1.0F);
            this.minecraft.particleEngine.createTrackingEmitter(pTarget, ModParticles.DISORIENTED_PARTICLES.get());
        }
    }

    private void pewter(LivingEntity pTarget, LivingEntity pAttacker) {
        if (Math.random() < 0.1) { //10%
            if (!pTarget.hasEffect(MobEffects.WEAKNESS)) {
                pTarget.addEffect(new MobEffectInstance(MobEffects.WEAKNESS, 100), pAttacker);
            }
            if (!pAttacker.hasEffect(MobEffects.DAMAGE_BOOST)) {
                pAttacker.addEffect(new MobEffectInstance(MobEffects.DAMAGE_BOOST, 100), pAttacker);
            }
            pTarget.playSound(SoundEvents.TRIDENT_RETURN, 4.0F, 1.0F);
            this.minecraft.particleEngine.createTrackingEmitter(pTarget, ParticleTypes.ENCHANTED_HIT);
        }
    }

    private void brass(LivingEntity pTarget, LivingEntity pAttacker) {
        if (Math.random() < 0.1) { //10%
            if (!pTarget.isOnFire()) {
                pTarget.setSecondsOnFire(5);
            } else {
                int fireTicks = pTarget.getRemainingFireTicks();
                pTarget.setRemainingFireTicks(fireTicks + 80);
            }
            pTarget.playSound(SoundEvents.FIRECHARGE_USE, 4.0F, 1.0F);
            this.minecraft.particleEngine.createTrackingEmitter(pTarget, ModParticles.MOD_FLAME_PARTICLES.get());
        }
    }

    private void zinc(LivingEntity pTarget, LivingEntity pAttacker) {
        if (Math.random() < 0.1) { //10%
            pTarget.setTicksFrozen(139);
            if (!pTarget.hasEffect(MobEffects.MOVEMENT_SLOWDOWN)) {
                pTarget.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 70, 3, false, false), pAttacker);
            }
            pTarget.playSound(SoundEvents.SKELETON_CONVERTED_TO_STRAY, 4.0F, 1.0F);
            this.minecraft.particleEngine.createTrackingEmitter(pTarget, ModParticles.SNOWFLAKE_PARTICLES.get());
        }
    }

    private void gold(LivingEntity pTarget, LivingEntity pAttacker) {
        if (Math.random() < 0.1) { //10%
            this.level = pAttacker.getLevel();
            AreaEffectCloud areaeffectcloud = new AreaEffectCloud(this.level, pAttacker.getX(), pAttacker.getY(), pAttacker.getZ());
            areaeffectcloud.setOwner((LivingEntity) pAttacker);
            areaeffectcloud.setRadius(3.0F);
            areaeffectcloud.setRadiusOnUse(-0.3F);
            areaeffectcloud.setWaitTime(4);
            areaeffectcloud.setRadiusPerTick(-areaeffectcloud.getRadius() / (float) areaeffectcloud.getDuration());
            areaeffectcloud.setPotion(Potions.HEALING);
            areaeffectcloud.addEffect(new MobEffectInstance(MobEffects.HEAL));

            this.level.addFreshEntity(areaeffectcloud);
        }
    }

    @Override
    public Rarity getRarity(ItemStack pStack) {
        if (pStack.getTag().contains("atium_mod.metal")) {
            return Rarity.COMMON;
        } else {
            return super.getRarity(pStack);
        }
    }

    //hover text
    @Override
    public void appendHoverText(ItemStack pStack, @Nullable Level pLevel, List<Component> pTooltipComponents, TooltipFlag pIsAdvanced) {
        if (pStack.getTag().contains("atium_mod.metal")) {
            int currentMetal = pStack.getTag().getInt("atium_mod.metal");
            if (Screen.hasControlDown()) {
                switch (currentMetal) { //1=iron, 2=steel, 3=tin, 4=pewter, 5=brass, 6=zinc, 7=copper, 8=bronze, 9=gold
                    case 1 -> pTooltipComponents.add(new TranslatableComponent("tooltip.atium_mod.atium_sword.tooltip.iron.ctrl"));
                    case 2 -> pTooltipComponents.add(new TranslatableComponent("tooltip.atium_mod.atium_sword.tooltip.steel.ctrl"));
                    case 3 -> pTooltipComponents.add(new TranslatableComponent("tooltip.atium_mod.atium_sword.tooltip.tin.ctrl"));
                    case 4 -> pTooltipComponents.add(new TranslatableComponent("tooltip.atium_mod.atium_sword.tooltip.pewter.ctrl"));
                    case 5 -> pTooltipComponents.add(new TranslatableComponent("tooltip.atium_mod.atium_sword.tooltip.brass.ctrl"));
                    case 6 -> pTooltipComponents.add(new TranslatableComponent("tooltip.atium_mod.atium_sword.tooltip.zinc.ctrl"));
                    case 9 -> pTooltipComponents.add(new TranslatableComponent("tooltip.atium_mod.atium_sword.tooltip.gold.ctrl"));
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
