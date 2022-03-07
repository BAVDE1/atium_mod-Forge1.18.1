package com.BAVDE.atium_mod.block;

import com.BAVDE.atium_mod.AtiumMod;
import com.BAVDE.atium_mod.block.custom.*;
//import com.BAVDE.atium_mod.block.custom.AtiumOreOvergrownRecharging;
import com.BAVDE.atium_mod.item.ModCreativeModeTab;
import com.BAVDE.atium_mod.item.ModItems;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.Material;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Supplier;

public class ModBlocks {
    public static final DeferredRegister<Block> BLOCKS =
            DeferredRegister.create(ForgeRegistries.BLOCKS, AtiumMod.MOD_ID);

    //Atium
    public static final RegistryObject<Block> PURE_ATIUM_BLOCK = registerBlock("pure_atium_block",
            () -> new Block(BlockBehaviour.Properties.of(Material.METAL)
                    .strength(7f).requiresCorrectToolForDrops()), ModCreativeModeTab.ATIUM_TAB);

    public static final RegistryObject<Block> ATIUM_ORE = registerBlock("atium_ore",
            () -> new AtiumOre(BlockBehaviour.Properties.of(Material.STONE)
                    .strength(5f).requiresCorrectToolForDrops()), ModCreativeModeTab.ATIUM_TAB);

    public static final RegistryObject<Block> EMPTY_ATIUM_ORE = registerBlock("empty_atium_ore",
            () -> new EmptyAtiumOre(BlockBehaviour.Properties.of(Material.STONE)
                    .strength(18f).requiresCorrectToolForDrops()), ModCreativeModeTab.ATIUM_TAB);

    public static final RegistryObject<Block> ATIUM_ORE_OVERGROWN = registerBlock("atium_ore_overgrown",
            () -> new AtiumOreOvergrown(BlockBehaviour.Properties.of(Material.STONE)
                    .strength(5f).requiresCorrectToolForDrops()), ModCreativeModeTab.ATIUM_TAB);

    public static final RegistryObject<Block> EMPTY_ATIUM_ORE_OVERGROWN = registerBlock("empty_atium_ore_overgrown",
            () -> new EmptyAtiumOreOvergrown(BlockBehaviour.Properties.of(Material.STONE)
                    .strength(18f).requiresCorrectToolForDrops()), ModCreativeModeTab.ATIUM_TAB);



    //Crystallized Atium
    public static final RegistryObject<Block> CRYSTALLIZED_ATIUM_BLOCK = registerBlock("crystallized_atium_block",
            () -> new CrystallizedAtiumBlock(BlockBehaviour.Properties.of(Material.AMETHYST)
                    .strength(1f).requiresCorrectToolForDrops().sound(SoundType.AMETHYST)), ModCreativeModeTab.ATIUM_TAB);

    public static final RegistryObject<Block> BUDDING_CRYSTALLIZED_ATIUM_BLOCK = registerBlock("budding_crystallized_atium_block",
            () -> new BuddingCrystallizedAtiumBlock(BlockBehaviour.Properties.of(Material.AMETHYST)
                    .randomTicks().strength(1.5f).requiresCorrectToolForDrops().sound(SoundType.AMETHYST)), ModCreativeModeTab.ATIUM_TAB);

    public static final RegistryObject<Block> CRYSTALLIZED_ATIUM_CLUSTER = registerBlock("crystallized_atium_cluster",
            () -> new CrystallizedAtiumCluster(7, 3, BlockBehaviour.Properties.of(Material.AMETHYST)
                    .noOcclusion().randomTicks().strength(1.5f).sound(SoundType.AMETHYST_CLUSTER).lightLevel((p_152632_) -> {return 5;})), ModCreativeModeTab.ATIUM_TAB);

    public static final RegistryObject<Block> LARGE_CRYSTALLIZED_ATIUM_BUD = registerBlock("large_crystallized_atium_bud",
            () -> new CrystallizedAtiumCluster(5, 3, BlockBehaviour.Properties.of(Material.AMETHYST)
                    .noOcclusion().randomTicks().strength(1.5f).sound(SoundType.AMETHYST_CLUSTER).lightLevel((p_152629_) -> {return  4;})), ModCreativeModeTab.ATIUM_TAB);

    public static final RegistryObject<Block> MEDIUM_CRYSTALLIZED_ATIUM_BUD = registerBlock("medium_crystallized_atium_bud",
            () -> new CrystallizedAtiumCluster(4, 3, BlockBehaviour.Properties.of(Material.AMETHYST)
                    .noOcclusion().randomTicks().strength(1.5f).sound(SoundType.AMETHYST_CLUSTER).lightLevel((p_152629_) -> {return  4;})), ModCreativeModeTab.ATIUM_TAB);

    public static final RegistryObject<Block> SMALL_CRYSTALLIZED_ATIUM_BUD = registerBlock("small_crystallized_atium_bud",
            () -> new CrystallizedAtiumCluster(3, 4, BlockBehaviour.Properties.of(Material.AMETHYST)
                    .noOcclusion().randomTicks().strength(1.5f).sound(SoundType.AMETHYST_CLUSTER).lightLevel((p_152629_) -> {return  4;})), ModCreativeModeTab.ATIUM_TAB);




    //block stuff idk
    private static <T extends Block>RegistryObject<T> registerBlock(String name, Supplier<T> block, CreativeModeTab tab) {
        RegistryObject<T> toReturn = BLOCKS.register(name, block);
        registerBlockItem(name, toReturn, tab);
        return toReturn;
    }

    private static <T extends Block> RegistryObject<Item> registerBlockItem(String name, Supplier<T> block, CreativeModeTab tab) {
        return ModItems.ITEMS.register(name, () -> new BlockItem(block.get(),
                new Item.Properties().tab(tab)));
    }

    public static void register(IEventBus eventBus) {
        BLOCKS.register(eventBus);
    }
}
