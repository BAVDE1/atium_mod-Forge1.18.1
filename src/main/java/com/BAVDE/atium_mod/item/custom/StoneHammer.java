package com.BAVDE.atium_mod.item.custom;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

public class StoneHammer extends Item{
    public StoneHammer(Properties pProperties) {
        super(pProperties);

    }

    @Override
    public boolean hasContainerItem(ItemStack stack) {
        return true;
    }

    @Override
    public ItemStack getContainerItem(ItemStack itemStack) {

        itemStack.setDamageValue(itemStack.getDamageValue() + 1);

        return itemStack.copy();
    }
}
