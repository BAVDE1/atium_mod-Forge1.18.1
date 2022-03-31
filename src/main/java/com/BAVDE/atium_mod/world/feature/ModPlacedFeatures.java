package com.BAVDE.atium_mod.world.feature;

import net.minecraft.core.Holder;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.data.worldgen.placement.VegetationPlacements;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.minecraft.world.level.levelgen.placement.PlacementContext;

public class ModPlacedFeatures {
    public static final Holder<PlacedFeature> CRYSTALLINE_PLACED = PlacementUtils.register("crystalline_placed",
            ModConfiguredFeatures.CRYSTALLINE_SPAWN, VegetationPlacements.treePlacement(
                    PlacementUtils.countExtra(3, 0.1f, 2)));
}

