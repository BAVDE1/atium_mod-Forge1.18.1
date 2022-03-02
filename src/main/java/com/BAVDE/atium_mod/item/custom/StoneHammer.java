package com.BAVDE.atium_mod.item.custom;

import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import org.checkerframework.checker.nullness.qual.Nullable;

public class StoneHammer extends Item {
    public StoneHammer(Properties pProperties) {
        super(pProperties);

    }
    @Override
    public boolean hasContainerItem(ItemStack stack) { //makes the hammer a container item, so it won't be consumed when crafted with (like buckets)
        return true;
    }

    @Override
    public ItemStack getContainerItem(ItemStack itemStack) {
        /*itemStack.hurtAndBreak(1, itemStack,(p_186374_) -> {
            p_186374_.broadcastBreakEvent(itemStack.getEquipmentSlot());});*/

        if (itemStack.getDamageValue() == 0 || itemStack.getDamageValue() < 0) { //if the durability is equal to 0 or less than 0 then:
            return itemStack; //deletes the item when crafted
        } else { //if the durability is above 0
            itemStack.setDamageValue(itemStack.getDamageValue() + 1); //item takes 1 durability damage when used in crafting
            return itemStack.copy(); //puts the newly damaged item in the crafting table
        }
    }
}