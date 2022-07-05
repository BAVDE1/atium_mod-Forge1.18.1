package com.BAVDE.atium_mod.effect;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;

public class DisorientedEffect extends MobEffect {
    protected DisorientedEffect(MobEffectCategory category, int colour) {
        super(category, colour);
    }

    //randomly moves entity in random direction (the higher amplifier the faster)
    @Override
    public void applyEffectTick(LivingEntity pLivingEntity, int pAmplifier) {
        var chance = Math.random();
        //push sizes
        var large = Math.random() / 3;
        var small = Math.random() / 5;
        //1/4 chance to be pushed in each direction
        //push X
        if (chance < 0.25) {
            if (chance < 0.125) {
                pLivingEntity.push(large, 0, 0);
            } else {
                pLivingEntity.push(-large, 0, 0);
            }
            //push Z
        } else if (chance < 0.5) {
            if (chance < 0.275) {
                pLivingEntity.push(0, 0, large);
            } else {
                pLivingEntity.push(0, 0, -large);
            }
            //push X > Z
        } else if (chance < 0.75) {
            if (chance < 0.625) {
                pLivingEntity.push(-large, 0, small);
            } else {
                pLivingEntity.push(large, 0, -small);
            }
            //push X < Z
        } else {
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
