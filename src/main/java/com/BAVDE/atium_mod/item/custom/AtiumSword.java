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
        //Level pLevel = pTarget.level;

        /*if (pLevel.isClientSide) {
            Random random = pLevel.getRandom();
            boolean flag = pTarget.xOld != pTarget.getX() || pTarget.zOld != pTarget.getZ();
            if (flag && random.nextBoolean()) {
                pLevel.addParticle(ParticleTypes.SNOWFLAKE, pTarget.getX(), (double) (pTarget.getY() + 1), pTarget.getZ(), (double) (Mth.randomBetween(random, -1.0F, 1.0F) * 0.083333336F), (double) 0.05F, (double) (Mth.randomBetween(random, -1.0F, 1.0F) * 0.083333336F));
            }
        }
        pTarget.setIsInPowderSnow(true);
        if (!pLevel.isClientSide) {
            pTarget.setSharedFlagOnFire(false);
        }*/
        //pLevel.addParticle(ParticleTypes.SNOWFLAKE, pEntity.getX(), (double)(pPos.getY() + 1), pEntity.getZ(), (double)(Mth.randomBetween(random, -1.0F, 1.0F) * 0.083333336F), (double)0.05F, (double)(Mth.randomBetween(random, -1.0F, 1.0F) * 0.083333336F));

        //pTarget.getTicksFrozen();

        pTarget.setTicksFrozen(139);
        //pTarget.hurt(DamageSource.FREEZE, 1F);
        return super.hurtEnemy(pStack, pTarget, pAttacker);
    }
}
