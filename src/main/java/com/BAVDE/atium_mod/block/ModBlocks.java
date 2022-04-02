package com.BAVDE.atium_mod.block;

import com.BAVDE.atium_mod.AtiumMod;
import com.BAVDE.atium_mod.block.custom.*;
//import com.BAVDE.atium_mod.block.custom.AtiumOreOvergrownRecharging;
import com.BAVDE.atium_mod.item.ModCreativeModeTab;
import com.BAVDE.atium_mod.item.ModItems;
import com.BAVDE.atium_mod.world.feature.tree.CrystallineTreeGrower;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
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
            () -> new Block(BlockBehaviour.Properties.of(Material.METAL).strength(7f).requiresCorrectToolForDrops()), ModCreativeModeTab.ATIUM_TAB);
    public static final RegistryObject<Block> ATIUM_ORE = registerBlock("atium_ore",
            () -> new AtiumOre(BlockBehaviour.Properties.of(Material.STONE).strength(5f).requiresCorrectToolForDrops()), ModCreativeModeTab.ATIUM_TAB);
    public static final RegistryObject<Block> EMPTY_ATIUM_ORE = registerBlock("empty_atium_ore",
            () -> new EmptyAtiumOre(BlockBehaviour.Properties.of(Material.STONE).strength(18f).requiresCorrectToolForDrops()), ModCreativeModeTab.ATIUM_TAB);
    public static final RegistryObject<Block> ATIUM_ORE_OVERGROWN = registerBlock("atium_ore_overgrown",
            () -> new AtiumOreOvergrown(BlockBehaviour.Properties.of(Material.STONE).strength(5f).requiresCorrectToolForDrops()), ModCreativeModeTab.ATIUM_TAB);
    public static final RegistryObject<Block> EMPTY_ATIUM_ORE_OVERGROWN = registerBlock("empty_atium_ore_overgrown",
            () -> new EmptyAtiumOreOvergrown(BlockBehaviour.Properties.of(Material.STONE).strength(18f).requiresCorrectToolForDrops()), ModCreativeModeTab.ATIUM_TAB);

    //Crystallized Atium
    public static final RegistryObject<Block> CRYSTALLIZED_ATIUM_BLOCK = registerBlock("crystallized_atium_block",
            () -> new CrystallizedAtiumBlock(BlockBehaviour.Properties.of(Material.AMETHYST).strength(1f).requiresCorrectToolForDrops().sound(SoundType.AMETHYST)), ModCreativeModeTab.ATIUM_TAB);
    public static final RegistryObject<Block> BUDDING_CRYSTALLIZED_ATIUM_BLOCK = registerBlock("budding_crystallized_atium_block",
            () -> new BuddingCrystallizedAtiumBlock(BlockBehaviour.Properties.of(Material.AMETHYST).randomTicks().strength(1.5f).requiresCorrectToolForDrops().sound(SoundType.AMETHYST)), ModCreativeModeTab.ATIUM_TAB);
    public static final RegistryObject<Block> CRYSTALLIZED_ATIUM_CLUSTER = registerBlock("crystallized_atium_cluster",
            () -> new CrystallizedAtiumCluster(7, 3, BlockBehaviour.Properties.of(Material.AMETHYST).noOcclusion().randomTicks().strength(1.5f).requiresCorrectToolForDrops().sound(SoundType.AMETHYST_CLUSTER).lightLevel((p_152632_) -> {return 5;})), ModCreativeModeTab.ATIUM_TAB);
    public static final RegistryObject<Block> LARGE_CRYSTALLIZED_ATIUM_BUD = registerBlock("large_crystallized_atium_bud",
            () -> new CrystallizedAtiumCluster(5, 3, BlockBehaviour.Properties.of(Material.AMETHYST).noOcclusion().randomTicks().strength(1.5f).requiresCorrectToolForDrops().sound(SoundType.AMETHYST_CLUSTER).lightLevel((p_152629_) -> {return  4;})), ModCreativeModeTab.ATIUM_TAB);
    public static final RegistryObject<Block> MEDIUM_CRYSTALLIZED_ATIUM_BUD = registerBlock("medium_crystallized_atium_bud",
            () -> new CrystallizedAtiumCluster(4, 3, BlockBehaviour.Properties.of(Material.AMETHYST).noOcclusion().randomTicks().strength(1.5f).requiresCorrectToolForDrops().sound(SoundType.AMETHYST_CLUSTER).lightLevel((p_152629_) -> {return  4;})), ModCreativeModeTab.ATIUM_TAB);
    public static final RegistryObject<Block> SMALL_CRYSTALLIZED_ATIUM_BUD = registerBlock("small_crystallized_atium_bud",
            () -> new CrystallizedAtiumCluster(3, 4, BlockBehaviour.Properties.of(Material.AMETHYST).noOcclusion().randomTicks().strength(1.5f).requiresCorrectToolForDrops().sound(SoundType.AMETHYST_CLUSTER).lightLevel((p_152629_) -> {return  4;})), ModCreativeModeTab.ATIUM_TAB);

    //Crystalline Tree
    public static final RegistryObject<Block> CRYSTALLINE_LOG = registerBlock("crystalline_log",
            () -> new ModFlammableRotatedPillarBlock(BlockBehaviour.Properties.copy(Blocks.OAK_LOG)), ModCreativeModeTab.ATIUM_TAB);
    public static final RegistryObject<Block> CRYSTALLINE_WOOD = registerBlock("crystalline_wood",
            () -> new ModFlammableRotatedPillarBlock(BlockBehaviour.Properties.copy(Blocks.OAK_WOOD)), ModCreativeModeTab.ATIUM_TAB);
    public static final RegistryObject<Block> STRIPPED_CRYSTALLINE_LOG = registerBlock("stripped_crystalline_log",
            () -> new ModFlammableRotatedPillarBlock(BlockBehaviour.Properties.copy(Blocks.STRIPPED_OAK_LOG)), ModCreativeModeTab.ATIUM_TAB);
    public static final RegistryObject<Block> STRIPPED_CRYSTALLINE_WOOD = registerBlock("stripped_crystalline_wood",
            () -> new ModFlammableRotatedPillarBlock(BlockBehaviour.Properties.copy(Blocks.STRIPPED_OAK_WOOD)), ModCreativeModeTab.ATIUM_TAB);
    public static final RegistryObject<Block> CRYSTALLINE_LEAVES = registerBlock("crystalline_leaves",
            () -> new CrystallineLeavesBlock(BlockBehaviour.Properties.copy(Blocks.OAK_LEAVES)) {
                @Override
                public boolean isFlammable(BlockState state, BlockGetter world, BlockPos pos, Direction face) {
                    return true;
                }
                @Override
                public int getFlammability(BlockState state, BlockGetter world, BlockPos pos, Direction face) {
                    return 60;
                }
                @Override
                public int getFireSpreadSpeed(BlockState state, BlockGetter world, BlockPos pos, Direction face) {
                    return 30;
                }
            }, ModCreativeModeTab.ATIUM_TAB);
    public static final RegistryObject<Block> BUDDING_CRYSTALLINE_LEAVES = registerBlock("budding_crystalline_leaves",
            () -> new BuddingCrystallineLeaves(BlockBehaviour.Properties.copy(Blocks.OAK_LEAVES).lightLevel((state) -> state.getValue(BuddingCrystallineLeaves.GROWN) ? 5 : 0)), ModCreativeModeTab.ATIUM_TAB);
    public static final RegistryObject<Block> CRYSTALLINE_SAPLING = registerBlock("crystalline_sapling",
            () -> new SaplingBlock(new CrystallineTreeGrower(), BlockBehaviour.Properties.copy(Blocks.OAK_SAPLING)), ModCreativeModeTab.ATIUM_TAB);

    //Crystalline wood building blocks
    public static final RegistryObject<Block> CRYSTALLINE_PLANKS = registerBlock("crystalline_planks",
            () -> new Block(BlockBehaviour.Properties.copy(Blocks.OAK_PLANKS)) {
                @Override
                public boolean isFlammable(BlockState state, BlockGetter world, BlockPos pos, Direction face) {
                    return true;
                }

                @Override
                public int getFlammability(BlockState state, BlockGetter world, BlockPos pos, Direction face) {
                    return 20;
                }

                @Override
                public int getFireSpreadSpeed(BlockState state, BlockGetter world, BlockPos pos, Direction face) {
                    return 5;
                }
            }, ModCreativeModeTab.ATIUM_TAB);
    public static final RegistryObject<Block> CRYSTALLINE_STAIRS = registerBlock("crystalline_stairs",
            () -> new StairBlock(() -> ModBlocks.CRYSTALLINE_PLANKS.get().defaultBlockState(), BlockBehaviour.Properties.copy(Blocks.OAK_STAIRS)) {
                @Override
                public boolean isFlammable(BlockState state, BlockGetter world, BlockPos pos, Direction face) {
                    return true;
                }

                @Override
                public int getFlammability(BlockState state, BlockGetter world, BlockPos pos, Direction face) {
                    return 20;
                }

                @Override
                public int getFireSpreadSpeed(BlockState state, BlockGetter world, BlockPos pos, Direction face) {
                    return 5;
                }
            }, ModCreativeModeTab.ATIUM_TAB);
    public static final RegistryObject<Block> CRYSTALLINE_SLAB = registerBlock("crystalline_slab",
            () -> new SlabBlock(BlockBehaviour.Properties.copy(Blocks.OAK_SLAB)), ModCreativeModeTab.ATIUM_TAB);
    public static final RegistryObject<Block> CRYSTALLINE_FENCE = registerBlock("crystalline_fence",
            () -> new FenceBlock(BlockBehaviour.Properties.copy(Blocks.OAK_FENCE)), ModCreativeModeTab.ATIUM_TAB);
    public static final RegistryObject<Block> CRYSTALLINE_FENCE_GATE = registerBlock("crystalline_fence_gate",
            () -> new FenceGateBlock(BlockBehaviour.Properties.copy(Blocks.OAK_FENCE_GATE)), ModCreativeModeTab.ATIUM_TAB);
    //interactive crystalline blocks
    public static final RegistryObject<Block> CRYSTALLINE_BUTTON = registerBlock("crystalline_button",
            () -> new WoodButtonBlock(BlockBehaviour.Properties.copy(Blocks.OAK_BUTTON).noCollission()), ModCreativeModeTab.ATIUM_TAB);
    public static final RegistryObject<Block> CRYSTALLINE_PRESSURE_PLATE = registerBlock("crystalline_pressure_plate",
            () -> new PressurePlateBlock(PressurePlateBlock.Sensitivity.EVERYTHING, BlockBehaviour.Properties.copy(Blocks.OAK_PRESSURE_PLATE)), ModCreativeModeTab.ATIUM_TAB);
    public static final RegistryObject<Block> CRYSTALLINE_DOOR = registerBlock("crystalline_door",
            () -> new DoorBlock(BlockBehaviour.Properties.copy(Blocks.OAK_DOOR).noOcclusion()), ModCreativeModeTab.ATIUM_TAB);
    public static final RegistryObject<Block> CRYSTALLINE_TRAPDOOR = registerBlock("crystalline_trapdoor",
            () -> new TrapDoorBlock(BlockBehaviour.Properties.copy(Blocks.OAK_TRAPDOOR).noOcclusion()), ModCreativeModeTab.ATIUM_TAB);





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
