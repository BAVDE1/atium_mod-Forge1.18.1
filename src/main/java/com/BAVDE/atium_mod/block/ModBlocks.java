package com.BAVDE.atium_mod.block;

import com.BAVDE.atium_mod.AtiumMod;
import com.BAVDE.atium_mod.block.custom.AtiumLode;
import com.BAVDE.atium_mod.block.custom.CrystallizedAtiumBlock;
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
            () -> new Block(BlockBehaviour.Properties.of(Material.STONE)
                    .strength(5f).requiresCorrectToolForDrops()), ModCreativeModeTab.ATIUM_TAB);

    public static final RegistryObject<Block> ATIUM_ORE_OVERGROWN = registerBlock("atium_ore_overgrown",
            () -> new Block(BlockBehaviour.Properties.of(Material.STONE)
                    .strength(5f).requiresCorrectToolForDrops()), ModCreativeModeTab.ATIUM_TAB);

    public static final RegistryObject<Block> ATIUM_LODE = registerBlock("atium_lode",
            () -> new AtiumLode(BlockBehaviour.Properties.of(Material.AMETHYST)
                    .strength(12f).requiresCorrectToolForDrops()), ModCreativeModeTab.ATIUM_TAB);


    //Crystallized Atium
    public static final RegistryObject<Block> CRYSTALLIZED_ATIUM_BLOCK = registerBlock("crystallized_atium_block",
            () -> new CrystallizedAtiumBlock(BlockBehaviour.Properties.of(Material.AMETHYST)
                    .strength(4f).requiresCorrectToolForDrops().sound(SoundType.AMETHYST)), ModCreativeModeTab.ATIUM_TAB);





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
