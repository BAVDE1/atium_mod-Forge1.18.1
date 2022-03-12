package com.BAVDE.atium_mod.worldgen;

import com.BAVDE.atium_mod.AtiumMod;
import com.BAVDE.atium_mod.worldgen.structures.AtiumGeodeStructure;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.levelgen.feature.StructureFeature;
import net.minecraft.world.level.levelgen.feature.configurations.JigsawConfiguration;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import static com.BAVDE.atium_mod.AtiumMod.MOD_ID;

public class Registration {

    private static final DeferredRegister<StructureFeature<?>> STRUCTURES = DeferredRegister.create(ForgeRegistries.STRUCTURE_FEATURES, MOD_ID);

    public static void init() {
        IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();
        STRUCTURES.register(bus);
    }

    public static final RegistryObject<StructureFeature<JigsawConfiguration>> ATIUM_GEODE_STRUCTURE = STRUCTURES.register("atium_geode_structure", AtiumGeodeStructure::new);

    public static final TagKey<Biome> HAS_ATIUM_GEODE_STRUCTURE = TagKey.create(Registry.BIOME_REGISTRY, new ResourceLocation(AtiumMod.MOD_ID, "has_structure/atium_geode_structure"));
}
