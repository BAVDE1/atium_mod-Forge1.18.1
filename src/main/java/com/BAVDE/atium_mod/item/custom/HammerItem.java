package com.BAVDE.atium_mod.item.custom;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

public class HammerItem extends Item {
    public HammerItem(Properties pProperties) {
        super(pProperties);

    }
    @Override
    public boolean hasContainerItem(ItemStack stack) { //makes the hammer a container item, so it won't be consumed when crafted with (like buckets)
        return true;
    }

    @Override
    public ItemStack getContainerItem(ItemStack itemStack) {
        if (itemStack.getDamageValue() < itemStack.getMaxDamage()) { //if the durability damage is higher the items' max durability
            itemStack.setDamageValue(itemStack.getDamageValue() + 1); //item takes 1 durability damage when used in crafting
            return itemStack.copy(); //puts the newly damaged item in the crafting table
        } else {
            itemStack.setDamageValue(itemStack.getDamageValue() + 1); //item takes 1 durability damage when used in crafting
            return itemStack; //doesn't give item back after crafting because it is NULL
        }
    }
}