package com.BAVDE.atium_mod.painting;

import com.BAVDE.atium_mod.AtiumMod;
import net.minecraft.world.entity.decoration.Motive;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModPaintings {
    public static final DeferredRegister<Motive> PAINTING_MOTIVE =
            DeferredRegister.create(ForgeRegistries.PAINTING_TYPES, AtiumMod.MOD_ID);

    public static final RegistryObject<Motive> MOONLIGHT =
            PAINTING_MOTIVE.register("moonlight", () -> new Motive(32,32));

    public static void register(IEventBus eventBus) {
        PAINTING_MOTIVE.register(eventBus);
    }
}
