package com.BAVDE.atium_mod.item;

import com.BAVDE.atium_mod.AtiumMod;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModItems {
    public static final DeferredRegister<Item> ITEMS=
            DeferredRegister.create(ForgeRegistries.ITEMS, AtiumMod.MOD_ID);

    //Atium
    public static final RegistryObject<Item> PUREATIUM = ITEMS.register("pure_atium",
            () -> new Item(new Item.Properties().tab(ModCreativeModeTab.ATIUM_TAB)));

    public static final RegistryObject<Item> RAWATIUM = ITEMS.register("raw_atium",
            () -> new Item(new Item.Properties().tab(ModCreativeModeTab.ATIUM_TAB)));

    public static final RegistryObject<Item> ATIUMBEAD = ITEMS.register("atium_bead",
            () -> new Item(new Item.Properties().tab(ModCreativeModeTab.ATIUM_TAB)));




    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}
