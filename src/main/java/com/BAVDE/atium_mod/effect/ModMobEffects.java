package com.BAVDE.atium_mod.effect;

import com.BAVDE.atium_mod.AtiumMod;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModMobEffects {
    public static final DeferredRegister<MobEffect> MOB_EFFECTS
            = DeferredRegister.create(ForgeRegistries.MOB_EFFECTS, AtiumMod.MOD_ID);

    public static final RegistryObject<MobEffect> DISORIENTED = MOB_EFFECTS.register("disoriented",
            () -> new DisorientedEffect(MobEffectCategory.HARMFUL, 2039587));

    public static void register(IEventBus eventBus) {
        MOB_EFFECTS.register(eventBus);
    }
}
