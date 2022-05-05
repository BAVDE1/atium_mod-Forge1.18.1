package com.BAVDE.atium_mod.particle;

import com.BAVDE.atium_mod.AtiumMod;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModParticles {
    public static final DeferredRegister<ParticleType<?>> PARTICLE_TYPES =
            DeferredRegister.create(ForgeRegistries.PARTICLE_TYPES, AtiumMod.MOD_ID);

    public static final RegistryObject<SimpleParticleType> SNOWFLAKE_PARTICLES =
            PARTICLE_TYPES.register("snowflake_particles", () -> new SimpleParticleType(true));

    public static final RegistryObject<SimpleParticleType> BLINDNESS_PARTICLES =
            PARTICLE_TYPES.register("blindness_particles", () -> new SimpleParticleType(true));

    public static final RegistryObject<SimpleParticleType> DISORIENTED_PARTICLES =
            PARTICLE_TYPES.register("disoriented_particles", () -> new SimpleParticleType(true));

    public static final RegistryObject<SimpleParticleType> FALLING_SMOKE_PARTICLES =
            PARTICLE_TYPES.register("falling_smoke_particles", () -> new SimpleParticleType(true));

    //register particles in ModEventBusEvents

    public static void register(IEventBus eventBus) {
        PARTICLE_TYPES.register(eventBus);
    }
}
