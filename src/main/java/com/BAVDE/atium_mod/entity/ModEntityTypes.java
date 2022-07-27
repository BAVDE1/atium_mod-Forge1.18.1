package com.BAVDE.atium_mod.entity;

import com.BAVDE.atium_mod.AtiumMod;
import net.minecraft.world.entity.EntityType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ModEntityTypes {
    public static final DeferredRegister<EntityType<?>> ENTITY_TYPES = DeferredRegister.create(ForgeRegistries.ENTITIES, AtiumMod.MOD_ID);

    //register here

    public static void register(IEventBus eventBus) {
        ENTITY_TYPES.register(eventBus);
    }
}
