package com.BAVDE.atium_mod.item.custom;

import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.SlotAccess;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ClickAction;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Optional;

public class CoinPouch extends Item {
    public CoinPouch(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public boolean overrideOtherStackedOnMe(ItemStack pStack, ItemStack pOther, Slot pSlot, ClickAction pAction, Player pPlayer, SlotAccess pAccess) {
        if (pAction == ClickAction.SECONDARY && pSlot.allowModification(pPlayer)) {
            if (pOther.isEmpty()) {
                if (pStack.getTag().contains("atium_mod.coins")) {
                    //removes all items from pouch
                    remove(pStack, pPlayer).ifPresent(pAccess::set);
                } else return false;
            } else if (pOther.getItem() == Items.IRON_NUGGET) {
                //adds items into pouch
                add(pStack, pOther, pPlayer);
            } else return false;

            return true;
        } else return false;
    }

    @Override
    public boolean isBarVisible(ItemStack pStack) {
        return true;
    }

    //uses durability bar to show how many iron nuggets item contains
    @Override
    public int getBarWidth(ItemStack pStack) {
        if (pStack.getTag().contains("atium_mod.coins")) {
            int currentCoins = pStack.getTag().getInt("atium_mod.coins");
            return Math.min(1 + (12 * currentCoins / pStack.getMaxDamage()), 13);
        } else {
            return 0;
        }
    }

    //add item to pouch
    private void add(ItemStack pouchStack, ItemStack additionStack, Player player) {
        int spaceLeft = pouchStack.getMaxDamage();
        int stackSize = additionStack.getCount();

        if (pouchStack.getTag().contains("atium_mod.coins")) {
            int currentCoins = pouchStack.getTag().getInt("atium_mod.coins");
            spaceLeft = pouchStack.getMaxDamage() - currentCoins;
            int addAmountAllowed = Math.min(spaceLeft, stackSize);

            //if there is space in the pouch
            if (!(currentCoins >= pouchStack.getMaxDamage())) {
                additionStack.shrink(addAmountAllowed);
                pouchStack.getTag().putInt("atium_mod.coins", currentCoins + addAmountAllowed);
                this.playInsertSound(player);
            }
        } else {
            int addAmountAllowed = Math.min(spaceLeft, stackSize);

            additionStack.shrink(addAmountAllowed);
            pouchStack.getTag().putInt("atium_mod.coins", addAmountAllowed);
            this.playInsertSound(player);
        }
    }

    //removes all items from pouch
    private Optional<ItemStack> remove(ItemStack pouchItem, Player player) {
        int currentCoins = pouchItem.getTag().getInt("atium_mod.coins");
        ItemStack returnItemStack = Items.IRON_NUGGET.getDefaultInstance();

        //remove all items & clear tag
        pouchItem.getTag().remove("atium_mod.coins");
        returnItemStack.setCount(currentCoins);

        this.playRemoveOneSound(player);
        return Optional.of(returnItemStack);
    }

    private void playRemoveOneSound(Entity entity) {
        entity.playSound(SoundEvents.BUNDLE_REMOVE_ONE, 0.8F, 0.8F + entity.getLevel().getRandom().nextFloat() * 0.4F);
    }

    private void playInsertSound(Entity entity) {
        entity.playSound(SoundEvents.BUNDLE_INSERT, 0.8F, 0.8F + entity.getLevel().getRandom().nextFloat() * 0.4F);
    }

    @Override
    public void appendHoverText(ItemStack pStack, @Nullable Level pLevel, List<Component> pTooltipComponents, TooltipFlag pIsAdvanced) {
        int count = 0;

        if (pStack.getTag().contains("atium_mod.coins")) {
            int currentCoins = pStack.getTag().getInt("atium_mod.coins");
            count = currentCoins;
        }
        pTooltipComponents.add(new TextComponent("Iron Nuggets: " + count + "/" + pStack.getMaxDamage()));
    }
}