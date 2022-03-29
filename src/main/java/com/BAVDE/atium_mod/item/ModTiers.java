package com.BAVDE.atium_mod.item;

import net.minecraft.tags.BlockTags;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraftforge.common.ForgeTier;

public class ModTiers { //middle click forgeTier, ctrl + h on Tiers go to vanilla tiers
    public static final ForgeTier CRYSTALLIZED_KNIFE_TIER = new ForgeTier(0, 250, 2.0f,
            1f, 10, BlockTags.NEEDS_STONE_TOOL,
            () -> Ingredient.of(ModItems.CRYSTALLIZED_SHARD.get()));

    public static final ForgeTier ATIUM = new ForgeTier(3, 1200, 7.0f,
            2.5f, 15, BlockTags.NEEDS_IRON_TOOL,
            () -> Ingredient.of(ModItems.PURE_ATIUM.get()));
}