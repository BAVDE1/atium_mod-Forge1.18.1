package com.BAVDE.atium_mod.util;

import com.BAVDE.atium_mod.AtiumMod;
import net.minecraft.commands.arguments.ResourceOrTagLocationArgument;
import net.minecraft.core.Registry;
import net.minecraft.data.BuiltinRegistries;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.tags.TagsProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ConfiguredStructureTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.levelgen.feature.ConfiguredStructureFeature;
import net.minecraft.world.level.levelgen.structure.BuiltinStructures;
import net.minecraftforge.common.Tags;
import net.minecraftforge.registries.ForgeRegistries;

public class ModTags {
    public static class Blocks {

        public static final TagKey<Block> METAL_ORES
                = tag("metal_ores");

        //tag stuff
        private static TagKey<Block> tag(String name) {
            return BlockTags.create(new ResourceLocation(AtiumMod.MOD_ID, name));
        }
        private static TagKey<Block> forgeTag(String name) {
            return BlockTags.create(new ResourceLocation("forge", name));
        }
    }

    public static class Items {

        //TAGS HERE
        public static final TagKey<Item> KNIFE = tag("knife");
        public static final TagKey<Item> HAMMER = tag("hammer");

        public static final TagKey<Item> INFUSING_METALS = tag("infusing_metals");

        public static final TagKey<Item> INFUSING_IRON = tag("infusing_iron");
        public static final TagKey<Item> INFUSING_STEEL = tag("infusing_steel");
        public static final TagKey<Item> INFUSING_TIN = tag("infusing_tin");
        public static final TagKey<Item> INFUSING_PEWTER = tag("infusing_pewter");
        public static final TagKey<Item> INFUSING_BRASS = tag("infusing_brass");
        public static final TagKey<Item> INFUSING_ZINC = tag("infusing_zinc");
        public static final TagKey<Item> INFUSING_COPPER = tag("infusing_copper");
        public static final TagKey<Item> INFUSING_BRONZE = tag("infusing_bronze");
        public static final TagKey<Item> INFUSING_GOLD = tag("infusing_gold");
        public static final TagKey<Item> INFUSING_ALUMINIUM = tag("infusing_aluminium");

        //tag stuff
        private static TagKey<Item> tag(String name) {
            return ItemTags.create(new ResourceLocation(AtiumMod.MOD_ID, name));
        }
        private static TagKey<Item> forgeTag(String name) {
            return ItemTags.create(new ResourceLocation("forge", name));
        }
    }
}
