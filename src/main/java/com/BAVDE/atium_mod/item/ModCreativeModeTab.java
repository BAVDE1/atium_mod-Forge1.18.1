package com.BAVDE.atium_mod.item;

import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;

public class ModCreativeModeTab {
    public static final CreativeModeTab ATIUM_TAB = new CreativeModeTab("atium_mod_tab") {
        @Override
        public ItemStack makeIcon() {
            return new ItemStack(ModItems.PUREATIUM.get());
        }
    };
}
