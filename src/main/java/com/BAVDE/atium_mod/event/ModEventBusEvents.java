package com.BAVDE.atium_mod.event;

import com.BAVDE.atium_mod.AtiumMod;
import com.BAVDE.atium_mod.event.loot.ZombieDropLeather;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.common.loot.GlobalLootModifierSerializer;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.jetbrains.annotations.NotNull;

@Mod.EventBusSubscriber(modid = AtiumMod.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModEventBusEvents {

    @SubscribeEvent
    public static void registerModifierSerializers(@NotNull final RegistryEvent.Register<GlobalLootModifierSerializer<?>> event) {
        event.getRegistry().registerAll(
                new ZombieDropLeather.Serializer().setRegistryName
                        (new ResourceLocation(AtiumMod.MOD_ID,"leather_from_zombie"))
        );
    }
}
