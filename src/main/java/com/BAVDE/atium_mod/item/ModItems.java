package com.BAVDE.atium_mod.item;

import com.BAVDE.atium_mod.AtiumMod;
import com.BAVDE.atium_mod.item.custom.*;
import net.minecraft.world.entity.EquipmentSlot;
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

    public static final RegistryObject<Item> ATIUM_COMPASS = ITEMS.register("atium_compass",
            () -> new AtiumCompass(new Item.Properties().tab(ModCreativeModeTab.ATIUM_TAB)));

    //Tools
    public static final RegistryObject<Item> ATIUM_SWORD = ITEMS.register("atium_sword",
            () -> new AtiumSword(ModTiers.ATIUM, 3, -2.4f, new Item.Properties().tab(ModCreativeModeTab.ATIUM_TAB).durability(1100)));
    public static final RegistryObject<Item> ATIUM_PICKAXE = ITEMS.register("atium_pickaxe",
            () -> new AtiumPickaxe(ModTiers.ATIUM, 1, -2.8f, new Item.Properties().tab(ModCreativeModeTab.ATIUM_TAB).durability(1100)));
    public static final RegistryObject<Item> ATIUM_AXE = ITEMS.register("atium_axe",
            () -> new AtiumAxe(ModTiers.ATIUM, 5.5f, -3f, new Item.Properties().tab(ModCreativeModeTab.ATIUM_TAB).durability(1100)));
    public static final RegistryObject<Item> ATIUM_SHOVEL = ITEMS.register("atium_shovel",
            () -> new AtiumShovel(ModTiers.ATIUM, 1.5F, -3f, new Item.Properties().tab(ModCreativeModeTab.ATIUM_TAB).durability(1100)));
    public static final RegistryObject<Item> ATIUM_HOE = ITEMS.register("atium_hoe",
            () -> new AtiumHoe(ModTiers.ATIUM, -1.5F, -0.5f, new Item.Properties().tab(ModCreativeModeTab.ATIUM_TAB).durability(1100)));
    //Armour
    public static final RegistryObject<Item> ATIUM_HELMET = ITEMS.register("atium_helmet",
            () -> new AtiumHelmet(ModArmourMaterials.ATIUM, EquipmentSlot.HEAD, new Item.Properties().tab(ModCreativeModeTab.ATIUM_TAB).durability(1100)));
    public static final RegistryObject<Item> ATIUM_CHESTPLATE = ITEMS.register("atium_chestplate",
            () -> new AtiumChestplate(ModArmourMaterials.ATIUM, EquipmentSlot.CHEST, new Item.Properties().tab(ModCreativeModeTab.ATIUM_TAB).durability(1100)));
    public static final RegistryObject<Item> ATIUM_LEGGINGS = ITEMS.register("atium_leggings",
            () -> new AtiumLeggings(ModArmourMaterials.ATIUM, EquipmentSlot.LEGS, new Item.Properties().tab(ModCreativeModeTab.ATIUM_TAB).durability(1100)));
    public static final RegistryObject<Item> ATIUM_BOOTS = ITEMS.register("atium_boots",
            () -> new AtiumBoots(ModArmourMaterials.ATIUM, EquipmentSlot.FEET, new Item.Properties().tab(ModCreativeModeTab.ATIUM_TAB).durability(1100)));

    //metals
    public static final RegistryObject<Item> REFINED_IRON = ITEMS.register("refined_iron",
            () -> new Item(new Item.Properties().tab(ModCreativeModeTab.ATIUM_TAB)));
    public static final RegistryObject<Item> STEEL = ITEMS.register("steel",
            () -> new Item(new Item.Properties().tab(ModCreativeModeTab.ATIUM_TAB)));
    public static final RegistryObject<Item> TIN = ITEMS.register("tin",
            () -> new Item(new Item.Properties().tab(ModCreativeModeTab.ATIUM_TAB)));
    public static final RegistryObject<Item> PEWTER = ITEMS.register("pewter",
            () -> new Item(new Item.Properties().tab(ModCreativeModeTab.ATIUM_TAB)));
    public static final RegistryObject<Item> BRASS = ITEMS.register("brass",
            () -> new Item(new Item.Properties().tab(ModCreativeModeTab.ATIUM_TAB)));
    public static final RegistryObject<Item> ZINC = ITEMS.register("zinc",
            () -> new Item(new Item.Properties().tab(ModCreativeModeTab.ATIUM_TAB)));
    public static final RegistryObject<Item> BRONZE = ITEMS.register("bronze",
            () -> new Item(new Item.Properties().tab(ModCreativeModeTab.ATIUM_TAB)));
    public static final RegistryObject<Item> PURIFIED_GOLD = ITEMS.register("purified_gold",
            () -> new Item(new Item.Properties().tab(ModCreativeModeTab.ATIUM_TAB)));
    public static final RegistryObject<Item> ALUMINIUM = ITEMS.register("aluminium",
            () -> new Item(new Item.Properties().tab(ModCreativeModeTab.ATIUM_TAB)));

    //items
    public static final RegistryObject<Item> DIAMOND_COMPOUND = ITEMS.register("diamond_compound",
            () -> new Item(new Item.Properties().tab(ModCreativeModeTab.ATIUM_TAB)));
    public static final RegistryObject<Item> CRYSTALLIZED_SHARD = ITEMS.register("crystallized_shard",
            () -> new Item(new Item.Properties().tab(ModCreativeModeTab.ATIUM_TAB)));
    public static final RegistryObject<Item> COIN = ITEMS.register("coin",
            () -> new Item(new Item.Properties().tab(ModCreativeModeTab.ATIUM_TAB)));

    //scrolls
    public static final RegistryObject<Item> EMPTY_SCROLL = ITEMS.register("empty_scroll",
            () -> new Item(new Item.Properties().tab(ModCreativeModeTab.ATIUM_TAB).stacksTo(16)));
    public static final RegistryObject<Item> STEEL_SCROLL = ITEMS.register("steel_scroll",
            () -> new Item(new Item.Properties().tab(ModCreativeModeTab.ATIUM_TAB).stacksTo(16)));
    public static final RegistryObject<Item> TIN_SCROLL = ITEMS.register("tin_scroll",
            () -> new Item(new Item.Properties().tab(ModCreativeModeTab.ATIUM_TAB).stacksTo(16)));
    public static final RegistryObject<Item> PEWTER_SCROLL = ITEMS.register("pewter_scroll",
            () -> new Item(new Item.Properties().tab(ModCreativeModeTab.ATIUM_TAB).stacksTo(16)));
    public static final RegistryObject<Item> BRASS_SCROLL = ITEMS.register("brass_scroll",
            () -> new Item(new Item.Properties().tab(ModCreativeModeTab.ATIUM_TAB).stacksTo(16)));
    public static final RegistryObject<Item> ZINC_SCROLL = ITEMS.register("zinc_scroll",
            () -> new Item(new Item.Properties().tab(ModCreativeModeTab.ATIUM_TAB).stacksTo(16)));
    public static final RegistryObject<Item> TIN_SCROLL_FRAGMENT = ITEMS.register("tin_scroll_fragment",
            () -> new Item(new Item.Properties().tab(ModCreativeModeTab.ATIUM_TAB)));
    public static final RegistryObject<Item> ZINC_SCROLL_FRAGMENT = ITEMS.register("zinc_scroll_fragment",
            () -> new Item(new Item.Properties().tab(ModCreativeModeTab.ATIUM_TAB)));

    //Pouches
    public static final RegistryObject<Item> COIN_POUCH = ITEMS.register("coin_pouch",
            () -> new CoinPouch(new Item.Properties().tab(ModCreativeModeTab.ATIUM_TAB).stacksTo(1).durability(64)));
    //add pouch upgrade

    //Knives
    public static final RegistryObject<Item> CRYSTALLIZED_KNIFE = ITEMS.register("crystallized_knife",
            () -> new KnifeItem(ModTiers.CRYSTALLIZED_KNIFE_TIER, 1, -2.2f, new Item.Properties().tab(ModCreativeModeTab.ATIUM_TAB).durability(250)));

    //Hammers
    public static final RegistryObject<Item> STONE_HAMMER = ITEMS.register("stone_hammer",
            () -> new HammerItem(new Item.Properties().tab(ModCreativeModeTab.ATIUM_TAB).durability(16)));
    public static final RegistryObject<Item> IRON_HAMMER = ITEMS.register("iron_hammer",
            () -> new HammerItem(new Item.Properties().tab(ModCreativeModeTab.ATIUM_TAB).durability(100)));
    public static final RegistryObject<Item> DIAMOND_HAMMER = ITEMS.register("diamond_hammer",
            () -> new HammerItem(new Item.Properties().tab(ModCreativeModeTab.ATIUM_TAB).durability(800)));




    //Item stuff
    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}
