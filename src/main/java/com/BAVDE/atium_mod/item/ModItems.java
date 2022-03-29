package com.BAVDE.atium_mod.item;

import com.BAVDE.atium_mod.AtiumMod;
import com.BAVDE.atium_mod.item.custom.*;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.Tiers;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModItems {
    public static final DeferredRegister<Item> ITEMS=
            DeferredRegister.create(ForgeRegistries.ITEMS, AtiumMod.MOD_ID);

    //Atium
    public static final RegistryObject<Item> PURE_ATIUM = ITEMS.register("pure_atium",
            () -> new Item(new Item.Properties().tab(ModCreativeModeTab.ATIUM_TAB)));

    public static final RegistryObject<Item> ATIUM_GEODE = ITEMS.register("atium_geode",
            () -> new Item(new Item.Properties().tab(ModCreativeModeTab.ATIUM_TAB)));

    public static final RegistryObject<Item> ATIUM_BEAD = ITEMS.register("atium_bead",
            () -> new Item(new Item.Properties().tab(ModCreativeModeTab.ATIUM_TAB)));

    public static final RegistryObject<Item> ATIUM_SWORD = ITEMS.register("atium_sword",
            () -> new SwordItem(ModTiers.ATIUM, 3, -2.4f,
                    new Item.Properties().tab(ModCreativeModeTab.ATIUM_TAB).durability(1000)));



    //Knives
    public static final RegistryObject<Item> WOOD_KNIFE = ITEMS.register("wood_knife",
            () -> new WoodKnife(Tiers.WOOD, 0, -2.2f,
                    new Item.Properties().tab(ModCreativeModeTab.ATIUM_TAB).durability(64)));

    public static final RegistryObject<Item> CRYSTALLIZED_KNIFE = ITEMS.register("crystallized_knife",
            () -> new CrystallizedKnife(ModTiers.CRYSTALLIZED_KNIFE_TIER, 1, -2.2f,
                    new Item.Properties().tab(ModCreativeModeTab.ATIUM_TAB).durability(250)));



    //Hammers
    public static final RegistryObject<Item> STONE_HAMMER = ITEMS.register("stone_hammer",
            () -> new StoneHammer(new Item.Properties().tab(ModCreativeModeTab.ATIUM_TAB).durability(16)));

    public static final RegistryObject<Item> IRON_HAMMER = ITEMS.register("iron_hammer",
            () -> new IronHammer(new Item.Properties().tab(ModCreativeModeTab.ATIUM_TAB).durability(128)));

    public static final RegistryObject<Item> DIAMOND_HAMMER = ITEMS.register("diamond_hammer",
            () -> new DiamondHammer(new Item.Properties().tab(ModCreativeModeTab.ATIUM_TAB).durability(1024)));


    //crystallized atium
    public static final RegistryObject<Item> CRYSTALLIZED_SHARD = ITEMS.register("crystallized_shard",
            () -> new Item(new Item.Properties().tab(ModCreativeModeTab.ATIUM_TAB)));


    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}
