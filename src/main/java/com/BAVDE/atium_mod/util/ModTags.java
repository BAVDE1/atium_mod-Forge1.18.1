package com.BAVDE.atium_mod.util;

import com.BAVDE.atium_mod.AtiumMod;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.common.Tags;

public class ModTags {
    public static class Blocks {
        public static final Tags.Blocks NEW_TAG = null;


        //tag stuff
        private static TagKey<Block> tag(String name) {
            return BlockTags.create(new ResourceLocation(AtiumMod.MOD_ID, name));
        }
        private static TagKey<Block> forgeTag(String name) {
            return BlockTags.create(new ResourceLocation("forge", name));
        }
    }

    public static class Items {

        public static final TagKey<Item> KNIFE
                = tag("knife");

        public static final TagKey<Item> HAMMER
                = tag("hammer");


        //tag stuff
        private static TagKey<Item> tag(String name) {
            return ItemTags.create(new ResourceLocation(AtiumMod.MOD_ID, name));
        }
        private static TagKey<Item> forgeTag(String name) {
            return ItemTags.create(new ResourceLocation("forge", name));
        }
    }
}
