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
        //if the durability damage is higher the items' max durability
        if (itemStack.getDamageValue() < (itemStack.getMaxDamage() - 1)) {
            //item takes 1 durability damage when used in crafting
            itemStack.setDamageValue(itemStack.getDamageValue() + 1);
            //puts the newly damaged item in the crafting inv
            return itemStack.copy();
        } else {
            return itemStack; //doesn't give item back after crafting since it returns NULL
        }
    }
}