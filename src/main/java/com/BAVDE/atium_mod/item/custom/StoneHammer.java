package com.BAVDE.atium_mod.item.custom;

import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.stats.Stats;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import org.checkerframework.checker.nullness.qual.Nullable;

import java.util.function.Consumer;

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

        if (itemStack.getDamageValue() < 16) {
            itemStack.setDamageValue(itemStack.getDamageValue() + 1); //item takes 1 durability damage when used in crafting
            return itemStack.copy(); //puts the newly damaged item in the crafting table
        } else {
            itemStack.setDamageValue(itemStack.getDamageValue() + 1); //item takes 1 durability damage when used in crafting
            return itemStack;
        }
    }
}