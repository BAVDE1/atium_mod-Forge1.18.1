package com.BAVDE.atium_mod.entity;

import com.BAVDE.atium_mod.AtiumMod;
import com.BAVDE.atium_mod.entity.projectile.IronCoinProjectile;
import com.BAVDE.atium_mod.entity.projectile.SteelCoinProjectile;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModEntityTypes {
    public static final DeferredRegister<EntityType<?>> ENTITY_TYPES = DeferredRegister.create(ForgeRegistries.ENTITIES, AtiumMod.MOD_ID);

    //register here
    public static final RegistryObject<EntityType<IronCoinProjectile>> IRON_COIN_PROJECTILE =
            ENTITY_TYPES.register("iron_coin_projectile", () -> EntityType.Builder.<IronCoinProjectile>of(IronCoinProjectile::new, MobCategory.MISC)
                    .sized(0.15f, 0.15f)
                    .build(new ResourceLocation(AtiumMod.MOD_ID, "iron_coin_projectile").toString()));

    public static final RegistryObject<EntityType<SteelCoinProjectile>> STEEL_COIN_PROJECTILE =
            ENTITY_TYPES.register("steel_coin_projectile", () -> EntityType.Builder.<SteelCoinProjectile>of(SteelCoinProjectile::new, MobCategory.MISC)
                    .sized(0.15f, 0.15f)
                    .build(new ResourceLocation(AtiumMod.MOD_ID, "steel_coin_projectile").toString()));

    public static void register(IEventBus eventBus) {
        ENTITY_TYPES.register(eventBus);
    }
}
