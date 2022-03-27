package com.BAVDE.atium_mod.world.feature;

import com.BAVDE.atium_mod.block.ModBlocks;
import net.minecraft.core.Holder;
import net.minecraft.data.worldgen.features.FeatureUtils;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.util.valueproviders.ConstantInt;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.WeightedPlacedFeature;
import net.minecraft.world.level.levelgen.feature.configurations.RandomFeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration;
import net.minecraft.world.level.levelgen.feature.featuresize.TwoLayersFeatureSize;
import net.minecraft.world.level.levelgen.feature.foliageplacers.BlobFoliagePlacer;
import net.minecraft.world.level.levelgen.feature.foliageplacers.FancyFoliagePlacer;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.levelgen.feature.trunkplacers.FancyTrunkPlacer;
import net.minecraft.world.level.levelgen.feature.trunkplacers.StraightTrunkPlacer;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;

import java.util.List;

public class ModConfiguredFeatures {
    public static final Holder<ConfiguredFeature<TreeConfiguration, ?>> CRYSTALLINE_TREE =
            FeatureUtils.register("crystalline", Feature.TREE, new TreeConfiguration.TreeConfigurationBuilder(
                    BlockStateProvider.simple(ModBlocks.CRYSTALLINE_LOG.get()),
                    new FancyTrunkPlacer(3, 11, 0),
                    BlockStateProvider.simple(ModBlocks.CRYSTALLINE_LEAVES.get()),
                    new FancyFoliagePlacer(ConstantInt.of(2), ConstantInt.of(4), 4),
                    new TwoLayersFeatureSize(0, 0, 0)).build());

    public static final Holder<PlacedFeature> CRYSTALLINE_CHECKED = PlacementUtils.register("crystalline_checked", CRYSTALLINE_TREE,
            PlacementUtils.filteredByBlockSurvival(ModBlocks.CRYSTALLINE_SAPLING.get()));

    /*public static final Holder<ConfiguredFeature<RandomFeatureConfiguration, ?>> CRYSTALLINE_SPAWN =
            FeatureUtils.register("crystalline_spawn", Feature.RANDOM_SELECTOR,
                    new RandomFeatureConfiguration(List.of(new WeightedPlacedFeature(CRYSTALLINE_CHECKED,
                            0.5F)), CRYSTALLINE_CHECKED));*/
}
