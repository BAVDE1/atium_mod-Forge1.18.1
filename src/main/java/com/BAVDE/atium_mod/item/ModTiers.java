package com.BAVDE.atium_mod.item;

import net.minecraft.tags.BlockTags;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraftforge.common.ForgeTier;

public class ModTiers { //middle click forgeTier, ctrl + h on Tiers go to tiers
    public static final ForgeTier Knife = new ForgeTier(0, 250, 2.0f,
            1f, 15, BlockTags.NEEDS_STONE_TOOL,
            () -> Ingredient.of(ModItems.CRYSTALLIZED_ATIUM_SHARD.get()));
}
