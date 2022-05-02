package com.BAVDE.atium_mod.effect;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;

public class DisorientedEffect extends MobEffect {
    protected DisorientedEffect(MobEffectCategory category, int colour) {
        super(category, colour);
    }

    @Override
    public void applyEffectTick(LivingEntity pLivingEntity, int pAmplifier) {
        var chance = Math.random();
        var large = Math.random() / 3;
        var small = Math.random() / 5;
        if (chance < 0.25) { //push X
            if (chance < 0.125) {
                pLivingEntity.push(large, 0, 0);
            } else {
                pLivingEntity.push(-large, 0, 0);
            }
        } else if (chance < 0.5) { //push Z
            if (chance < 0.275) {
                pLivingEntity.push(0, 0, large);
            } else {
                pLivingEntity.push(0, 0, -large);
            }
        } else if (chance < 0.75) { //push X > Z
            if (chance < 0.625) {
                pLivingEntity.push(-large, 0, small);
            } else {
                pLivingEntity.push(large, 0, -small);
            }
        } else { //push X < Z
            if (chance < 0.875) {
                pLivingEntity.push(-small, 0, large);
            } else {
                pLivingEntity.push(small, 0, -large);
            }
        }
        super.applyEffectTick(pLivingEntity, pAmplifier);
    }

    @Override
    public boolean isDurationEffectTick(int pDuration, int pAmplifier) {
        int i = 4 >> pAmplifier;
        if (i > 0) {
            return pDuration % i == 0;
        } else {
            return true;
        }
    }
}
