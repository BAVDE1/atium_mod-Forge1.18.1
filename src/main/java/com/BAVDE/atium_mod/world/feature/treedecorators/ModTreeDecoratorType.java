package com.BAVDE.atium_mod.world.feature.treedecorators;

import com.BAVDE.atium_mod.AtiumMod;
import com.mojang.serialization.Codec;
import net.minecraft.core.Registry;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.levelgen.feature.treedecorators.TreeDecorator;
import net.minecraft.world.level.levelgen.feature.treedecorators.TreeDecoratorType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Supplier;

public class ModTreeDecoratorType extends TreeDecoratorType {
    private static final DeferredRegister<TreeDecoratorType<?>> MOD_TREE_DECORATOR =
            DeferredRegister.create(ForgeRegistries.TREE_DECORATOR_TYPES, AtiumMod.MOD_ID);

    public static final TreeDecoratorType<TreeDecorator> HANGING_LEAVES = registerDecoration("hanging_leaves", CrystallineTreeDecorator.CODEC);

    public ModTreeDecoratorType(Codec codec) {
        super(codec);
    }

    private static <P extends TreeDecorator> TreeDecoratorType<P> registerDecoration(String pKey, Codec<P> pCodec) {
        return Registry.register(Registry.TREE_DECORATOR_TYPES, pKey, new TreeDecoratorType<>(pCodec));
    }

    public static void register(IEventBus eventBus) {
        MOD_TREE_DECORATOR.register(eventBus);
    }
}
