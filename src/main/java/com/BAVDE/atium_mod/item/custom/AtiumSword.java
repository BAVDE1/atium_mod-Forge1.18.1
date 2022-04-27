package com.BAVDE.atium_mod.item.custom;

import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.Tier;

public class AtiumSword extends SwordItem {
    public AtiumSword(Tier pTier, int pAttackDamageModifier, float pAttackSpeedModifier, Properties pProperties) {
        super(pTier, pAttackDamageModifier, pAttackSpeedModifier, pProperties);
    }

    @Override
    public boolean hurtEnemy(ItemStack pStack, LivingEntity pTarget, LivingEntity pAttacker) {
        int chance = pTarget.level.random.nextInt(100);

        if (chance > 50) {
            pTarget.setTicksFrozen(139);
        }
        return super.hurtEnemy(pStack, pTarget, pAttacker);
    }
}
