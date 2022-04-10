package com.BAVDE.atium_mod.item;

import com.BAVDE.atium_mod.AtiumMod;
import com.BAVDE.atium_mod.item.custom.*;
import net.minecraft.world.item.*;
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
    //gear
    public static final RegistryObject<Item> ATIUM_SWORD = ITEMS.register("atium_sword",
            () -> new SwordItem(ModTiers.ATIUM, 3, -2.4f, new Item.Properties().tab(ModCreativeModeTab.ATIUM_TAB).durability(1100)));
    public static final RegistryObject<Item> ATIUM_PICKAXE = ITEMS.register("atium_pickaxe",
            () -> new PickaxeItem(ModTiers.ATIUM, 1, -2.8f, new Item.Properties().tab(ModCreativeModeTab.ATIUM_TAB).durability(1100)));
    public static final RegistryObject<Item> ATIUM_AXE = ITEMS.register("atium_axe",
            () -> new AxeItem(ModTiers.ATIUM, 5F, -3f, new Item.Properties().tab(ModCreativeModeTab.ATIUM_TAB).durability(1100)));
    public static final RegistryObject<Item> ATIUM_SHOVEL = ITEMS.register("atium_shovel",
            () -> new ShovelItem(ModTiers.ATIUM, 1.5F, -3f, new Item.Properties().tab(ModCreativeModeTab.ATIUM_TAB).durability(1100)));
    public static final RegistryObject<Item> ATIUM_HOE = ITEMS.register("atium_hoe",
            () -> new HoeItem(ModTiers.ATIUM, -2, 0, new Item.Properties().tab(ModCreativeModeTab.ATIUM_TAB).durability(1100)));

    //crystallized atium
    public static final RegistryObject<Item> CRYSTALLIZED_SHARD = ITEMS.register("crystallized_shard",
            () -> new Item(new Item.Properties().tab(ModCreativeModeTab.ATIUM_TAB)));

    //Knives
    public static final RegistryObject<Item> WOOD_KNIFE = ITEMS.register("wood_knife",
            () -> new KnifeItem(Tiers.WOOD, 0, -2.2f, new Item.Properties().tab(ModCreativeModeTab.ATIUM_TAB).durability(64)));
    public static final RegistryObject<Item> CRYSTALLIZED_KNIFE = ITEMS.register("crystallized_knife",
            () -> new KnifeItem(ModTiers.CRYSTALLIZED_KNIFE_TIER, 1, -2.2f, new Item.Properties().tab(ModCreativeModeTab.ATIUM_TAB).durability(250)));

    //Hammers
    public static final RegistryObject<Item> STONE_HAMMER = ITEMS.register("stone_hammer",
            () -> new HammerItem(new Item.Properties().tab(ModCreativeModeTab.ATIUM_TAB).durability(16)));
    public static final RegistryObject<Item> IRON_HAMMER = ITEMS.register("iron_hammer",
            () -> new HammerItem(new Item.Properties().tab(ModCreativeModeTab.ATIUM_TAB).durability(128)));
    public static final RegistryObject<Item> DIAMOND_HAMMER = ITEMS.register("diamond_hammer",
            () -> new HammerItem(new Item.Properties().tab(ModCreativeModeTab.ATIUM_TAB).durability(1024)));




    //Item stuff
    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}
