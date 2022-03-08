package com.BAVDE.atium_mod.item;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;

public class ModCreativeModeTab {
    public static final CreativeModeTab ATIUM_TAB = new CreativeModeTab("atium_mod_tab") {
        @Override
        public ItemStack makeIcon() {
            return new ItemStack(ModItems.PURE_ATIUM.get());
        }
    };
}
