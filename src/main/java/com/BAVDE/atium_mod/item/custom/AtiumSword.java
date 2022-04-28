package com.BAVDE.atium_mod.item.custom;

import net.minecraft.client.Minecraft;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.Tier;

public class AtiumSword extends SwordItem {
    public AtiumSword(Tier pTier, int pAttackDamageModifier, float pAttackSpeedModifier, Properties pProperties) {
        super(pTier, pAttackDamageModifier, pAttackSpeedModifier, pProperties);
    }

    @Override
    public boolean hurtEnemy(ItemStack pStack, LivingEntity pTarget, LivingEntity pAttacker) {
        //int chance = pTarget.level.random.nextInt(100);

        //if (chance > 50) {
            pTarget.setTicksFrozen(139);
            if (!pTarget.hasEffect(MobEffects.MOVEMENT_SLOWDOWN)) {
                pTarget.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 80, 3), pAttacker);
            }
            pTarget.playSound(SoundEvents.SKELETON_CONVERTED_TO_STRAY, 4.0F, 1.0F);
        //}
        return super.hurtEnemy(pStack, pTarget, pAttacker);
    }
}
